package com.paxos.service;
/**
 * yanghao
 * Copyright (C) 2020-2030 All Rights Reserved.
 */

import com.paxos.bean.Acceptor;
import com.paxos.bean.Porposer;
import com.paxos.domain.result.AcceptResult;
import com.paxos.domain.result.PrepareResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanghao
 * @version Round.java, v 0.1 2020-01-11 10:01
 */
public class Round extends AbstractRound {

    /**
     * 构造器
     *
     * @param proposerNumber  唯一编号
     */
    public Round(Integer proposerNumber) {
        super(proposerNumber);
    }

    /**
     * 准备方法
     *
     * @param acceptorMap
     * @return
     */
    @Override
    public Integer prepare(Map<String, Acceptor> acceptorMap) {

        //返回集合
        List<PrepareResult> resultList = new ArrayList<>();
        PrepareResult prepareResult;
        Integer agreeCount = 0;

        for(Acceptor acceptor : acceptorMap.values()){
            if(acceptor.getAccepNumber() == null || acceptor.getAccepNumber() < this.getProposerNumber()){
                //取请求者的编号
                acceptor.setAccepNumber(this.getProposerNumber());
                //返回对象
                prepareResult = new PrepareResult(true, acceptor);
                //统计返回成功个数
                agreeCount ++;

            }else{
                //返回对象
                prepareResult = new PrepareResult(false, acceptor);

            }

            resultList.add(prepareResult);
        }

        //存储每个Round的prepare阶段返回值
        this.setResultList(resultList);

        return agreeCount;
    }

    /**
     * 推荐方法（提交方法）
     *
     * @param porposer
     * @param acceptorMap
     * @return
     */
    @Override
    public List<AcceptResult> accept(Porposer porposer, Map<String, Acceptor> acceptorMap) {
        List<AcceptResult> resultList = new ArrayList<>();
        AcceptResult acceptResult;

        //从上次返回结果里面取出编号最大的结果（没有就默认是创建对象时生成的）
        Integer max = 0;
        for(PrepareResult result : this.getResultList()){
            if(result.getAcceptor().getReferrer() != null && result.getAcceptor().getAccepNumber() > max){
                max = result.getAcceptor().getAccepNumber();
                //取上次prepare阶段返回的推荐
                porposer.setReferrer(result.getAcceptor().getReferrer());
            }
        }

        Acceptor acceptor;
        for(PrepareResult result : this.getResultList()){
            //查出最新的评审团信息
            acceptor = acceptorMap.get(result.getAcceptor().getAccepName());

            //如果评审团的编号已经大于本次提交，则返回失败和评审团编号
            if(acceptor.getAccepNumber() > this.getProposerNumber()){
                acceptResult = new AcceptResult(false, acceptor.getAccepNumber());
            }else{
                acceptor.setAccepNumber(this.getProposerNumber());
                acceptor.setReferrer(porposer.getReferrer());

                acceptResult = new AcceptResult(true);
            }

            resultList.add(acceptResult);

        }

        return resultList;
    }

    /**
     * 判断是否停止选举
     *
     * @param acceptorMap
     * @return
     */
    @Override
    public String isEndExecute(Map<String, Acceptor> acceptorMap) {
        Map<String, Integer> electMap = new HashMap<>();
        Integer referrerCount = 0;

        for(Acceptor acceptor : acceptorMap.values()){
            if(electMap.get(acceptor.getReferrer()) == null){
                referrerCount = 1;

            }else {
                referrerCount = electMap.get(acceptor.getReferrer());
                referrerCount ++;

                //评审团超过一半同意即可选举结束
                if(referrerCount >= (acceptorMap.size() + 1) / 2){
                    return acceptor.getReferrer();
                }

            }

            electMap.put(acceptor.getReferrer(), referrerCount);

        }

        return null;
    }

}
