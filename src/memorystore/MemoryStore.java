package memorystore;

import com.sun.jdi.Value;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStore {

    private final ConcurrentHashMap<String, ValueEntry> hashMap;

    public MemoryStore() {
        this.hashMap = new ConcurrentHashMap<>();
    }

    public void put(String key, String value, long time){
        ValueEntry valueEntry;
        if(time == 0){
            valueEntry = new ValueEntry(value, 0);
        } else {
            valueEntry = new ValueEntry(value, System.currentTimeMillis() + (time * 1000));
        }
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

    public ValueEntry showValue(String key){
        ValueEntry value = hashMap.get(key);
        return value;
    }

    public Enumeration<String> getKeys() {
        return hashMap.keys();
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
