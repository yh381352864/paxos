package com.paxos.test;
/**
 * fshows.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */

import com.paxos.bean.Acceptor;
import com.paxos.service.Elect;
import com.paxos.service.Round;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yanghao
 * @version ElectTest.java, v 0.1 2020-01-11 13:22
 */
public class ElectTest {

    public static void main(String[] args){
        //创建评审团（奇数个）
        Integer count = 5;
        Map<String, Acceptor> acceptorMap = createAcceptor(count);

        Elect elect = new Elect();

        //构造一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,10,10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for(int i = 1; i <= 10; i ++){
            int finalI = i;
            threadPool.execute(() -> {
                try {

                    elect.electReferrer(finalI, acceptorMap);

                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    threadPool.shutdown();// 关闭线程池
                }
            });
        }

        Round round = new Round(1);
        while (true){
            try {
                //每0.1秒查看一次选举结果
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }

            String referrer = round.isEndExecute(acceptorMap);
            if(referrer != null){
                System.out.println("本次选举结果 == " + referrer);
                break;
            }

        }

    }

    /**
     * 创建评审团
     */
    private static Map<String, Acceptor> createAcceptor(int count) {
        Map<String, Acceptor> acceptorMap = new HashMap<>();

        Acceptor acceptor;
        for(int i = 0;i < count;i++){
            acceptor = new Acceptor();
            acceptorMap.put(acceptor.getAccepName(), acceptor);
        }

        return acceptorMap;
    }

}
