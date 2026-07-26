package memorystore;

public class ExpiredValueCleanerThread extends Thread {
    private final MemoryStore memoryStore;

    public ExpiredValueCleanerThread(MemoryStore memoryStore) {
        this.memoryStore = memoryStore;
    }

    @Override
    public void run() {

        while(true){
            while(memoryStore.getKeys().hasMoreElements()){
                String key = memoryStore.getKeys().nextElement();
                memoryStore.get(key);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
