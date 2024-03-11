package key.value.store;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String args[])
    {
        InMemoryDb keyValueStore = new InMemoryDb();

//        while (true) {
//            Scanner scanner = new Scanner(System.in);
//            String i = scanner.nextLine();
//            if ("get".equalsIgnoreCase(i)) {
//                System.out.println(myDB.get(scanner.nextLine()));
//            } else if ("put".equalsIgnoreCase(i)) {
//                String key = scanner.nextLine();
//                Map<String, String> attributes = new HashMap<>();
//                while (true) {
//                    String attK = scanner.nextLine();
//                    if ("exit".equalsIgnoreCase(attK)) break;
//                    String attV = scanner.nextLine();
//                    attributes.put(attK, attV);
//                }
//                myDB.put(key, attributes.entrySet().stream().collect(Collectors.toList()));
//            } else if ("delete".equalsIgnoreCase(i)) {
//                myDB.delete(scanner.nextLine());
//            } else if ("search".equalsIgnoreCase(i)) {
//                String attK = scanner.nextLine();
//                String attV = scanner.nextLine();
//                System.out.println(myDB.search(attK, attV));
//            } else if ("keys".equalsIgnoreCase(i)) {
//                System.out.println(myDB.keys());
//            } else if ("exit".equalsIgnoreCase(i)) {
//                break;
//            }
//        }

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit && scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("\\s+");

            switch (tokens[0]) {
                case "get":
                    String getKey = tokens[1];
                    HashMap<String, Object> getValue = keyValueStore.get(getKey);
                    if (getValue != null) {
                        System.out.println(getValue.toString());
                    } else {
                        System.out.println("null");
                    }
                    break;
                case "put":
                    String putKey = tokens[1];
                    List<Pair<String, String>> putAttributes = new ArrayList<>();
                    for (int i = 2; i < tokens.length; i += 2) {
                        putAttributes.add(new Pair<>(tokens[i], tokens[i + 1]));
                    }
                    keyValueStore.put(putKey, putAttributes);
                    break;
                case "delete":
                    String deleteKey = tokens[1];
                    keyValueStore.delete(deleteKey);
                    break;
                case "search":
                    String searchAttributeKey = tokens[1];
                    String searchAttributeValue = tokens[2];
                    List<String> searchResult = keyValueStore.search(searchAttributeKey, searchAttributeValue);
                    System.out.println(String.join(", ", searchResult));
                    break;
                case "keys":
                    List<String> keys = keyValueStore.keys();
                    System.out.println(String.join(", ", keys));
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }

    }
}
