package com.paxos.service;
/**
 * yanghao
 * Copyright (C) 2020-2030 All Rights Reserved.
 */

import com.paxos.bean.Acceptor;
import com.paxos.bean.Porposer;
import com.paxos.domain.result.AcceptResult;
import com.paxos.domain.result.PrepareResult;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author yanghao
 * @version AbstractRound.java, v 0.1 2020-01-11 09:48
 */
@Data
public abstract class AbstractRound {

    /**
     * 每次提交唯一的编号
     */
    private Integer proposerNumber;

    /**
     * prepare阶段返回值
     */
    private List<PrepareResult> resultList;

    /**
     * 构造器
     * @param proposerNumber
     */
    public AbstractRound(Integer proposerNumber){
        this.proposerNumber = proposerNumber;
    }

    /**
     * 准备方法
     * @param acceptorMap
     * @return
     */
    public abstract Integer prepare(Map<String, Acceptor> acceptorMap);

    /**
     * 推荐方法（提交方法）
     * @param porposer
     * @param acceptorMap
     * @return
     */
    public abstract List<AcceptResult> accept(Porposer porposer, Map<String, Acceptor> acceptorMap);

    /**
     * 判断是否停止选举
     * @param acceptorMap
     * @return
     */
    public abstract String isEndExecute(Map<String, Acceptor> acceptorMap);

}
