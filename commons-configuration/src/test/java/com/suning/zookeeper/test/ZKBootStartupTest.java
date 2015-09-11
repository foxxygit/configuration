package com.suning.zookeeper.test;

import com.suning.zookeeper.CreateMode;
import com.suning.zookeeper.ZKBootStartup;
import com.suning.zookeeper.client.ZKClusterClient;
import com.suning.zookeeper.factory.ZookeeperClientMakerFactory;

public class ZKBootStartupTest {
    public static void main(String[] args) {
        ZKBootStartup starter=new ZKBootStartup();
        starter.start();
        // 获取zkclient
        ZKClusterClient client = ZookeeperClientMakerFactory.getInstance().getZKClusterClient("club_zkClient");
        
        String path = "/test/xieyong";
        // 创建一个节点
        if (!client.isExsit(path)) {
            client.createNode(path, "xieyong", CreateMode.PERSISTENT);
        }
        // 对节点进行监听
        PathListener listener = new PathListener();
        client.registerConnectionListener(listener);
        client.registerNodeListener(path, listener);
        // 小小的睡一会
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // donothing
        }
        // 改变值
        client.setNodeValue(path, "xieyong1");

        // 检测是否改变
        System.out.println(client.getNodeValue(path));
        
        // 大大的睡一会
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            // donothing
        }
    }
}
