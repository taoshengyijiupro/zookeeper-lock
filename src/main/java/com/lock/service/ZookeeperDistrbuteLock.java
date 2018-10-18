package com.lock.service;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZookeeperDistrbuteLock extends ZooleeperAbstractLock {
    @Override
    Boolean tryLock() {

        try {
            zkClient.createEphemeral(PATH);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    void waitLock() {
        //使用事件监听获取节点被删除
        IZkDataListener iZkDataListener = new IZkDataListener() {

            //当节点删除
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                if (countDownLatch!=null) {
                    //唤醒
                    countDownLatch.countDown();
                }
            }

            //当节点改变
            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        };
        //注册节点信息
        zkClient.subscribeDataChanges(PATH,iZkDataListener);
        if (zkClient.exists(PATH)){
            try {
                //创建信号量
                countDownLatch = new CountDownLatch(1);
                //等到
                countDownLatch.await();
            }catch (Exception e){

            }
        }
        //删除时间通知
        zkClient.unsubscribeDataChanges(PATH,iZkDataListener);
    }
}
