package com.atguigu.test;

import lombok.extern.java.Log;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @author: bin.wang
 * @date: 2020/6/28 09:32
 */
@Log
public class WatchMore {
    //常量
    private static final String CONNECTSTRING = "192.168.83.131:2181";
    private static final int SESSION_TIMEOUT = 50 * 1000;
    private static final String PATH = "/hello";

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    /**
     * 实例变量
     */
    private ZooKeeper zooKeeper=null;

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    private String oldValue=null;


    /**
     * 监控我们的hello节点，获取初始值后设置watch，只要发生变化，打印出最新的值，一次性watch
     * @param args
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        WatchMore watchMore=new WatchMore();
        watchMore.setZooKeeper(watchMore.startZK());
        if(watchMore.getZooKeeper().exists(PATH, false)==null){
            watchMore.createZnode(PATH, "AAA");
            String retValue=watchMore.getZnode(PATH);
            log.info("**************first retValue:"+retValue);
            Thread.sleep(Long.MAX_VALUE);
        }else{
            log.info("**************node exist");
        }
    }

    public ZooKeeper startZK() throws IOException {
        return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    public void createZnode(String nodePath, String nodeValue) throws KeeperException, InterruptedException {
        zooKeeper.create(nodePath, nodeValue.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public String getZnode(String nodePath) throws KeeperException, InterruptedException {
        String result = null;
        result = new String(zooKeeper.getData(nodePath, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    triggerValue(PATH);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, new Stat()));
        oldValue=result;
        return result;
    }

    public boolean triggerValue(String nodePath) throws KeeperException, InterruptedException {
        String result=null;
        byte[] byteArr=zooKeeper.getData(PATH, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    triggerValue(nodePath);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, new Stat());
        result=new String(byteArr);
        String newValue=result;
        if(oldValue.equals(newValue)){
            log.info("************no changes**********");
            return false;
        }else{
            log.info("**************oldValue:"+oldValue+"\t newValue:"+newValue);
            oldValue=newValue;
            return true;
        }
    }

}
