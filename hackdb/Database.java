package hackdb;

/**
 * @Filename:       Database.java
 * @Author:         Matthew Mayo
 * @Modified:       2014-06-18
 * @Description:    
 */


import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.io.*;


class Database {

    private String dbName = "";
    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap();

    public Database(String name) { dbName = name; }

    @Override public String toString() { return dbName; }

    protected void add(String key, String value) { map.put(key, value); }

    protected void del(String key) { map.remove(key); }

    protected String get(String key) { return map.get(key); }

    protected int count() { return map.size(); }

    protected void purge() { map.clear(); }

    protected void sort() { System.out.println(new TreeMap(map)); }    

    protected void showAll() {
        TreeMap sorted = new TreeMap(map);
        Iterator<String> keySetIterator = sorted.keySet().iterator();
        while(keySetIterator.hasNext()) {
            String key = keySetIterator.next();
            System.out.println(key + ", " + sorted.get(key));
        }
    }

    protected void showKeys() {
        TreeMap sorted = new TreeMap(map);
        Iterator<String> keySetIterator = sorted.keySet().iterator();
        while(keySetIterator.hasNext()) {
            String key = keySetIterator.next();
            System.out.println(key);
        }
    }

    protected String minKey() {
        if (map.size() > 0) { return (new TreeMap(map)).firstEntry().toString(); }
        return null;
    }

    protected String maxKey() {
        if (map.size() > 0) { return (new TreeMap(map)).lastEntry().toString(); }
        return null;
    }

    protected boolean hasKey(String key) {
        return map.containsKey(key);
    }

    protected boolean hasValue(String value) {
        return map.containsValue(value);
    }

    protected void save(String filename) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(map);
            out.close();
            System.out.println("Data saved to " + filename);
        }
        catch (Exception e) { System.out.println("Error saving"); }
    }

    protected void load(String filename) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            ConcurrentHashMap temp1 = (ConcurrentHashMap) in.readObject();
            in.close();
            ConcurrentHashMap temp2 = new ConcurrentHashMap();
            temp2.putAll(map);
            map.putAll(temp2);
            map.putAll(temp1);
        }
        catch (Exception e) { System.out.println("Error loading"); }
    }
}
