package com.paxos.domain.result;
/**
 * yanghao
 * Copyright (C) 2020-2030 All Rights Reserved.
 */

import com.paxos.bean.Acceptor;
import lombok.Data;

/**
 * @author yanghao
 * @version PrepareResult.java, v 0.1 2020-01-11 09:52
 */
@Data
public class PrepareResult {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 评审团
     */
    private Acceptor acceptor;

    /**
     * 对象构造器
     * @param success
     * @param acceptor
     */
    public PrepareResult(boolean success, Acceptor acceptor){
        this.success = success;
        this.acceptor = acceptor;
    }

}
