package project_final_S;
//Class Node for representation of Fibonacci node
class Node 
{
	//public AdjacentNode value;
	public int id_node;
	public int degree;
	public Node left;
	public Node right;
	public Node parent;
	public Node child;
	public boolean mark;
	public boolean visit;
	public int data; //to store the priority
	//Default Constructor
	public Node()
	{
		this.data = Integer.MAX_VALUE;
		this.degree = 0;
		this.left = this;
		this.right = this;
		this.parent = null;
		this.child = null;
		this.mark = false;
		this.id_node = -1;
	}
	//public Node(AdjacentNode value, int data)
	public Node(int data)
	{
		this.id_node = data;
		this.data = Integer.MAX_VALUE;
		this.degree = 0;
		this.left = this;
		this.right = this;
		this.parent = null;
		this.child = null;
		this.mark = false;
	}
}