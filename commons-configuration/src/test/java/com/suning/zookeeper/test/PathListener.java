package com.suning.zookeeper.test;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.state.ConnectionState;

import com.suning.zookeeper.ZkNodeData;
import com.suning.zookeeper.client.ZKClusterClient;
import com.suning.zookeeper.listener.ZKConnectionListener;
import com.suning.zookeeper.listener.ZkNodeListener;

public class PathListener implements ZkNodeListener, ZKConnectionListener {

    /* (non-Javadoc)
     * @see com.suning.zookeeper.listener.ZKConnectionListener#stateChanged(com.suning.zookeeper.client.ZKClusterClient, org.apache.curator.framework.state.ConnectionState)
     */
    @Override
    public void stateChanged(ZKClusterClient client, ConnectionState newState) {
        //连接重建时，需要重新注册对节点的监听
        client.registerNodeListener("/test/xieyong", this);
    }

    @Override
    public void childUpdated(ZkNodeData nodeData) {
        System.out.println("我改变了 ，我的值是：" + nodeData.getValue());
    }

    @Override
    public void childAdded(ZkNodeData nodeData) {
        System.out.println("我新增了,我的值是：" + nodeData.getValue());
    }

    @Override
    public void childDeleted(ZkNodeData nodeData) {
        System.out.println("我删除了,我的值是：" + nodeData.getValue());
    }

    @Override
    public void nodeUpdated(ZkNodeData nodeData) {
        System.out.println("我是节点，我改变了!!，我的值是：" + nodeData.getValue());
    }

    /* (non-Javadoc)
     * @see com.suning.zookeeper.listener.ZkNodeListener#accept(com.suning.zookeeper.ZkNodeData)
     */
    @Override
    public boolean accept(ZkNodeData nodeData) {
        return "/test/xieyong".equals(nodeData.getPath())
                || StringUtils.startsWith(nodeData.getPath(), "/test/xieyong");
    }

}