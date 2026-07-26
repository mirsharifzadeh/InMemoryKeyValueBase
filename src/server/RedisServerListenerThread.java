package server;

import parser.CommandParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisServerListenerThread extends Thread {

    private final int port;
    private final CommandParser commandParser;

    public RedisServerListenerThread(int port, CommandParser commandParser) {
        this.port = port;
        this.commandParser = commandParser;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server starts on port " + port);

        try {
            while(true){
                Socket socket = serverSocket.accept();

                ClientHandlerThread clientHandlerThread = new ClientHandlerThread(socket, commandParser);
                clientHandlerThread.start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Error while closing server socket");
            }
        }
    }

}
