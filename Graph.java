package project_final_S;

import java.io.*;
import java.util.*;

public class Graph {
	private static Hashtable<Integer, ArrayList<AdjacentNode>> adjacencyList = new Hashtable<Integer, ArrayList<AdjacentNode>>();
	private static Hashtable<Integer, ArrayList<AdjacentNode>> adjacencyListMST = new Hashtable<Integer, ArrayList<AdjacentNode>>();
	private static Hashtable<Integer, ArrayList<AdjacentNode>> adjacencyListMSTUndirected = new Hashtable<Integer, ArrayList<AdjacentNode>>();
	
	static int noNodes;
	boolean[] v = null;
	boolean[] visitedFibo;

	private static boolean isAdjacent(int node1, int node2) {
		ArrayList<AdjacentNode> adjList = adjacencyList.get(node1);
		for (AdjacentNode adjNode : adjList) {
			if (adjNode.nodeNumber == node2)
				return true;
		}
		return false;
	}
	
	// Generate a graph randomly
	public void GraphRamdomGenerated(int n, int d)
			throws NumberFormatException, // generate random
			IOException {
		adjacencyList.clear();
		noNodes = n;
		int density = d;
		v = new boolean[noNodes];
		// int density = d;
		int node1, node2, edgewt;
		int edge_count = 0;
		Random rgen = new Random();
		int no_edges = (int) Math.round(((noNodes) * (noNodes - 1) / 2)
				* density / 100); // calculate the number of edges
		// System.out.println("no of edges: " + no_edges);
		while (edge_count < no_edges) {
			edgewt = rgen.nextInt(999) + 1;
			node1 = rgen.nextInt(noNodes);
			node2 = rgen.nextInt(noNodes);
			// add both nodes to the adjacency list ie for 1 add (2,3) then add
			// (1,3) for 2 as well
			if ((node1 != node2)) {
				AdjacentNode adjacentNode = new AdjacentNode(node1, node2,
						edgewt);
				AdjacentNode adjacentNode2 = new AdjacentNode(node2, node1,
						edgewt);
				if (adjacencyList.get(node1) == null) { // add to node 1's
														// adjacency list
					ArrayList<AdjacentNode> adj = new ArrayList<AdjacentNode>();
					adjacencyList.put(node1, adj);
				}
				if (adjacencyList.get(node2) == null) { // add to the node 2's
														// adjacency list
					ArrayList<AdjacentNode> adj = new ArrayList<AdjacentNode>();
					adjacencyList.put(node2, adj);
				}
				if (!isAdjacent(node1, node2)) { // they don't create a loop
					adjacencyList.get(node1).add(adjacentNode);
					adjacencyList.get(node2).add(adjacentNode2);
					edge_count++;
				}
			}
		}
	}

