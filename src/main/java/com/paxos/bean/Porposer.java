package com.paxos.bean;
/**
 * yanghao
 * Copyright (C) 2020-2030 All Rights Reserved.
 */

import cn.hutool.core.util.RandomUtil;
import lombok.Data;

/**
 * 团队成员
 * @author yanghao
 * @version Porposer.java, v 0.1 2020-01-11 09:31
 */
@Data
public class Porposer {

    /**
     * 推荐人
     */
    private String referrer;

    /**
     * 构造器
     */
    public Porposer(){
        this.referrer = "P" + RandomUtil.randomInt(26);
    }

}
