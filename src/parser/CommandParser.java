package parser;

import memorystore.MemoryStore;

import java.util.Scanner;

public class CommandParser {

    private final MemoryStore memoryStore;

    public CommandParser(MemoryStore memoryStore) {
        this.memoryStore = memoryStore;
    }

    public String parseUserInput(String input){

        String method = null;
        boolean methodParsed = false;

        String key = null;
        boolean keyParsed = false;

        String value = null;
        boolean valueParsed = false;

        int expTime = 0;

        String[] tokens = {null, null, null, null};
        tokens = input.split("\\s+");

        String tmpMethod = tokens[0].toUpperCase();
        for(Methods x : Methods.values()){
            if(x.name().equals(tmpMethod)){
                methodParsed = true;
                method = tmpMethod;
                break;
            }
        }

        if(tokens[1] != null) {
            keyParsed = true;
            key = tokens[1];
        }

        if(tokens[2] != null) {
            valueParsed = true;
            value = tokens[2];
        }

        if (Integer.parseInt(tokens[3]) > 0) {
            expTime = Integer.parseInt(tokens[3]);
        }

        if(methodParsed && keyParsed && valueParsed) {

            switch (method){
                case "SET" -> memoryStore.put(key, value, expTime);
                case "GET" -> memoryStore.get(key);
                case "DEL" -> memoryStore.delete(key);
            }
            return "OK";
        } else if(methodParsed && !keyParsed) {
            switch (method) {
                case "SET" -> {
                    return "Err: Key Missing for SET";
                }
                case "GET" -> {
                    return "Err: Key Missing for GET";
                }
                case "DEL" -> {
                    return "Err: Key Missing for DEL";
                }
            }
        } else if(methodParsed && !valueParsed) {
            switch (method) {
                case "SET" -> {
                    return "Err: Value Missing for SET";
                }
                case "GET" -> memoryStore.get(key);
                case "DEL" -> memoryStore.delete(key);
            }
        } else if (!methodParsed){
            return "ERR: Unknown Command or Invalid Method";
        }
        return null;
    }

}
