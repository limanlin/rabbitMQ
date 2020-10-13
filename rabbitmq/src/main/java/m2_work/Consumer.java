package m2_work;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setUsername("admin");
        f.setPassword("admin");
        f.setVirtualHost("/lml");
        Channel c = f.newConnection().createChannel();
        c.queueDeclare("hello_world",true,false,false,null);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                byte[] m = delivery.getBody();
                String msg = new String(m);
                long startTime=System.nanoTime();   //获取开始时间
                System.out.println("收到："+msg);
                for (int i = 0; i < msg.length(); i++) {
                    if (msg.charAt(i)=='.'){
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                long endTime=System.nanoTime(); //获取结束时间
                long a =endTime-startTime;
                c.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                System.out.println("消息处理完毕——————————————————————————————耗时："+(a/1000));
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {

            }
        };
        //autoAck设置为false,则需要手动确认发送回执
        c.basicConsume("hello_world",false,
                //处理消息的回调对象
                deliverCallback,
                //取消接受信息回调对象
                cancelCallback);
    }

}
