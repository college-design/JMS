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
        ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("TestQueue");

        MessageConsumer consumer = session.createConsumer(destination);
        ObjectMessage message = (ObjectMessage)consumer.receive();
        if (message != null) {
            String messageString = (String)message.getObject();
            System.out.println("Receive : " + messageString);
        }
    }

}