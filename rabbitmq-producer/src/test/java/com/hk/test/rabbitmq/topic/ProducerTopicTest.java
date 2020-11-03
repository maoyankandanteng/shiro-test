package com.hk.test.rabbitmq.topic;

import com.hk.rabbit.RabbitmqApplication;
import com.hk.rabbit.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@SpringBootTest(classes = RabbitmqApplication.class)
@RunWith(SpringRunner.class)
public class ProducerTopicTest {

    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     * 使用rabbitTemplate发送消息
     */
   @Test
    public void test(){
       String ms="消息";
       /**
        * 交换机名称
        * routingKey
        * 消息内容
        */
       rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.email.sms",ms.getBytes());
   }
}
