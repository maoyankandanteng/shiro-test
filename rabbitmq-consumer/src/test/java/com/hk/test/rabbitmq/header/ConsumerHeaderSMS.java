package com.hk.test.rabbitmq.header;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;

/**
 * 入门程序---消费者
 */
public class ConsumerHeaderSMS {
    public static final String QUEUE_INFORM_SMS="queue_inform_sms";
    public static final String EXCHANGE_HEADERS_INFORM="exchange_headers_inform";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机,一个mq的服务可以设置多个虚拟机，每个虚拟机就相当于一个独立的mq
        connectionFactory.setVirtualHost("/");
        Connection connection=null;
        try{
            //创建连接
            connection= connectionFactory.newConnection();
            //创建会话通道
            Channel channel = connection.createChannel();
            //声明队列
            //String queue,队列名称
            // boolean durable,是否持久化，mq重启之后队列还在
            // boolean exclusive,是否排他，独占连接,队列只允许在该连接中访问，如果连接关闭，队列自动删除.true 可用于临时队列
            // boolean autoDelete,队列不再使用时，是否自动删除此队列
            // Map<String,Object> arguments 可以设置队列的扩展参数
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);

            channel.exchangeDeclare(EXCHANGE_HEADERS_INFORM, BuiltinExchangeType.HEADERS);
            Map<String,Object> headers=new Hashtable<>();
            headers.put("inform_sms","sms");
            //交换机和队列进行绑定
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_HEADERS_INFORM,"",headers);


            //监听队列
            DefaultConsumer defaultConsumer=new DefaultConsumer(channel){
                /**
                 *
                 * @param consumerTag 飙戏消费者
                 * @param envelope 信封
                 * @param properties 消息属性
                 * @param body 消息内容
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    long deliveryTag = envelope.getDeliveryTag();//消息id
                    String exchange = envelope.getExchange();
                    String routingKey = envelope.getRoutingKey();
                    String message=new String(body, StandardCharsets.UTF_8);
                    System.out.println("短信消息:"+message+",路由key:"+routingKey);
                }
            };
            /**
             * boolean autoAck 自动回复
             * consumer callback 消费方法，当消费者接受到消息要执行的方法
             */
            channel.basicConsume(QUEUE_INFORM_SMS,true,defaultConsumer);
            //监听，长连接
        }catch (Exception e){

        }
    }
}
