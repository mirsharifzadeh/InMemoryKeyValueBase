package memorystore;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryStore {

    private final ConcurrentHashMap<String, ValueEntry> hashMap;

    public MemoryStore() {
        this.hashMap = new ConcurrentHashMap<>();
    }

    public void put(String key, String value, long time){
        ValueEntry valueEntry = new ValueEntry(value, System.currentTimeMillis() + (time * 1000));
        hashMap.put(key, valueEntry);
    }

    public void put(String key, String value) {
        ValueEntry valueEntry = new ValueEntry(value);
        hashMap.put(key, valueEntry);
    }

    public ValueEntry get(String key){
        ValueEntry value = hashMap.get(key);
        if(value != null) {

            if (value.getExpireAtTimeStamp() < System.currentTimeMillis() && value.getExpireAtTimeStamp() != 0) {
                hashMap.remove(key);
                return null;
            }
            return value;
        } else {
            return null;
        }
    }

    public void delete(String key){
        if(hashMap.get(key) != null) {
            hashMap.remove(key);
        }
    }

    public void printMap() {
        System.out.println(hashMap);
    }
}
