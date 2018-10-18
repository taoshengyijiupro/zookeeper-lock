package com.lock.service;

import com.lock.OrderNumGenerator;

public class OrderService implements Runnable {

    OrderNumGenerator orderNumGenerator = new OrderNumGenerator();
    ZookeeperDistrbuteLock lock = new ZookeeperDistrbuteLock();
    @Override
    public void run() {

        try {
            //模拟生成订单号
            //synchronized (this) {
            lock.getLock();
            getNumber();
            //}
        }catch (Exception e){

        }finally {
            lock.unLock();
        }

    }

    public void getNumber() {
        String number = orderNumGenerator.getNumber();
        System.out.println(Thread.currentThread().getName() + ",##生成唯一订单号number：" + number);
    }

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        for (int i = 0; i < 100; i++) {
            new Thread(orderService).start();
        }
    }
}
