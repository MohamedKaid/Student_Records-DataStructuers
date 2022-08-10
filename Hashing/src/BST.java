/**
 *  A linked data structure implementation of a binary search tree
 *  @author COSC 311, W '22
 *  @version (3-10-22)
 */

public class BST<E extends Comparable<E>> {
	
	// Class Node is defined as an inner class
	private static class Node <E> {
		
		// data stored in the node
		private E data;
		
		// reference to the root of the left and right subtrees
		private Node<E> left;
		private Node<E> right;
		
		/**
         * Construct a node with the given data value
         * @param item - The data value 
         */
		public Node(E item) {
			data = item;
			left = right = null;
		}
		
		/** 
		 * Return a string representing the node
		 * @param  None  
		 * @return a string representing the data stored in the node  	
		 */
		public String toString () {
			return data.toString();
		}
	}
	
	//data member
	private Node<E> root;
	
	
	/**
     * Construct an empty binary search tree 
     * @param none
     */
	public BST () {
		root = null;
	}

	
	/** 
	 * loops through the Binary search tree and prints it out level by level
	 * @param  none
	 * @return the tree printed out level by level
	 */
	public void levelOrder() {
		levelOrder(root);
	}

	/** 
	 * loops through the Binary search tree using a queue and prints it out level by level
	 * @param  current  the current root
	 * @return the tree printed out level by level
	 */
	private void levelOrder(Node<E> root) {
		QueueSLL<Node<E>> queue = new QueueSLL<>();
		if(root == null) {
			return;
		}else
			queue.offer(root);
			queue.offer(null);
			System.out.println();
			
		while(!queue.empty()) {
			if(queue.peek()==null) {
				System.out.println();
				queue.remove();

				if(queue.peek()!=null) {
					queue.offer(null);
				}
			}
			if(queue.empty()) {
				return;
			}
			Node<E> current = queue.remove();
			System.out.print(current.data +" ");
			if(current.left !=null) {
				queue.offer(current.left);
			}
			if(current.right != null) {
				queue.offer(current.right);
			}
		}
	}
	
	
	/** 
	 * Search for an item in the tree
	 * @param  item  the target value
	 * @return true 
	 */
	public E find (E item) {
		return find(root,item);
	}
	
	/** 
	 * Search for an item in the tree rooted at current
	 * @param  current  the current root
	 * @param  item  the target value
	 * @return true 
	 */
	private E find (Node<E> current , E item) {
		if (current == null)
			return null;
		int result = current.data.compareTo(item);
		if (result == 0)
			return current.data;
		else if (result < 0)
			return find (current.right, item);
		else
			return find (current.left, item);
	}
	
	/** 
	 * Insert an item to the tree
	 * @param  item  the value to be inserted 
	 * @return none 
	 */
	public void add(E item) {
		
			root =  add (root, item);
	}
	
	/** 
	 * Insert an item to the tree rooted at current
	 * @param  current  the current root
	 * @param  item  the value to be inserted 
	 * @return reference to the node that was inserted 
	 */
	private Node<E> add (Node <E>current , E item) {
		if (current == null) 
			current = new Node<>(item);
		else {
            int result = current.data.compareTo(item);
            if (result < 0)
                current.right =  add (current.right, item);
            else if (result > 0)
                current.left =  add (current.left, item);
		}
		return current;
	}
	
	/** 
	 * Traverse the tree using preorder traversal 
	 * @param  none
	 * @return none 
	 */
	public void preorder() {
		preorder (root, 1);
	}
	
	/** 
	 * Traverse the tree using preorder traversal 
	 * @param  current the current root
	 * @param  level the level of the current node
	 * @return none 
	 */
	private void preorder (Node<E> current, int level) {
		if (current != null) {
			for (int i = 1; i  < level; i++ )
				
				System.out.print(" ");
			System.out.print(current);
			if(current.left==null && current.right==null) {
				System.out.println();
			}
			preorder(current.left, level+1);
			preorder(current.right, level+1);
			
		}
	}
	 
	/** 
	 * Return the smallest value in the tree 
	 * @param  none
	 * @return the smallest value 
	 */
	public E min () {
		return min(root);
	}

	/** 
	 * Return the smallest value in the tree  
	 * @param  current the current root
	 * @return the smallest value
	 */
	private E min (Node<E> current) {
		if (current.left == null)
			return current.data;
		else 
			return min(current.left);
	}
	
	/** 
	 * Delete a given item from the tree 
	 * @param  item the item to be deleted
	 * @return none
	 */
	public void delete (E item) {
		root = delete (root,item);
	}
	
	/** 
	 * Delete a given item from the tree 
	 * @param  current the current root
	 * @param  item the item to be deleted
	 * @return a reference to a node 
	 */
	private Node<E> delete(Node<E> current , E item) {
		if (current != null) {
			int result = current.data.compareTo(item);
			if (result < 0)
				current.right =  delete (current.right, item);
			else if (result > 0)
				current.left =  delete (current.left, item);	
			else {    // find it
				if (current.left == null)    // current has 1 child
					current = current.right;
				else if (current.right == null)  
					current = current.left;
				else {   // current has two children
					E replace = min(current.right);
					current.data = replace;
					current.right = delete(current.right, replace);	
				}
			}
		}
		return current;
	}	
}

