package com.paxos.service;
/**
 * yanghao
 * Copyright (C) 2020-2030 All Rights Reserved.
 */

import com.paxos.bean.Porposer;
import com.paxos.domain.result.AcceptResult;
import com.paxos.bean.Acceptor;
import java.util.List;
import java.util.Map;

/**
 * @author yanghao
 * @version Elect.java, v 0.1 2020-01-11 10:08
 */
public class Elect {

    /**
     * 选举方法
     * @param proposerNumber
     * @param acceptorMap
     * @return
     */
    public void electReferrer(Integer proposerNumber, Map<String, Acceptor> acceptorMap){
        if(acceptorMap == null || acceptorMap.size() == 0){
            return;
        }

        //团队人员
        Porposer porposer = new Porposer();
        //System.out.println(Thread.currentThread().getName() + " ==申请人== " + porposer.getReferrer() + " == " + proposerNumber);

        //超过半数同意，则进行accept阶段（没有申请通过的就需要一直发信息了！！）
        Integer agreeCount;
        Round round;
        String referrer;

        while (true){
            //创建Round
            round = new Round(proposerNumber);

            //1.prepare阶段（向评审团发送请求）
            agreeCount = round.prepare(acceptorMap);

            //没有超过半数同意
            if(agreeCount < (acceptorMap.size() + 1) / 2){
                proposerNumber ++;
                continue;
            }

            //2.accept阶段
            List<AcceptResult> resultList = round.accept(porposer, acceptorMap);
            for(AcceptResult acceptResult : resultList){
                //取Rejected(n)里面n最大的一个去申请
                if(!acceptResult.isSuccess() && acceptResult.getAccepNumber() > proposerNumber){
                    proposerNumber = acceptResult.getAccepNumber();
                }
            }

            //判断循环是否结束
            referrer = round.isEndExecute(acceptorMap);
            if(referrer != null){
                break;
            }

        }

        return;

    }

}
