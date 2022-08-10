/**
 * OrderedSinglyLinkedList is a class that provides some of the
 * capabilities required by the List interface using a single 
 * linked list data structure. Only the following methods are 
 * provided: get, add, remove, indexOf, size, toString, and iterator.
 *
 * @author Koffman and Wolfgang, modified by COSC 311
 * @version (1-25-22)
 */

import java.util.*;
public class OrderedSinglyLinkedList <E extends Comparable<E>> implements Iterable<E>{

	// Class Node is defined as an inner class
    /** A Node is the building block for the SinglyLinkedList */
    private static class Node<E> {

        /** Data members */
        private E data;
        /** The link */
        private Node<E> next;

        /**
         * Construct a node with the given data value
         * @param data - The data value 
         */
        public Node(E data) {
            this(data, null);
        }
        
        /**
         * Construct a node with the given data value and link
         * @param data - The data value 
         * @param next - The link
         */
        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
 
    // Data fields
    /** A reference to the head of the list */
    private Node<E> head;
    /** The size of the list */
    private int size;

    // Constructor and methods of SinglyLinkedList class
    // Constructor
    public OrderedSinglyLinkedList () {
    	head = null;
    	size = 0;
    }
    
    // Helper Methods
    /** Insert an item as the first item of the list.
     *	@param item The item to be inserted
     */
    private void addFirst(E item) {
        head = new Node<E>(item, head);
        size++;
    }
    
    /**
     * Add a node after a given node
     * @param node The node which the new item is inserted after
     * @param item The item to insert
     */
    private void addAfter(Node<E> node, E item) {
        node.next = new Node<E>(item, node.next);
        size++;
    }
    
    /**
     * Remove the first node from the list
     * @returns The removed node's data or null if the list is empty
     */
    private E removeFirst() {
        Node<E> temp = head;
        if (head != null) {
            head = head.next;
        }
        if (temp != null) {
            size--;
            return temp.data;
        } else {
            return null;
        }
    }
    
    
    /**
     * Remove the node after a given node
     * @param node The node before the one to be removed
     * @returns The data from the removed node, or null
     *          if there is no node to remove
     */
    private E removeAfter(Node<E> node) {
        Node<E> temp = node.next;
        if (temp != null) {
            node.next = temp.next;
            size--;
            return temp.data;
        } else {
            return null;
        }
    }
    
    /**
     * Find the node at a specified index
     * @param index The index of the node sought
     * @returns The node at index or null if it does not exist
     */
    private Node<E> getNode(int index) {
        Node<E> node = head;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
    }
    
    /**
     * Get the data value at index
     * @param index The index of the element to return
     * @returns The data at index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        return node.data;
    }
    
    /**
     * Insert the specified item at the correct position in the ordered linked list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices)
     * @param item The item to be inserted
	 * @returns none
     */
    public void add (E item)
    {
    		Node <E> curr = head;
    		Node<E> prev = null;
    		while (curr != null && curr.data.compareTo(item) < 0) {
    				prev = curr;
    				curr = curr.next;
    		}
    		if (curr == head)	
    			addFirst(item);
    		else {
    			prev.next = new Node <E> (item,prev.next);
    			size++;	
    		}
    }
    
    /**
     * Search for the target and return the position of the first
     * occurrence, or -1 if it is not in the linked list.
     * @param target  The value to search for
     * @returns The index of the node, or -1 
     */
    public int indexOf (E target) {
    	Node <E> temp = head;
    	int index = 0;
    	
    	while (temp != null && temp.data.compareTo(target)<= 0 ) {
    		if (temp.data.compareTo(target) == 0)
    			return index;
    		
    		temp = temp.next;
    		index ++;
    		
    	}
    	return -1;
    	
    	/*
    	while (temp != null) {
    		if (temp.data.equals(target))
    			return index;
    		else {
    			temp = temp.next;
    			index ++;
    		}
    	}
    	return -1;
    	*/
    }
    
    /**
     * Remove the item at the specified position in the list. Shifts
     * any subsequent items to the left (subtracts one from their
     * indices). Returns the item that was removed.
     * @param index The index of the item to be removed
     * @returns The item that was at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
       // Node<E> removedNode = null;
        if (index == 0) {
            return removeFirst();
        } else {
            Node<E> node = getNode(index - 1);
            return removeAfter(node);
        }
    }
    
    /**
     * Obtain a string representation of the list
     * @param None
     * @return A String representation of the list 
     */
    @Override
    public String toString() {
        String str = new String("[");
        Node<E> p = head;
        if (p != null) {
            while (p.next != null) {
                str = str + p.data.toString();
                str = str + " ==> ";
                p = p.next;
            }
            str = str + p.data.toString();
        }
        str = str + "]";
        return str;
    }
    
    /**
     * Return an iterator over the items in the list
     * @returns An iterator over the items in the list
     */
    @Override
    public Iterator<E> iterator() {
        return new IterImpl<E>(head);
    }
    
    /** Nested class to provide the iterator implementation */
    private class IterImpl<E> implements Iterator<E> {

        /** Reference to the current node */
        private Node<E> current;

        /**
         * Create a new IterImpl starting at a specified node
         * @param node The starting node
         */
        public IterImpl(Node<E> start) {
            current = start;
        }

        /**
         * Returns true if the iteration has more elements.
         * In other words, returns true if next will return
         * an element and not throw an exception.
         * @returns true if the iterator has more elements
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next element in the iteration
         * @returns The next element in the iteration
         * @throws NoSuchElementException if there are no more elements
         */
        @Override
        public E next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            E returnValue = current.data;
            current = current.next;
            return returnValue;
        }

        /**
         * Removes the last item returned by next from the
         * list. This method is not supported by this iterator.
         * @throws UnsupportedOperationException operation not supported
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    } 
    
}
    
  


