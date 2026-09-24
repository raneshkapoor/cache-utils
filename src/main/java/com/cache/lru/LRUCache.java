package com.cache.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom LRU Cache Implementation - Least Recently Used
 *
 * Time Complexity:
 *  - get(): O(1)
 *  - put(): O(1)
 *  - Eviction: O(1)
 *
 * Space Complexity: O(capacity)
 *
 * Implementation uses:
 * 1. HashMap - for O(1) lookups
 * 2. DoublyLinkedList - for O(1) insertion/deletion and order
 */
public class LRUCache<K, V> {

    /**
     * Internal private class for each Node
     */
    private static class Node<K, V> {

        K key;
        V value;
        Node<K, V> next;
        Node<K, V> prev;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final Node<K, V> head;
    private final Node<K, V> tail;

    /**
     * Initialize LRU cache with given capacity
     *
     * @param capacity - maximum number of entries
     */
    public LRUCache(int capacity) {

        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Method to get cache value for a key.
     * Return null if key not exists, or the cache value for that key.
     * If cache is found, move the value to most recently used.
     *
     * @param key - key for which cache value is required
     * @return - Value of type V for the key, or null if key not exists
     */
    public V get(K key) {

        if (!cache.containsKey(key)) {
            return null;
        }

        Node<K, V> node = cache.get(key);

        removeNode(node);
        addToHead(node);

        return node.value;
    }

    /**
     * Method for adding key and value to the cache.
     * If the cache size is full, evict the least recently used and add the key value.
     * If the key already exists in cache, update the cache value for that key.
     * Move the added value to the most frequently used.
     *
     * @param key - key for the value
     * @param value - value to be put in cache
     */
    public void put(K key, V value) {

        // If cache already exists for given key, update the value and move to Most Recently Used
        if (cache.containsKey(key)) {
            Node<K, V> node = cache.get(key);
            node.value = value;
            removeNode(node);
            addToHead(node);
            return;
        }

        // If cache is full, evict the least recently used
        if (cache.size() == capacity) {
            Node<K, V> last = tail.prev;
            removeNode(last);
            cache.remove(last.key);
        }

        // Add the new value to the Most Frequently Used

        Node<K, V> node = new Node<>(key, value);

        addToHead(node);
        cache.put(key, node);
    }

    /**
     * Internal method to remove a node, or an entry from cache
     *
     * @param node - Node to be removed
     */
    private void removeNode(Node<K, V> node) {

        Node<K, V> prev = node.prev;
        Node<K, V> next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    /**
     * Internal method to move the most recently used node to starting
     *
     * @param node - Node to be moved
     */
    private void addToHead(Node<K, V> node) {
        node.next = head.next;
        head.next.prev = node;
        node.prev = head;
        head.next = node;
    }

}
