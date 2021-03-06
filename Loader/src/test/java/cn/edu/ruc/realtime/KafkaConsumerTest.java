package cn.edu.ruc.realtime;

import cn.edu.ruc.realtime.model.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;

/**
 * RealTimeAnalysis
 *
 * @author Jelly
 */
public class KafkaConsumerTest {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        props.put("value.deserializer", "cn.edu.ruc.realtime.utils.MessageDer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<Long, Message> consumer = new KafkaConsumer<>(props);
        TopicPartition topicPartition = new TopicPartition("07261212", 2);
        consumer.assign(Arrays.asList(topicPartition));
        consumer.seekToBeginning(topicPartition);

        int counter = 0;
        while (true) {
            ConsumerRecords<Long, Message> records = consumer.poll(10000);

            for (ConsumerRecord<Long, Message> record: records) {
                counter++;
//                System.out.println(record.key() + ": " + record.value().getValue());
            }
//            for (ConsumerRecord<Long, Message> record : records) {
//                counter++;
//                System.out.println(record.key() + ": " + record.value().getValue());
//            }
            System.out.println(counter);
        }
    }
}
