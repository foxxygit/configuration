package com.suning.zookeeper.test;

import com.suning.zookeeper.CreateMode;
import com.suning.zookeeper.client.ZKClusterClient;
import com.suning.zookeeper.factory.ZkConfig;
import com.suning.zookeeper.factory.ZookeeperClientMakerFactory;

public class ZookeeperTest {

    public static void main(String[] args) {
        ZkConfig config = new ZkConfig();
        config.setConnectionTimeoutMs(600000);
        config.setName("default_zk_client");
        config.setRetrySleepTimeMs(1000);
        config.setRetryTimes(3);
        config.setSessionTimeoutMs(600000);
        config.setUrl("10.25.8.140:2181,10.25.8.140:2182,10.25.8.140:2183");
        // 初始化zk连接
        ZookeeperClientMakerFactory.getInstance().init(config);
        // 获取zkclient
        ZKClusterClient client = ZookeeperClientMakerFactory.getInstance().getZKClusterClient("default_zk_client");
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
        
        
        addShutdownHook("default_zk_client");
        // 大大的睡一会
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            // donothing
        }

    }
    
    private static void addShutdownHook(final String name) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ZookeeperClientMakerFactory.getInstance().getZKClusterClient(name).shutdown();
            }
        });
    }

}
