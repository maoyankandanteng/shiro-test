package com.hk.test.rabbitmq.publish;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class ProducerPublish {
    public static final String QUEUE_INFORM_EMAIL="queue_inform_email";
    public static final String QUEUE_INFORM_SMS="queue_inform_sms";
    public static final String EXCHANGE_FANOUT_INFORM="exchange_fanout_inform";

    /**
     *
     * 1.Work queues 工作队列
     * 2.Publish/Subscribe发布订阅
     * 3.Routing 路由
     * 4.Topics 通配符
     * 5.Header Header转发器
     * 6.RPC 远程过程调用
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机,一个mq的服务可以设置多个虚拟机，每个虚拟机就相当于一个独立的mq
        connectionFactory.setVirtualHost("/");
        Connection connection=null;
        Channel channel=null;
        try{
            //创建连接
           connection= connectionFactory.newConnection();
           //创建会话通道
            channel = connection.createChannel();
            //声明队列
            //String queue,队列名称
            // boolean durable,是否持久化，mq重启之后队列还在
            // boolean exclusive,是否排他，独占连接,队列只允许在该连接中访问，如果连接关闭，队列自动删除.true 可用于临时队列
            // boolean autoDelete,队列不再使用时，是否自动删除此队列
            // Map<String,Object> arguments 可以设置队列的扩展参数
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);
            //声明交换机

            /**
             * FANOUT publish/subscribe发布订阅
             * direct Routing 路由
             * topic 通配符
             * headers
             */
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            //交换机和队列进行绑定

            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_FANOUT_INFORM,"");
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_FANOUT_INFORM,"");
            //发送消息
            //String exchange,交换机，如果不指定，将使用mq默认交换机
            //routingKey 路由key，交换机根据key来将消息转发到指定的队列，如果使用默认交换机，routingKey要设置成队列的名称
            //BasicProperties props 消息属性
            //byte[] body 消息内容
            for (int i=0;i<5;i++) {
                String content="通知："+i;
                channel.basicPublish(EXCHANGE_FANOUT_INFORM,"",null,content.getBytes());
                System.out.println("send:通知"+i);
            }
        }catch (Exception e){

        }finally {
            //先关闭通道，关闭连接
         channel.close();
          connection.close();
        }
    }
}
