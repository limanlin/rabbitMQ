package m1_simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        f.setVirtualHost("/lml");

        Channel cc = f.newConnection().createChannel();
        //定义队列
        //告诉服务器想使用的队列
        //服务器检查队列如果不存在，则回新建队列
        cc.queueDeclare("helloworld", false, false, false, null);


        /*生产者和消费者都创建了一个队列的原因是？
        保证存在，两边都创建则不会因为消费者比生产者早启动而导致找不到队列
        * */
        //处理消息的回调对象
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                byte[] a = message.getBody();
                String msg = new String(a);
                System.out.println("收到"+msg);
            }
        };
        // 取消接收信息的回调对象
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {

            }
        };

        //3,消费数据
        cc.basicConsume("helloworld",true,
                //处理消息的回调对象
                deliverCallback,
                //取消接受信息回调对象
                cancelCallback);
    }

}
