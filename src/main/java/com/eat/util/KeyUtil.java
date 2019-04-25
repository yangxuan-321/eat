package com.eat.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author : yangxuan
 * @description : 生成主键等不重复工具类
 * @Time : Created in 13:10 2018/6/2
 * @Modifyed By :
 */
public class KeyUtil {

    public static final String  getUniqueKey(){
        //生成六位随机数 -- 为避免重复加上线程id  以后处理分布式主键问题
        //Random random = new Random();
        //试试return String.valueOf(System.currentTimeMillis() + random.nextInt(900000) + 100000 + Thread.currentThread().getId());
        return getUUID32();
    }

    public static final String getUUID32(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

}
