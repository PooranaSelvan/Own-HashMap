import java.util.Iterator;

public class MyHashSet<K> implements Iterable<K>{
    int size = 0;
    int capacity;
    float loadFactor = 0.75f;
    int threshold;


    Node<K>[] bucket;
    @SuppressWarnings("rawtypes")
    MyHashMap myhashMap = new MyHashMap<>();


    @SuppressWarnings("unchecked")
    MyHashSet(){
        this.capacity = 16;
        bucket = new Node[capacity];
        this.threshold = (int) (capacity * loadFactor);
    }

    @SuppressWarnings("hiding")
    private class Node<K> {
        K key;
        Node<K> next;

        Node(K key){
            this.key = key;
            this.next = null;
        }

        public K getKey(){
            return this.key;
        }

        public String toString(){
            return "Key : "+this.key;
        }
    }

    private static class NullKeyException extends RuntimeException {
        NullKeyException() {
            super("Null Key is Not Allowed!");
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

    @SuppressWarnings("unchecked")
    private void resize(){
        capacity *= 2;
        threshold = (int)(capacity * loadFactor);

        Node<K>[] oldBucket = bucket;
        bucket = new Node[capacity];
        size = 0;

        for (Node<K> node : oldBucket) {
            while (node != null) {
                add(node.getKey());
                node = node.next;
            }
        }
    }

    public void add(K key){
        if((size + 1) > threshold){
            resize();
        }

        checkNullKey(key);
        int index = getIndex(key);

        Node<K> arr = bucket[index]; // head node
        Node<K> currentNode = arr;

        while (currentNode != null) {
            if(currentNode.getKey().equals(key)){
                currentNode.key = key;
            }

            currentNode = currentNode.next;
        }

        Node<K> newKeyNode = new Node<K>(key);
        newKeyNode.next = arr;
        bucket[index] = newKeyNode;
        size++;
    }

    @Override
    public Iterator<K> iterator() {

        return new Iterator<K>() {
            int bucketIndex = 0;
            Node<K> currentNode = null;


            @Override
            public boolean hasNext() {
                while (currentNode == null && bucketIndex < bucket.length) {
                    currentNode = bucket[bucketIndex++];
                }

                return currentNode != null;
            }

            @Override
            public K next() {
                K key = currentNode.getKey();
                currentNode = currentNode.next;
                return key;
            }

        };
    }

    @Override
    public String toString(){
        String res = "";

        res += "{";

        for (Node<K> node : bucket) {
            while (node != null) {
                res += node + ", ";
                node = node.next;
            }
        }

        res += "}";


        return res;
    }
}