package m4_routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setUsername("admin");
        f.setPassword("admin");

        Channel c = f.newConnection().createChannel();
        //定义交换机
        c.exchangeDeclare("direct_logs", BuiltinExchangeType.DIRECT);
        //定义队列，并获取队列名
        String queue = c.queueDeclare().getQueue();
        //定义绑定键
        System.out.println("输入绑定键，用空格隔开");
        String s = new Scanner(System.in).nextLine();
        //使用正则表达式以空格的条件对s进行划分
        String[] a = s.split("\\s");
        //通过绑定键key将交换机和队列进行绑定
        for (String key:a) {
            c.queueBind(queue,"direct_logs",key);
        }
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                byte[] body = delivery.getBody();
                String msg = new String(body);
                String key = delivery.getEnvelope().getRoutingKey();
                System.out.println("收到："+msg+",key:"+key);
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {

            }
        };
        //接收消息
        c.basicConsume(queue,true,deliverCallback,cancelCallback );
    }
}
