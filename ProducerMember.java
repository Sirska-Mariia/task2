import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.collection.IQueue;

public class ProducerMember {
    public static void main(String[] args) throws Exception {
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        IQueue<Integer> queue = hz.getQueue("bounded-queue"); 
        
        System.out.println("Producer started...");
        for (int k = 1; k <= 100; k++) {
            queue.put(k);
            System.out.println("Producing: " + k + " | Queue size: " + queue.size());
            Thread.sleep(1000); 
        }
        
        queue.put(-1);
        System.out.println("Producer Finished!");
    }
}
