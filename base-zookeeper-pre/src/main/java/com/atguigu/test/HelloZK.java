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
public class HelloZK {
    private static final String CONNECTSTRING = "192.168.83.131:2181";
    private static final int SESSION_TIMEOUT = 50 * 1000;
    private static final String PATH = "/hello2";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        HelloZK helloZK=new HelloZK();
        ZooKeeper zooKeeper=helloZK.startZK();
        if(zooKeeper.exists(PATH,false)==null){
            helloZK.createZnode(zooKeeper,PATH,"hello0316");
            String retValue=helloZK.getZnode(zooKeeper, PATH);
            log.info("*****************************retValue"+retValue);
        }else{
            log.info("node already exist");
        }
        helloZK.stopZK(zooKeeper);
    }

    public ZooKeeper startZK() throws IOException {
        return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    public void stopZK(ZooKeeper zk) throws InterruptedException {
        if (null != zk) {
            zk.close();
        }
    }

    public void createZnode(ZooKeeper zooKeeper, String nodePath, String nodeValue) throws KeeperException, InterruptedException {
        zooKeeper.create(nodePath, nodeValue.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public String getZnode(ZooKeeper zooKeeper, String nodePath) throws KeeperException, InterruptedException {
        String result = null;
        result = new String(zooKeeper.getData(nodePath, false, new Stat()));
        return result;
    }

}
