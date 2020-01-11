package com.paxos.domain.result;
/**
 * fshows.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */

import lombok.Data;

/**
 * @author yanghao
 * @version AcceptResult.java, v 0.1 2020-01-11 09:56
 */
@Data
public class AcceptResult {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 已经接受的编号
     */
    private Integer accepNumber;

    /**
     * 构造器
     * @param success
     */
    public AcceptResult(boolean success){
        this.success = success;
    }

    /**
     * 构造器
     * @param success
     * @param accepNumber
     */
    public AcceptResult(boolean success, Integer accepNumber){
        this.success = success;
        this.accepNumber = accepNumber;
    }

}
