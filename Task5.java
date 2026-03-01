import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class Task5 {
    public static void main(String[] args) {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        IMap<String, Integer> map = client.getMap("locking-map");
        map.putIfAbsent("key", 0);

        long startTime = System.currentTimeMillis();
        System.out.println("Starting 10k increments with Pessimistic Lock...");

        for (int k = 0; k < 10000; k++) {
            map.lock("key");
            try {
                Integer value = map.get("key");
                value++;
                map.put("key", value);
            } finally {
                map.unlock("key");
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Finished! Final value: " + map.get("key"));
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        
        client.shutdown();
    }
}