import memorystore.MemoryStore;
import parser.CommandParser;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        MemoryStore memoryStore = new MemoryStore();
        CommandParser commandParser = new CommandParser(memoryStore);

        boolean state = true;

        while(state) {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Input: ");
            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("exit")){
                state = false;
            }

            System.out.println(commandParser.parseUserInput(input));
            memoryStore.printMap();

        }



    }
}