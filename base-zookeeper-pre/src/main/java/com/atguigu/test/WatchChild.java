package com.atguigu.test;

import lombok.extern.java.Log;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * @author: bin.wang
 * @date: 2020/6/28 09:32
 */
@Log
public class WatchChild {
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
     * 1。监控我们的/hello节点 完成注册
     * 2.按照注册的父亲节点（/hello）,监控下面的子节点（增加/删除）的变化情况
     * @param args
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        WatchChild watchChild=new WatchChild();
        watchChild.setZooKeeper(watchChild.startZK());
        Thread.sleep(Long.MAX_VALUE);
    }

    public ZooKeeper startZK() throws IOException {
        return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //存在子节点变化
                //并且在/hello的路径下
                if(watchedEvent.getType()== Event.EventType.NodeChildrenChanged && watchedEvent.getPath().equals(PATH)){
                    showChildNode(PATH);
                }else{
                    showChildNode(PATH);//注册父亲节点 并打印初识节点有多少个
                }
            }
        });
    }
    public void showChildNode(String nodePath){
        List<String> list=null;
        try {
            list=zooKeeper.getChildren(nodePath, true);
            log.info("*************"+list);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
