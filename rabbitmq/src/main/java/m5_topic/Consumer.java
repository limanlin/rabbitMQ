package m5_topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connect cc = new Connect();
        Channel c = cc.getChannel("topic_logs", BuiltinExchangeType.TOPIC);
        String queue = UUID.randomUUID().toString();
        //队列名，非持久，独占（排他），自动删除，其他参数
        c.queueDeclare(queue,false,true,true,null);
    }
}
