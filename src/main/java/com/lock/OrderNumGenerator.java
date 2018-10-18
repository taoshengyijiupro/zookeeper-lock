package com.lock;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成订单号
 */
public class OrderNumGenerator {

    private static int count = 0;

    /**
     * 生成订单号
     *
     * @return
     */
    public String getNumber() {
//        try {
//            Thread.sleep(2000);
//        }catch (Exception e){
//
//        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date()) + "-" + ++count;
    }
}
