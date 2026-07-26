import memorystore.ExpiredValueCleanerThread;
import memorystore.MemoryStore;
import parser.CommandParser;
import server.RedisServerListenerThread;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        MemoryStore memoryStore = new MemoryStore();
        CommandParser commandParser = new CommandParser(memoryStore);

        ExpiredValueCleanerThread valueCleanerThread = new ExpiredValueCleanerThread(memoryStore);
        valueCleanerThread.start();

        RedisServerListenerThread redisServerListenerThread = new RedisServerListenerThread(6379, commandParser);
        redisServerListenerThread.start();

    }
}
