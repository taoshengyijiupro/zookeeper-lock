package com.lock.service;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * 重构重复代码，将重复代码交给子类执行
 */
public abstract class ZooleeperAbstractLock implements Lock {

    private static final String CONNECTSTRING = "127.0.0.1:2181";
    protected ZkClient zkClient = new ZkClient(CONNECTSTRING);
    protected static final String PATH = "/lock";
    protected CountDownLatch countDownLatch = null;

    @Override
    public void getLock() {
        if (tryLock()){
            System.out.println("###获取锁成功###");
        }else {
            waitLock();

            //重新获取锁
            getLock();
        }
    }

    //是否获取锁成功
    abstract Boolean tryLock();

    //等待
    abstract void waitLock();
    @Override
    public void unLock() {
        if (zkClient!=null){
            zkClient.close();
        }
    }
}
