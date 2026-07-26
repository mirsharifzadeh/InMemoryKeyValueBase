package parser;

import memorystore.MemoryStore;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CommandParser {

    private final MemoryStore memoryStore;

    public CommandParser(MemoryStore memoryStore) {
        this.memoryStore = memoryStore;
    }

    public String parseUserInput(String input){

        String response = "Empty Response";

        String method = null;

        String[] tokens;
        tokens = input.split("\\s+");

        if(tokens.length > 0){
            String tmpMethod = tokens[0].toUpperCase();
            for(Methods x : Methods.values()){
                if(tmpMethod.equals(x.name())){
                    method = tmpMethod;
                    break;
                }
            }
            if (method == null) {
                response = "ERR: Invalid or No Method";
            }
        }

        if(method != null) {
            switch (method) {
                case "SET" -> {
                    String key = null;
                    String value = null;
                    int expTime = 0;

                    if(tokens.length >= 2){
                        key = tokens[1];
                    } else {
                        response = "ERR: Key Not Found For SET Method\n";
                    }
                    if(tokens.length >= 3){
                        value = tokens[2];
                    } else {
                        response += "ERR: Value Not Found For SET Method";
                    }

                    if(tokens.length == 4){
                        try {
                            expTime = Integer.parseInt(tokens[3]);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Number Format!");
                        }
                    }

                    if(key != null && value != null){
                        memoryStore.put(key, value, expTime);
                        response = "OK";
                    } else if(value == null){
                        response += "\nERR: Value Is Necessary For SET Method";
                    }
                    if(key == null){
                        response += "\nERR: Key Is Necessary For SET Method";
                    }
                }
                case "GET" -> {
                    String key = null;

                    if(tokens.length >= 2) {
                        key = tokens[1];
                    } else {
                        response = "ERR: Key Not Found For GET Method\n";
                    }

                    if(key != null){
                        if(memoryStore.get(key) != null){
                            response = "OK VALUE: " + memoryStore.get(key).toString();
                        } else {
                            response = "OK, VALUE ERR: Value Expired or Null";
                        }
                    } else {
                        response += "ERR: Key Is Necessary For GET Method";
                    }
                }
                case "DEL" -> {
                    String key = null;

                    if(tokens.length >= 2){
                        key = tokens[1];
                    } else {
                        response = "ERR: Key Not Found For DEL Method\n";
                    }

                    if(key != null){
                        if(memoryStore.get(key) != null){
                            response = "OK, DELETED VALUE: " + memoryStore.showValue(key);
                            memoryStore.delete(key);
                        } else {
                            response = "OK, VALUE ERR: Value Expired or Null";
                        }
                    } else {
                        response += "ERR: Key Is Necessary For DEL Method";
                    }
                }
            }
        }
        return response;
    }
}
