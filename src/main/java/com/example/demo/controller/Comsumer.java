package com.example.demo.controller;

import com.example.demo.config.RabbitConfig;
import java.io.IOException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;

@Component
public class Comsumer {


  @RabbitHandler
  @RabbitListener(queues = RabbitConfig.TEST_QUEUE)
  public void aa(String nnnn,Channel channel, Message message){
    try {
      channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    } catch (IOException e) {
      e.printStackTrace();
      //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);

    }

    System.out.println(nnnn);
  }
}
