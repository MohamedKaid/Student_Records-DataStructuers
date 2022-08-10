/**
 * A partial implementation of a hash table using chaining (binary search trees)
 *  @author COSC 311, W '22
 *  @version (4-5-22)
 *  Note: You must add methods get, remove, and find to this class
 */


@SuppressWarnings ("unchecked")
public class Hashing <E extends Comparable <E>>{
	private final int SIZE= 37;
	private BST<Pair<E>> [] table;

	// constructor 
	public Hashing () {
		//table = (BST<Pair<E>> [])new Object [SIZE];  //*** this doesn't work!
		table = new BST[SIZE];
	}

	// hash the key using division  (replace this hash function whit the one given)
	public int hash(int key) {
		return ((key*key) >> 10) % 37;
	}

	// add a (key,value) pair into the hash table, make sure this method works!
	public void put (Pair<E> item) {
		int key = (Integer) item.getFirst();
		key = hash(key);
		if (table[key] == null) table[key] = new BST<Pair<E>> ();
		table[key].add(item);	
	}	

	// print the hash table using preOrder traversal of BSTs
	public void print(int start, int end) {
		for (int i=start;i<end;i++)
			if (table[i] != null) {
				System.out.print(i + ": ");
				table[i].levelOrder();
			}
	}
	
	public void finalPrint() {
		for (int i=0;i<SIZE;i++)
			if (table[i] != null) {
				System.out.print(i + ": ");
				table[i].preorder();
			}
	}


	public Pair<E> get(E item) {
		for(int i=0;i<SIZE;i++) {
			if(table[i]!=null) {
				return table[i].find((Pair<E>) item);
			}
		}
		return null;
	}

	public Pair<E> remove(E item){
		for(int i=0;i<SIZE;i++) {
			if(table[i]!=null) {
				table[i].delete((Pair<E>) item);
			}
		}
		return null;
	}

	public boolean find(E item) {
		for(int i=0;i<SIZE;i++) {
			if(table[i]!=null) {
				if(table[i].find((Pair<E>) item).equals(true)) {
					return true;
				}
			}
		}
		return false;
	}



}
