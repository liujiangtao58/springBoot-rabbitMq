package com.example.demo.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {



  //EXCHANGE
  public static final String TEST_EXCHANGE = "COM.TEST.EXCHANGE";
  //queue
  public static final String TEST_QUEUE = "COM.TEST.QUEUE";



  /**
   * 配置交换机.
   */
  @Bean
  public TopicExchange getExchange() {
    return new TopicExchange(TEST_EXCHANGE);
  }

  /**
   * 配置队列.
   */
  @Bean
  public Queue getQueue() {
    return new Queue(TEST_QUEUE);
  }

  /**
   * 绑定exchange queue routekey.
   *
   * @param queueMessage 队列
   * @param exchange     交换机
   * @param routekey     路由
   * @return
   */
  public Binding bindingExchange(Queue queueMessage, TopicExchange exchange, String routekey) {
    return BindingBuilder.bind(queueMessage).to(exchange).with(routekey);
  }


  /**
   * 绑定队列与exchange.
   *
   * @return
   */
  @Bean
  public Binding agencyChangeBinding() {
    return bindingExchange(
        getQueue(),
        getExchange(),
        RabbitConfig.TEST_QUEUE);
  }

}
