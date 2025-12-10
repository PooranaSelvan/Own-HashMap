import java.lang.RuntimeException;
import java.util.ArrayList;
import java.util.Collection;
// import java.util.HashSet;
// import java.util.HashMap;
import java.util.Map;

public class MyHashMap<K, V> {
    int capacity;
    float loadFactor = 0.75f;
    int threshold;
    int count = 0;
    static final int MAXIMUM_CAPACITY = 1073741824;
    // threshold = 16 × 0.75 = 12
    Node<K, V>[] bucket;


    MyHashMap() {
        this.capacity = 16;
        bucket = new Node[capacity];
        this.threshold = (int) (capacity * loadFactor);
    }

    MyHashMap(int capacity) {
        if (capacity <= 0) {
            throw new NullPointerException();
        }

        this.capacity = capacity;
        bucket = new Node[capacity];
        this.threshold = (int) (capacity * loadFactor);
    }

    private class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return key + " : " + value;
        }
    }

    private static class NullKeyException extends RuntimeException {
        NullKeyException() {
            super("Null Key is Not Allowed!");
        }
    }


    private static class SizeException extends RuntimeException {
        SizeException(){
            super("Size Exceeded!");
        }
    }


    private int getIndex(K key) {
        return (key.hashCode() & Integer.MAX_VALUE) % capacity;
    }

    private void checkNullKey(K key) {
        if (key == null) {
            throw new NullKeyException();
        }
    }

    private void resize(){
        if (capacity >= MAXIMUM_CAPACITY) {
            throw new SizeException();
        }

        capacity *= 2;
        threshold = (int)(capacity * loadFactor);

        if (capacity > MAXIMUM_CAPACITY) {
            capacity = MAXIMUM_CAPACITY;
        }

        Node<K, V>[] oldBucket = bucket;
        bucket = new Node[capacity];
        count = 0;

        for (Node<K, V> node : oldBucket) {
            while (node != null) {
                put(node.getKey(), node.getValue());
                node = node.next;
            }
        }
    }

    public V put(K key, V val) {
        if(capacity > MAXIMUM_CAPACITY){
            throw new SizeException();
        } else {
            if((count + 1) > threshold){
                resize();
            }

            checkNullKey(key);

            int index = getIndex(key);
            // System.out.println("Bucket Index : "+index);

            Node<K, V> arr = bucket[index]; // head node
            Node<K, V> currentNode = arr; // copy ^

            while (currentNode != null) {
                if (currentNode.getKey().equals(key)) {
                    currentNode.setValue(val);
                    return currentNode.getValue();
                }

                currentNode = currentNode.next;
            }

            Node<K, V> newNode = new Node<>(key, val);
            newNode.next = arr;
            bucket[index] = newNode;
            count++;

            return newNode.getValue();
        }
    }

    public V putIfAbsent(K key, V val){
        if (capacity > MAXIMUM_CAPACITY) {
            throw new SizeException();
        }


        if((count + 1) > threshold){
            resize();
        }

        checkNullKey(key);

        int index = getIndex(key);

        Node<K, V> arr = bucket[index];
        Node<K, V> currentNode = arr;

        while (currentNode != null) {
            if(currentNode.getKey().equals(key)){
                return null;
            }

            currentNode = currentNode.next;
        }

        Node<K, V> newNode = new Node<K, V>(key, val);
        newNode.next = arr;
        bucket[index] = newNode;
        count++;
        return newNode.getValue();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        if(map == null){
            return;
        }

        if(capacity > MAXIMUM_CAPACITY){
            throw new SizeException();
        }

        for (Map.Entry<? extends K, ? extends V> e : map.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    public V replace(K key, V val){
        checkNullKey(key);

        int index = getIndex(key);

        Node<K, V> arr = bucket[index];
        Node<K, V> currentNode = arr;

        while (currentNode != null) {
            if(currentNode.getKey().equals(key)){
                V oldVal = currentNode.getValue();
                currentNode.setValue(val);
                return oldVal;
            }

            currentNode = currentNode.next;
        }

        return null;
    }

    public boolean replace(K key, V oldVal, V newVal){
        checkNullKey(key);

        int index = getIndex(key);

        Node<K, V> arr = bucket[index];
        Node<K, V> currentNode = arr;

        while (currentNode != null) {
            if(currentNode.getKey().equals(key) && currentNode.getValue().equals(oldVal)){
                return true;
            }

            currentNode = currentNode.next;
        }

        return false;
    }

    public V get(K key) {
        checkNullKey(key);

        int index = getIndex(key);
        // System.out.println("Get Index : " + index);

        Node<K, V> arr = bucket[index];

        while (arr != null) {
            if (arr.getKey().equals(key)) {
                return arr.getValue();
            }

            arr = arr.next;
        }

        return null;
    }

    public V remove(K key) {
        checkNullKey(key);

        int index = getIndex(key);

        Node<K, V> head = bucket[index];
        Node<K, V> prev = null;

        while (head != null) {
            if (head.getKey().equals(key)) {
                if (prev != null) {
                    prev.next = head.next;
                } else {
                    bucket[index] = head.next;
                }
                count--;
                return head.getValue();
            }

            prev = head;
            head = head.next;
        }

        return null;
    }

    public V remove(K key, V val) {
        checkNullKey(key);

        int index = getIndex(key);

        Node<K, V> head = bucket[index];
        Node<K, V> prev = null;

        while (head != null) {
            if (head.getKey().equals(key) && head.getValue().equals(val)) {
                if (prev != null) {
                    prev.next = head.next;
                } else {
                    bucket[index] = head.next;
                }
                count--;
                return head.getValue();
            }

            prev = head;
            head = head.next;
        }

        return null;
    }

    public Collection<V> values(){
        Collection<V> res = new ArrayList<>();

        for (int i = 0; i < bucket.length; i++) {
            Node<K, V> node = bucket[i];

            while (node != null) {
                res.add(node.getValue());
                node = node.next;
            }
        }

        return res;
    }

    public MyHashSet<K> keySet(){
        MyHashSet<K> hashSet = new MyHashSet<>();

        for (Node<K, V> node : bucket) {
            while (node != null) {
                hashSet.add(node.getKey());
                node = node.next;
            }
        }

        return hashSet;
    }

    public boolean containsKey(K key){
        checkNullKey(key);

        int index = getIndex(key);

        Node<K, V> arr = bucket[index];

        while (arr != null) {
            if(arr.getKey().equals(key)){
                return true;
            }

            arr = arr.next;
        }

        return false;
    }

    public boolean containsValue(V val){

        for(int i = 0; i < bucket.length; i++){
            Node<K, V> node = bucket[i];

            while (node != null) {
                if(node.getValue().equals(val)){
                    return true;
                }

                node = node.next;
            }
        }

        return false;
    }

    public MyHashMap<K, V> clone(){
        MyHashMap<K, V> newMap = new MyHashMap<>(capacity);

        for(int i = 0; i < bucket.length; i++){
            Node<K, V> node = bucket[i];

            while (node != null) {
                newMap.put(node.getKey(), node.getValue());
                node = node.next;
            }
        }

        return newMap;
    }

    public void clear(){
        capacity = 16;
        bucket = new Node[capacity];
        count = 0;
        threshold = (int)(capacity * loadFactor);
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    @Override
    public String toString() {
        String res = "";
        res += "capacity = " + capacity + ", count = " + count + "\n";


        res += "[";
        for (int i = 0; i < bucket.length; i++) {
            Node<K, V> node = bucket[i];

            if (node == null) {
                res += "null";
                if(i < bucket.length - 1){
                    res += ", ";
                }
            } else {
                while (node != null) {
                    res += node.key + " : " + node.value + ", ";

                    node = node.next;

                    if (node != null) {
                        res += " -> ";
                    }
                }
            }
        }
        res += "]";
        return res;
    }
}