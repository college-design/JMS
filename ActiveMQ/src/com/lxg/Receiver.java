package com.lxg;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 点对点
 * Created by 刘雪岗 on 2017/1/21.
 */
public class Receiver {

    public static void main(String[] args) throws JMSException {
        // Create a ConnectionFactory，创建连接工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616");
        // Create a Connection，创建连接
        Connection connection = factory.createConnection();
        //打开连接
        connection.start();

        // Create a Session，创建会话
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);//指定ACK_Mode签收确认模式为自动确认

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("TestQueue");//创建消息目标(点对点模型队列)
        //Destination destination = session.createTopic("TEST.FOO");//创建消息目标(订阅主题)

        // Create a MessageConsumer from the Session to the Topic or Queue//创建消息消费者
        MessageConsumer consumer = session.createConsumer(destination);

        // Wait for a message
        ObjectMessage message = (ObjectMessage)consumer.receive();//接收到达的消息，如果没有收到此方法将阻塞等待直到指定超时时间

        //判断消息类型是否为空
        if (message != null) {
            String messageString = (String)message.getObject();
            System.out.println("Receive : " + messageString);
        }
    }

}