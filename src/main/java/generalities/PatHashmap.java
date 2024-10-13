package generalities;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Log
public class PatHashmap<K,V> {

    private List<Node> buckets;
    private int size;
    private int bucketLoadRatio;

    public PatHashmap() {
        this.bucketLoadRatio = 5;
        buckets = new ArrayList<>();
        buckets.add(null);
    }

    public int nbOfBuckets() {
        return buckets.size();
    }

    public int size() {
        return size;
    }

    private class Node {
        private int hash;
        private K key;
        private V value;
        private Node next;

        boolean hasNext() {
            return next != null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(",{%s:%s}".formatted(key, value));
            if (hasNext()) {
                sb.append(next.toString());
            }
            return sb.toString();
        }
    }

    public void put(K key, V value) {
        int hashCode = hashKey(key);
        checkLoadAndAddBuckets();
        int reduced = reduceHashCode(hashCode, buckets.size());

        Node bucket = getBucket(reduced);
        putInBucket(bucket, hashCode, key, value);
    }

    public Node getBucket(int index) {
        if (buckets.get(index) == null) {
            buckets.set(index, new Node());
        }
        return buckets.get(index);
    }

    private void putInBucket(Node node, int hashCode, K key, V value) {
        if (node.key == null) {
            node.hash = hashCode;
            node.key = key;
            node.value = value;
            this.size++;
            return;
        }
        if (node.hash == hashCode) {
            node.value = value;
        } else if (node.hasNext()) {
            putInBucket(node.next, hashCode, key, value);
        } else {
            Node newNode = new Node();
            newNode.hash = hashCode;
            newNode.key = key;
            newNode.value = value;
            node.next = newNode;
            this.size++;
        }
    }

    private void checkLoadAndAddBuckets() {
        float load = (float) size() / nbOfBuckets();

        if (load > bucketLoadRatio) {
            log.info("[checkLoadAndAddBuckets] load '%f' exceed limit '%d' -> add new bucket".formatted(load, bucketLoadRatio));
            this.buckets.add(new Node());
        }
    }

    private int hashKey(Object key) {
        return key.hashCode();
    }

    static int reduceHashCode(int hashCode, int nbOfBuckets) {
        return Math.abs(hashCode % nbOfBuckets);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buckets.stream().forEach(node -> sb.append(node.toString()));
        sb.deleteCharAt(0);
        return sb.toString();
    }
}
