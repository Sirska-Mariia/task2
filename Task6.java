import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class Task6 {
    public static void main(String[] args) {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        IMap<String, Integer> map = client.getMap("locking-map");
        map.putIfAbsent("key", 0);

        long startTime = System.currentTimeMillis();
        System.out.println("Starting Optimistic Locking...");

        for (int k = 0; k < 10000; k++) {
            while (true) {
                Integer oldValue = map.get("key");
                Integer newValue = oldValue + 1;
                if (map.replace("key", oldValue, newValue)) {
                    break; 
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Finished! Final value: " + map.get("key"));
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        client.shutdown();
    }
}