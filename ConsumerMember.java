import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.collection.IQueue;

public class ConsumerMember {
    public static void main(String[] args) throws Exception {
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        IQueue<Integer> queue = hz.getQueue("bounded-queue"); 
        
        System.out.println("Consumer started and waiting for items...");
        while (true) {
            int item = queue.take(); 
            System.out.println("Consumed: " + item);
            
            if (item == -1) {
                queue.put(-1); 
                break;
            }
            Thread.sleep(5000); 
        }
        System.out.println("Consumer Finished!");
    }
}