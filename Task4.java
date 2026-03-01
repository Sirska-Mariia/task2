import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class Task4 {
    public static void main(String[] args) {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        IMap<String, Integer> map = client.getMap("locking-map");
        map.putIfAbsent("key", 0);
        
        System.out.println("Starting 10,000 increments without locks...");
        
        
        for (int k = 0; k < 10000; k++) {
            Integer value = map.get("key");
            value++;
            map.put("key", value);
        }
        
        System.out.println("Finished! Final value in this client: " + map.get("key"));
        client.shutdown();
    }
}