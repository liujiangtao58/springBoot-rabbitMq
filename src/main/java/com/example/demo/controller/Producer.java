package com.example.demo.controller;

import com.example.demo.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/company")
public class Producer implements RabbitTemplate.ReturnCallback {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  @GetMapping("test")
  public void testSendMq() {
    Logger log = LoggerFactory.getLogger(RabbitTemplate.class);

    rabbitTemplate.setReturnCallback(this);
    rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
      if (!ack) {
        System.out.println("HelloSender消息发送失败" + cause + correlationData.toString());
      } else {
        System.out.println("HelloSender 消息发送成功 ");
      }
    });
  /*  rabbitTemplate.setMandatory(true);

    // 消息返回, yml需要配置 publisher-returns: true
    rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
      String correlationId = message.getMessageProperties().getCorrelationId();
      log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
    });*/
    rabbitTemplate.convertAndSend(
        RabbitConfig.TEST_EXCHANGE, RabbitConfig.TEST_QUEUE, "45678998767909878987");
  }

  @Override
  public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
    System.out.println("sender return success" + message.toString() + "===" + replyCode + "===" + replyText + "===" + exchange);

  }
}

