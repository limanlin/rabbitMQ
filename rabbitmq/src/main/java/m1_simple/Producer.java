package m1_simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");//外网主机域名
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        //如果连接外网的服务器，需要设置指定虚拟主机
        f.setVirtualHost("/lml");
        //创建通道
        Connection cc = f.newConnection();
        Channel c = cc.createChannel();
        //定义队列，指定某个队列
        c.queueDeclare("helloworld",
                false,//是否是持久队列
                false,//是否是排他队列（消费者独占的队列）
                false,//是否自动删除（没有消费者时，自动删除）
                null);//其他属性

        //发送消息
        c.basicPublish("","helloworld",null,//其他参数
                "臭嗨".getBytes());
        System.out.println("消息已发送");
        c.close();
        cc.close();

    }
}
