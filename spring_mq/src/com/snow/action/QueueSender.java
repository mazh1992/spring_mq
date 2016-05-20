package com.snow.action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.*;
import java.io.Serializable;

/**
 * Created by mazhenhua on 2016/5/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext.xml"})
public class QueueSender {

    @Resource
    private JmsTemplate jmsTemplate;

    @Test
    public void send(){
        sendMqMessage(null,"spring activemq queue type message !");
    }

    /**
     * 说明:发送的时候如果这里没有显示的指定destination.将用spring xml中配置的destination
     * @param destination
     * @param message
     */
    public void sendMqMessage(Destination destination, final String message){
        if(null == destination){
            destination = jmsTemplate.getDefaultDestination();
        }
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {

                // 发送文本类型 可以转换成JSON发送出去
                TextMessage textMessage = session.createTextMessage(message);
                // 发送对象 对象是只读的。
                //ObjectMessage objectMessage = session.createObjectMessage(testSendObject);
                // JMS 定义了5中消息类型： TextMessage、MapMessage、BytesMessage、StreamMessage和ObjectMessage
                return textMessage;
            }
        });
        System.out.println("spring send message...");
    }

}