	// Input via the file
	public void GraphFileInput(String args, String args2)
			throws FileNotFoundException {
		Scanner sc = new Scanner(new File(args2)); //scanner class for reading the data from the file
		int no_of_nodes = sc.nextInt();
		int no_of_edges = sc.nextInt();
		noNodes = no_of_nodes;
		while (sc.hasNextInt()) {
			int node1 = sc.nextInt();
			int node2 = sc.nextInt();
			int edgewt = sc.nextInt();
			// same as above
			if ((node1 != node2)) {
				AdjacentNode adjacentNode = new AdjacentNode(node1, node2,
						edgewt);
				AdjacentNode adjacentNode2 = new AdjacentNode(node2, node1,
						edgewt);
				if (adjacencyList.get(node1) == null) {
					ArrayList<AdjacentNode> adj = new ArrayList<AdjacentNode>();
					adjacencyList.put(node1, adj);
				}
				if (adjacencyList.get(node2) == null) {
					ArrayList<AdjacentNode> adj = new ArrayList<AdjacentNode>();
					adjacencyList.put(node2, adj);
				}
				if (!isAdjacent(node1, node2)) {
					adjacencyList.get(node1).add(adjacentNode);
					adjacencyList.get(node2).add(adjacentNode2);
				}
			}
		}
	}
	public void PrimSimpleScheme(String args) {// search for minimum edge in
												// adjancencylist of node
		Hashtable<Integer, Integer> MST = new Hashtable<Integer, Integer>(); // minimum
																				// snapping
																				// tree
		ArrayList<AdjacentNode> Node;
		ArrayList<String> output = new ArrayList<String>();
		Hashtable<Integer, Boolean> visited = new Hashtable<Integer, Boolean>();
		adjacencyListMST.put(0, adjacencyList.get(0)); // start with node 0
		visited.put(0, true);
		int MSTcost = 0;
		int curr = 0;
		while (true) { // keep doing until the adjacency list size is equal to
						// the number of nodes
			Set<Integer> keySet = adjacencyListMST.keySet(); // nodes present in
			int min = Integer.MAX_VALUE;
			AdjacentNode nextTreeNode = null;
			for (Integer NodeId : keySet) {
				Node = adjacencyList.get(NodeId);
				for (AdjacentNode ad : Node) {
					// If adjacent node is already in spanning tree
					// dont consider the weight.
					if (visited.get(ad.nodeNumber) == null) { // check if the
																// node is not
																// visited
						if (ad.weight < min) {
							min = ad.weight;
							nextTreeNode = ad;
							curr = NodeId;
						}
					}
				}
			}
			visited.put(nextTreeNode.nodeNumber, true); // post visitng add true
														// in the hashmap
			// System.out.print("Min "+nextTreeNode.weight);
			MSTcost = MSTcost + min;
			MST.put(nextTreeNode.nodeNumber, curr);
			output.add(curr + " " + nextTreeNode.nodeNumber);
			adjacencyListMST.put(nextTreeNode.nodeNumber,
					adjacencyList.get(nextTreeNode.nodeNumber));
			if (adjacencyListMST.size() == noNodes)
				break;
		}
		if (!args.equals("-r")) { //print only time for random mode
			System.out.println(MSTcost);
			for (String out : output) {
				System.out.println(out);
			}
		}
	}
	public void runDFS(int seed) { // run the dfs on the graph and add to the
									// array of v
		// System.out.println("seed "+seed + "v.size = "+v.length);
		v[seed] = true;
		ArrayList<AdjacentNode> adjacentNodes = adjacencyList.get(seed);
		for (AdjacentNode ad : adjacentNodes) {
			if (!v[ad.nodeNumber]) { // recursively call on each child
				runDFS(ad.nodeNumber);
			}
		}
	}
	public boolean isConnected() { // to check if the graph is connected or not
		int i;
		for (i = 0; i < v.length; i++) {
			if (v[i] == false) {
				System.out.println("the graph isn't connected");
				return false;
			}
		}
		return true;
	}// isConnected
	//Gets the minimum edge for the passed node from the spanning tree existing graph.
	private AdjacentNode getEdgeEnd(Node node1) {
		int minEdgeValue = Integer.MAX_VALUE;
		AdjacentNode minEdge = null;
		ArrayList<AdjacentNode> adjacentNodes = adjacencyList
				.get(node1.id_node); // for each node
		for (AdjacentNode iter : adjacentNodes) // and for each object
		{
			if (!visitedFibo[iter.nodeNumber]) { // not visited
				continue;
			}
			if (iter.weight < minEdgeValue) { // check the weight
				minEdgeValue = iter.weight;
				minEdge = iter;
			}
		}
		return minEdge;
	}
// Generate the MST using 
	public void PrimFHeap(String args) {
		Heap heap = new Heap();
		Node node;
		visitedFibo = new boolean[noNodes];
		int noEdgesAdded = 0;
		AdjacentNode edgeEnd = null;
		long cost = 0;
		heap.insertNode(new Node(0), 0);
		adjacencyListMST.clear();
		while (noEdgesAdded != noNodes) {
			node = heap.removeMin();
			noEdgesAdded++;
			edgeEnd = getEdgeEnd(node);

			adjacencyListMST.put((node.id_node), new ArrayList<AdjacentNode>());
			visitedFibo[node.id_node] = true;
			if (edgeEnd != null) {
				AdjacentNode newNode = new AdjacentNode(edgeEnd.nodeNumber,
						node.id_node, edgeEnd.weight);
				adjacencyListMST.get(edgeEnd.nodeNumber).add(newNode);
				// System.out.println("Between " + edgeEnd.nodeNumber + " " +
				// node.id_node);
				cost += edgeEnd.weight;
			}//end if
			ArrayList<AdjacentNode> adjacentNodes = adjacencyList
					.get(node.id_node);
			for (AdjacentNode iter : adjacentNodes) {
				if (visitedFibo[iter.nodeNumber]) {
					continue;
				}
				if (heap.isPresent(iter.nodeNumber)) {
					heap.decreaseKey(heap.getNode(iter.nodeNumber), iter.weight);
				} else {
					heap.insertNode(new Node(iter.nodeNumber), iter.weight);
				}//end else
			}//end for
		}//end while
		if (!args.equals("-r")) { // dont print when mode is random
			System.out.println(cost);
			Set<Integer> keySet1 = adjacencyListMST.keySet();
			for (Integer i : keySet1) {
				ArrayList<AdjacentNode> adjacentNodes = adjacencyListMST.get(i);
				for (AdjacentNode ad : adjacentNodes) {
					// minCost = minCost + ad.weight;
					System.out.println(i + " " + ad.nodeNumber);// + " ::" + ad.weight);
				
				}// end for
			}//end for
		}//end if
	}// PrimFHeap
}// class graph

class AdjacentNode {
	int sourceNode;
	int nodeNumber;
	int weight;

	public AdjacentNode() {
		this.sourceNode = -1;
		this.nodeNumber = -1;
		this.weight = 0;
	}

	public AdjacentNode(int sourceNode, int nodeNumber, int edgeCost) {
		this.sourceNode = sourceNode;
		this.nodeNumber = nodeNumber;
		this.weight = edgeCost;
	}
}// class AdjacentNode
