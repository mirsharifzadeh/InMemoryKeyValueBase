package server;

import parser.CommandParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandlerThread extends Thread {

    private final Socket socket;
    private final CommandParser commandParser;

    public ClientHandlerThread(Socket socket, CommandParser commandParser) {
        this.socket = socket;
        this.commandParser = commandParser;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while((line = reader.readLine()) != null){
                String response = commandParser.parseUserInput(line);
                writer.println(response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error while closing reader");
                }
            }

            if (writer != null) {
                writer.close();
            }
        }
    }
}
