package key.value.store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryDb {
    ConcurrentHashMap<String, HashMap<String, Object>> inMemoryDb;
    HashMap<String, Column> columnMetadata;

    public InMemoryDb(){
        inMemoryDb = new ConcurrentHashMap<>();
        columnMetadata = new HashMap<>();
    }

    public HashMap<String, Object> get(String key){
        return inMemoryDb.getOrDefault(key, null);
    }

    public List<String> search(String attributeKey, String attributeValue){
        return inMemoryDb.entrySet().stream()
                .filter(e -> e.getValue().containsKey(attributeKey) && e.getValue().get(attributeKey).equals(attributeValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Type parseValue(String value) {
        try {
            Integer.parseInt(value);
            return Type.Integer;

        } catch (NumberFormatException e) {
            try {
                Float.parseFloat(value);
                return Type.Float;

            } catch (NumberFormatException ex) {
                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    return Type.Boolean;
                }
                return Type.String;
            }
        }
    }

    public void put(String key, List<Pair<String, String>> attributes){
        HashMap<String, Object> attributeStore = inMemoryDb.computeIfAbsent(key, k -> new HashMap<>());
        for(Pair<String, String> a: attributes){
            if(columnMetadata.containsKey(a.getFirst())){
                if(columnMetadata.get(a.getFirst()).getType().equals(parseValue(a.getSecond()))){
                    attributeStore.put(a.getFirst(), a.getSecond());
                }
                else{
                    throw new IllegalArgumentException("Type mismatch");
                }
            }
            else{
                columnMetadata.put(a.getFirst(), new Column(a.getFirst(), parseValue(a.getSecond())));
                attributeStore.put(a.getFirst(), a.getSecond());
            }
        }

    }

    public void delete(String key){
        inMemoryDb.remove(key);
    }

    public List<String> keys(){
        return inMemoryDb.keySet().stream().collect(Collectors.toList());
    }


}
