package m3_publishsubscribe;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setUsername("admin");
        f.setPassword("admin");

        Channel c = f.newConnection().createChannel();
        //定义交换机
        c.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);
        //定义随机队列
        String queue = c.queueDeclare().getQueue();
        //绑定交换机和队列

        c.queueBind(queue,"logs","");
        DeliverCallback deliverCallback = new DeliverCallback() {

            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                byte[] m = delivery.getBody();
                String msg = new String(m);
                System.out.println("收到："+msg);
            }
        };

        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {

            }
        };
        c.basicConsume(queue,true,deliverCallback,cancelCallback);
    }
}
