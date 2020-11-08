import java.util.Iterator;
import java.util.ArrayList;
import java.util.*;
public class Graph implements GraphADT{
	private int n = 0;
	ArrayList<Node> nodeList; //stores node of graph
	private Edge[][] adjacencyMatrix; //determine adacently connected nodes
	/**
	 * Constructor for the Graph class
	 * creates a graph with n nodes and no edges
	 * @param n - The number of nodes to create the graph with.
	 */
	Graph(int n) {
		this.n = n;
		//initilize array list and adj matrix
		nodeList  = new ArrayList<Node>();		
		adjacencyMatrix = new Edge[n][n];
		// populate the node list 
		for (int i = 0; i < n; i++) {
			Node newNode = new Node(i);			
			nodeList.add(newNode);
			for (int j = 0; j < n; j++){
				adjacencyMatrix[i][j] = null;
				
			}
		}		
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param name - the name of the node to find.
	 * @return - Gets the node corresponding to the name, if the node not in graph, throw
	 * a Graph Exception.
	 */
	public Node getNode(int name) throws GraphException {
		// check if its out of range.
		if (name > (nodeList.size() - 1)) {
			throw new GraphException();
		}		
		return nodeList.get(name);
	}

	/**
	 * Inserts an edge with w/ given end points and edge type into the graph.
	 * adds an edge of any type connected u and v
	 * if node does not exist or is already in graph throw exception
	 * @param u - First end point. 
	 * @param v - Second end point.
	 * @param edgeType - Type of edge.
	 */
	public void insertEdge(Node u, Node v, int edgeType) throws GraphException {	
		if (u.getName() > (nodeList.size() - 1) || v.getName() > (nodeList.size() - 1)) {
			throw new GraphException();
		}
		else {
			Edge newEdge = new Edge(u, v, edgeType);
			adjacencyMatrix[u.getName()][v.getName()] = newEdge;
			adjacencyMatrix[v.getName()][u.getName()] = newEdge;
		}
	}

	/**
	 * Gets an Iterator containing all the incident edges of the supplied Node.
	 * 
	 * @param u - The Node to get the incident edges for.
	 * @return - Returns an Iterator with all the incident edges of Node u. 
	 */
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		ArrayList<Edge> list = new ArrayList<Edge>(); //create a new array list
		if (u.getName() > (nodeList.size() - 1)) {
			throw new GraphException();
		}		
		else {		
			// gets all the incident edges of node u
			for (int i = 0; i < n; i++){
				if (adjacencyMatrix[u.getName()][i] != null) {
					list.add(adjacencyMatrix[u.getName()][i]);
				}
			}
			//sort it (used when debugging)
			Collections.sort(list, new Comparator<Edge>() {

		        public int compare(Edge e1, Edge e2) {
		            // (-1) - (0) -> 0> -1 good 
		            return e2.getType() - e1.getType();
		        }
		    });
		}		
		return list.iterator();
	}

	/**
	 * 
	 * @param u - First end point. 
	 * @param v - Second end point.
	 * @return -Returns the edge connecting u and v
	 */
	public Edge getEdge(Node u, Node v) throws GraphException {
		if (u.getName() > (nodeList.size() - 1) || v.getName() > (nodeList.size() - 1)) {
			throw new GraphException();
		}
		else return adjacencyMatrix[u.getName()][v.getName()];
		
	}

	/**
	 * Tells if two nodes are adjacent to each other, and returns a true/false statement.
	 * 
	 * @param u - First end point.
	 * @param v - Second end point.
	 * @return - Returns true/false if the provided nodes are adjacent.
	 */
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		if (u.getName() > (nodeList.size() - 1) || v.getName() > (nodeList.size() - 1)) {
			throw new GraphException();
		}
		if (adjacencyMatrix[u.getName()][v.getName()] != null) {
			return true;
		}
		else {
			return false;
		}
	}
	//was used to check if the graph is printing right
	/*public void print() {
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[0].length; j++) {
				if (adjacencyMatrix[i][j] != null)
					System.out.print(adjacencyMatrix[i][j].getType()+" ");
				else
					System.out.print("X ");
			}
			System.out.println("");
		}
	}*/
}


