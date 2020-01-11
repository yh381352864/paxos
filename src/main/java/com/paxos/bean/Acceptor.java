package com.paxos.bean;
/**
 * yanghao
 * Copyright (C) 2020-2030 All Rights Reserved.
 */

import cn.hutool.core.util.RandomUtil;
import lombok.Data;

/**
 * 评审团
 * @author yanghao
 * @version Acceptor.java, v 0.1 2020-01-11 09:32
 */
@Data
public class Acceptor {

    /**
     * 评审团唯一标识
     */
    private String AccepName;

    /**
     * 是否结束选举
     */
    private boolean endElect;

    /**
     * 接受的编号
     */
    private Integer accepNumber;

    /**
     * 推荐人
     */
    private String referrer;

    /**
     * 构造器
     */
    public Acceptor(){
        this.AccepName = RandomUtil.randomString(20);
        this.endElect = false;
    }

}
