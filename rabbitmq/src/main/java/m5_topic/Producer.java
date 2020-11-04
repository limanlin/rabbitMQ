package m5_topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        Connect cn = new Connect();
        Channel c = cn.getChannel("topic_logs", BuiltinExchangeType.TOPIC);

        //发送消息携带路由键
        while (true){
            System.out.println("请输入消息：");
            String msg = new Scanner(System.in).nextLine();
            System.out.println("请输入路由键：");
            String key = new Scanner(System.in).nextLine();
            c.basicPublish("topic_logs",key,null,msg.getBytes());
        }
    }
}
