package m2_work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        f.setVirtualHost("/lml");
        //创建连接
        Channel c = f.newConnection().createChannel();
        //定义队列
        c.queueDeclare("hello_world", true, false, false, null);
        //发送消息
        while (true) {
            System.out.println("请输入：");
            String msg = new Scanner(System.in).nextLine();
            //把msg转化喂Bytes发送到hellworld队列中
            c.basicPublish("", "hello_world", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        }

    }
}
