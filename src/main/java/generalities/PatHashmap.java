package generalities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * A HashMap is a list of Buckets and in each bucket there is a list of key/value object where data is stored.
 * @param <K>
 * @param <V>
 */
public class PatHashmap<K,V> {

    private List<Bucket> buckets;
    private int size;
    private int bucketLoadRatio;

    public PatHashmap(int bucketLoadRatio) {
        this.bucketLoadRatio = bucketLoadRatio;
        buckets = new ArrayList<>();
        buckets.add(null);
    }

    public int nbOfBuckets() {
        return buckets.size();
    }

    public int size() {
        return size;
    }

    private class Bucket {
        final private Node firstNode = new Node();

        public Node getFirstNode() {
            return firstNode;
        }
    }

    private class Node {
        private int hash;
        private K key;
        private V value;
        private Node next;

        boolean hasNext() {
            return next != null;
        }

        public Node getNext() {
            return next;
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

    /**
     * When we insert new 'key/value' data:
     * 1- calculate the key's hashcode using a hashing function.
     * 2- if bucket load would be exceeded, then add a new bucket.
     * 3- reduce hashcode into an int -> this value will be the bucket where data will be stored.
     * 4- store the new data into the bucket
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        int hashCode = hashKey(key);
        checkLoadAndAddBuckets();
        int reduced = reduceHashCode(hashCode, buckets.size());

        Bucket bucket = getBucket(reduced);
        putInBucket(bucket.getFirstNode(), hashCode, key, value);
    }

    public Bucket getBucket(int index) {
        if (buckets.get(index) == null) {
            buckets.set(index, new Bucket());
        }
        return buckets.get(index);
    }

    /**
     * Insert a key/value element into a specific bucket
     * if the bucket is still empty then initialise first node
     * then if key exist in the bucket
     * @param node
     * @param hashCode
     * @param key
     * @param value
     */
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
            putInBucket(node.getNext(), hashCode, key, value);
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
            System.out.println("[checkLoadAndAddBuckets] load '%f' exceed bucket load limit '%d' -> add new bucket".formatted(load, bucketLoadRatio));
            this.buckets.add(new Bucket());
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
        StringBuilder sb = new StringBuilder("HASHMAP: {\n");
        AtomicInteger idx = new AtomicInteger();
        buckets.forEach(bucket -> {
            sb.append("  bucket:%d ".formatted(idx.get())).append(bucketToString(bucket.getFirstNode())).append("\n");
            idx.getAndIncrement();
        });
//        sb.deleteCharAt(0);
        sb.append("} #[size: %d, nbOfBuckets: %d]".formatted(this.size(), this.nbOfBuckets()));
        return sb.toString();
    }

    private String bucketToString(Node node) {
        return "{%s: %s}".formatted(node.key, node.value) + (node.hasNext() ? ", " + bucketToString(node.next) : "");
    }
}
