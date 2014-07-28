package project_final_S;

import java.util.*;

public class Heap {
	Node min;
	int no_nodes;

	Hashtable<Integer, Node> backDoor = new Hashtable<Integer, Node>();

	public Node insertNode(Node x, int data) {
		x.data = data;
		if (min != null) { // add x to rootlist
			x.left = min;
			x.right = min.right;
			min.right = x;
			x.right.left = x;
			if (data < min.data) { // if priority is less make it root
				min = x;
			}
		} else {
			min = x;
		}
		backDoor.put(x.id_node, x);
		no_nodes++;
		return x;
	}

	public void link(Node x, Node y) // make y a child of x, y is the one with
	// higher data value
	{
		y.left.right = y.right; // remove y from root list of heap
		y.right.left = y.left;
		y.parent = x; // x will be parent of y
		if (x.child == null) { // x has no children, rootlist of y alone
			x.child = y;
			y.right = y;
			y.left = y;
		} else { // insert into x's children list
			y.left = x.child;
			y.right = x.child.right;
			x.child.right = y;
			y.right.left = y;
		}
		x.degree++; // increase degree of node x
		y.mark = false; // since we make y a child we clear the mark field (mark
		// = false)
		/*
		 * int i=0; System.out.println("-------------"); for(Node
		 * n=min;i<no_nodes;i++) { System.out.println("-->"+n.id_node);
		 * n=n.right; } System.out.println("-------------");
		 */
	}
	public void consolidate() {
		// double GoldenRatioBound = 1.0 / Math.log((1.0 + Math.sqrt(5.0)) /
		// 2.0);
		int maxDegree = ((int) Math.floor(Math.log(no_nodes)
				* (1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0)))) + 1;
		// maxDegree = no_nodes;
		// int arraySize = ((int) Math.floor(Math.log(nNodes) * oneOverLogPhi))
		// + 1;
		// System.out.println("array size"+maxDegree);
		List<Node> array = new ArrayList<Node>(maxDegree);
		for (int i = 0; i < maxDegree; i++) { // Initialize degree array
			array.add(null);
		}
		int numRoots = 0;
		Node x = min;
		if (x != null) { // get the number of root nodes.
			numRoots++;
			x = x.right; // root +
			while (x != min) { // siblings of root
				// System.out.println("here");
				numRoots++;
				x = x.right;
			}
		}
		// System.out.println("roots: "+numRoots);
		while (numRoots > 0) { // For each node in root list do...
			int d = x.degree; // Access this node's degree..
			Node next = x.right;
			for (;;) {
				// System.out.println("here");
				Node y = array.get(d);
				if (y == null) {
					break;
				}
				// System.out.println("X: "+x.data+ "  "+ y.data);
				if (x.data > y.data) {
					Node temp = y; // Compare keys to decide the parent
					y = x;
					x = temp;
				}
				link(x, y); // y becomes child of x
				array.set(d, null);
				d++;
			}
			array.set(d, x); // Save this node for later when we might encounter
			// another of the same degree.
			x = next;
			numRoots--; // do for until numRoots
		}
		/*
		 * int j=0; System.out.println("abc-------------"); for(Node
		 * n=min;j<no_nodes;j++) { System.out.println("-->"+n.id_node);
		 * n=n.right; } System.out.println("-------------");
		 */
		min = null; // delete old tree and new tree with the new list
		// System.out.println("maxdegree "+maxDegree);
		for (int i = 0; i < maxDegree; i++) {
			Node y = array.get(i);
			if (y == null) {
				// System.out.println("y is null for"+i);
				continue;
			} // System.out.println("min"+min.id_node);
			if (min != null) {
				// System.out.println("min"+min.id_node);
				y.left.right = y.right;
				y.right.left = y.left;
				y.left = min;
				y.right = min.right;
				min.right = y;
				y.right.left = y;
				if (y.data < min.data) {
					min = y;
				}
			} else {
				min = y;
			}
			int k = 0;
			for (Node n = min; k < no_nodes; k++) {
				// System.out.println("--dd->"+n.id_node);
				n = n.right;
			}
		}
	}
	//Takes the input as a node, and cuts it from its parent and inserts the node into the top level list.

	public void cut(Node x, Node y) // cut x from parent y
	{
		x.left.right = x.right; // delete x from the circular list
		x.right.left = x.left;
		y.degree--;
		if (y.child == x) { // if x was child of y,update y.child
			y.child = x.right;
		}
		if (y.degree == 0) { // x is only child
			y.child = null;
		}
		x.left = min; // add x to root list of heap
		x.right = min.right;
		min.right = x;
		x.right.left = x; // correct?
		x.parent = null; // set parent[x] to nil
		x.mark = false; // set mark[x] to false
	}
//Removes the node if more than one child has been cut and adds to the top level list
	public void cascadeCut(Node y) {
		Node x = y.parent;
		if (x != null) { // if there's a parent of y
			if (!y.mark) { // if y is unmarked, set it marked
				y.mark = true;
			} else { // it's marked, cut it from parent
				cut(y, x); // cut its parent as well
				cascadeCut(x);
			}
		}
	}
	//Removes the minimum element from the heap and returns that element. 
	public Node removeMin() {
		Node m = min; // reference of min
		if (m != null) { // non empty heap
			int numChild = m.degree;
			if (m.child != null) {
				Node v = m.child;
				Node RightPtr;
				while (numChild > 0) { // for each child add it in the circular
										// root list
					RightPtr = v.right;
					v.left.right = v.right; // remove x (child of node min) from
					// child list
					v.right.left = v.left;
					v.left = min; // add x to root list of heap
					v.right = min.right;
					min.right = v;
					v.right.left = v;
					v.parent = null; // set parent[x] to null
					v = RightPtr;
					numChild--;
				}
			}
			m.left.right = m.right; // remove z(min) from root list of heap
			m.right.left = m.left;
			if (m == m.right) { // empty heap
				// System.out.println("empty heap");
				min = null;
			} else {
				min = m.right;
				// System.out.println("consolidate");
				consolidate();
			}
			no_nodes--; // decrement size of heap
		}// if
		if (m != null) {
			backDoor.remove(m.id_node);
		}
		return m;
	}// removeMIn
	public void decreaseKey(Node x, int newKey) {
		if (newKey > x.data) {
			return;
		}
		x.data = newKey;
		Node y = x.parent;
		if ((y != null) && (x.data < y.data)) {
			cut(x, y);
			cascadeCut(y);
		}
		if (x.data < min.data) {
			min = x;
		}
	}

	public boolean isEmpty() {
		if (min == null)
			return true;
		return false;
	}

	//Checks if the node is present in the heap.
	public boolean isPresent(int nodeId) {
		return backDoor.containsKey(nodeId);
	}

	//Returns the node from the heap.
	public Node getNode(int nodeId) {
		if (isPresent(nodeId)) {
			return backDoor.get(nodeId);
		}
		return null;
	}
}
