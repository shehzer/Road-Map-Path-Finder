import java.io.*;
import java.util.*;
/*
 * CH=0, CV=0, WIDTH =4
 * +F+X+T+  (+F+)(+X+) (+T+)			(0)-  (1)  (2)--(3)
 * FXCXFXF (+F+) (+C+) (+F+)             |     |	|	 |
 * +T+T+F+								(4)-- (5) -(6) -(7)
 * ALREADY BUILD THE NODES
 */

//The following instance variables are initialized
public class RoadMap {
	char toll_rd;
	char pub_rd;
	char rew_rd;

	private int start;
	private int end;
	private int width;
	private int length;
	private int ibudget;
	private int toll;
	private int gain;

	private Graph graph;
	private Stack<Node> stack = new Stack<Node>();

	// The constructor for this class uses the input file to set values for the
	// variables

	public RoadMap(String inputFile) throws MapException {

		try {
			File inputfile = new File(inputFile);
			BufferedReader input = new BufferedReader(new FileReader(inputfile));

			Integer.parseInt(input.readLine()); // Throw away the first line of the input, as we do not need it
			start = Integer.parseInt(input.readLine()); // set the starting vertex as the next val
			end = Integer.parseInt(input.readLine()); // set end as the next value inputed
			width = Integer.parseInt(input.readLine()); // width of the map
			length = Integer.parseInt(input.readLine()); // length of map
			ibudget = Integer.parseInt(input.readLine()); // initial amount of money available
			toll = Integer.parseInt(input.readLine());// amount of money paid for p road
			gain = Integer.parseInt(input.readLine()); // amnt of money gained from reward road

			rew_rd = 'C'; // set each of our variables to the corresponding value in txt file
			toll_rd = 'T';
			pub_rd = 'F';

			// new graph is created using the total number of nodes (width * length)
			Graph mapgraph = new Graph(width * length);
			Stack currStack = new Stack<Node>();

			String line = input.readLine();
			int lineIndex = 0;
			int horIndex = 0;

			// after the variables' values have been set, the program begins to receive
			// further input
			// this input contains the structure of the map, which is then used to create
			// edges between nodes
			// the edges are then stored in the graph
			while (line != null) { // while there is still a char to be read

				// used to keep track of the nodes and non-node elements
				int index = 0;
				while (index < line.length()) {
					Node sNode, eNode;
					if (lineIndex % 2 == 0) { // if its a horizontal line
						sNode = mapgraph.getNode(horIndex + ((index - 1) / 2));
						eNode = mapgraph.getNode(horIndex + ((index - 1) / 2) + 1);
					} else { // vertical
						sNode = mapgraph.getNode(horIndex + (index / 2));
						eNode = mapgraph.getNode(horIndex - width + (index / 2));
					}
					if (line.charAt(index) == toll_rd) { // if its a toll road insert and set type
						mapgraph.insertEdge(sNode, eNode, 1);
					} else if (line.charAt(index) == pub_rd) { // public road insert and set type
						mapgraph.insertEdge(sNode, eNode, 0);
					} else if (line.charAt(index) == rew_rd) { // reward road insert and set type
						mapgraph.insertEdge(sNode, eNode, -1);
					}
					index++; // increment index
				}
				lineIndex++; // increment lineIndex
				if (lineIndex % 2 != 0) {
					horIndex = horIndex + width;
				}
				line = input.readLine();
			}
			input.close();
			graph = mapgraph;
			stack = currStack;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GraphException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Error: " + e);
		}
	}

	public Graph getGraph() {
		return graph;
	}

	public int getStartingNode() {
		return start;
	}

	public int getDestinationNode() {
		return end;
	}

	int getInitialMoney() {
		return ibudget;
	}

	public Iterator findPath(int start, int destination, int initialMoney) throws GraphException {

		// calls recursive helper function ,
		// returns java iterator if it returns true
		// with nodes in the path
		if (searchPathDFS(start, destination, initialMoney))
			return stack.iterator();

		// if helper function returns false, no path exists, return null
		return null;

	}

	/**
	 * checks if a path between start and destination exists given the budget
	 * 
	 * @param start        is the name of the starting node
	 * @param destination  is the name of the destination node
	 * @param initialMoney is initial amount we have
	 * @throws GraphException
	 * @returns true if a path exists given budget, false otherwise
	 */
	private boolean searchPathDFS(int nodeName, int destination, int currentMoney) throws GraphException {

		// gets current node, pushes to stack and marks as true, therefore keeping track
		// of vertices along path dfs folllows

		Node currentNode = graph.getNode(nodeName);
		stack.push(currentNode);
		currentNode.setMark(true);

		// recursive function base case
		// return if destination
		if (nodeName == destination) {
			return true;
		}

		else {

			// gets all the incident edges of the current node
			Iterator<Edge> mapNodes = graph.incidentEdges(currentNode);

			while (mapNodes.hasNext()) {

				// defines road and road type, as well as endpoint to check if it is a marked
				// edge
				Edge road = mapNodes.next();
				int rdType = road.getType();
				Node Endpoint;
				// we must check to determine whether the first is the end point or the second
				// node
				// essentially defining a direction (1) -- (2)E or E(1) -- (2)
				if (currentNode.getName() == road.firstEndpoint().getName())
					Endpoint = road.secondEndpoint();
				else
					Endpoint = road.firstEndpoint();

				if (!Endpoint.getMark()) {

					// updates budget based on road fares
					// recursively searches again using road endpoint as the starting
					// node name, same destination and updated budget
					int budget = updateBal(currentMoney, rdType);
					if (budget >= 0) {
						// only returns true if algorithm returns true, does not return false when
						// algorithm returns false because remaining nodes need to be traversed

						if (searchPathDFS(Endpoint.getName(), destination, budget))
							return true;

					}
				}
			}

			// if no path was found, unmark the current node
			currentNode.setMark(false);
			stack.pop(); // pop it from the stack
			return false; // return false
		}

	}

	/**
	 * private function to update budget based on which road is taken
	 * 
	 * @param currentBal is the current money balance
	 * @param rdType     (private (1), reward(-1), public(0))
	 * @return updated balance for budget
	 */
	private int updateBal(int currentBal, int rdType) {

		// edge type -1 represents reward road
		if (rdType == -1)
			return currentBal + gain;

		// edge type 1 represents private road
		else if (rdType == 1)
			return currentBal - toll;

		// otherwise, edge type is 0, public road -- no addition or sub
		return currentBal;
	}
}
