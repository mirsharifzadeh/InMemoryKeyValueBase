package memorystore;

public class ValueEntry {

    private final String value;
    private final long expireAtTimeStamp;

    public ValueEntry(String value, long expireAtTimeStamp) {
        this.value = value;
        this.expireAtTimeStamp = expireAtTimeStamp;
    }

    public long getExpireAtTimeStamp() {
        return expireAtTimeStamp;
    }

    @Override
    public String toString() {
        if(value == null){
            return "null";
        }
        return this.value;
    }
}
