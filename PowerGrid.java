import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

// Eduard Klimenko TCSS343 HW5
public class PowerGrid {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String s = "fill";
		// re-prompts for input, q to break the loop
		while (true) {
			System.out.println("Enter graph file: (q to quit)");
			s = scan.nextLine();
			if (s.equals("q"))
				break;
			SimpleGraph graph = new SimpleGraph();
			GraphInput.LoadSimpleGraph(graph, s);
			Set<Edge> edgeSets = kruskal(graph);
			System.out.println(printMST(edgeSets));
		}
		scan.close();
		System.out.println("Done.");
	}

	// Kruskal's algorithm using heap
	public static Set<Edge> kruskal(SimpleGraph graph) {
		// heap as priority queue
		PriorityQueue<Edge> q = new PriorityQueue<Edge>(25, new EdgeComparator());

		// add all edges into the queue
		Iterator<Edge> i = graph.edges();
		while (i.hasNext()) {
			Edge e = i.next();
			q.add(e);
		}

		// sets a unique value for the set
		Iterator<Vertex> j = graph.vertices();
		int num = 1;
		while (j.hasNext()) {
			j.next().setData(new Integer(num));
			num++;
		}

		// set up trees and edges
		int[] arrayOfTrees = new int[graph.numVertices() + 1];
		Set<Edge> edgeSets = new TreeSet<Edge>(new EdgeComparator());
		int encounteredEdges = 0;
		while (encounteredEdges < graph.numVertices() - 1) {
			Edge curEdge = q.poll();
			Vertex verta = curEdge.getFirstEndpoint();
			Vertex vertb = curEdge.getSecondEndpoint();

			// find
			int rootX = ((Integer) verta.getData());
			rootX = find(arrayOfTrees, rootX);
			int rootY = ((Integer) vertb.getData());
			rootY = find(arrayOfTrees, rootY);

			// union
			if (union(arrayOfTrees, rootX, rootY)) {
				encounteredEdges++;
				edgeSets.add(curEdge);
			}
		}
		return edgeSets;
	}

	// Find operation, work up the tree to the representative
	public static int find(int[] trees, int node) {
		if (trees[node] < 1)
			return node;
		else
			return find(trees, trees[node]);
	}

	// Union operation, attach root of one tree to the other
	public static boolean union(int[] trees, int rootX, int rootY) {
		if (rootX == rootY)
			return false;
		if (trees[rootX] >= trees[rootY])
			trees[rootX] = rootY;
		else
			trees[rootY] = rootX;
		return true;
	}

	// Prints the minimum spanning tree
	public static String printMST(Set<Edge> edgeSets) {
		StringBuilder builder = new StringBuilder();
		builder.append("Minimum spanning tree: \n");
		builder.append(String.format("%-25s%-25s%-25s%n", "Vertex 1:", "Vertex 2:", "Edge Weight:"));
		double totalWeight = 0;
		for (Edge e : edgeSets) {
			builder.append(String.format("%-25s%-25s%-25.2f%n", e.getFirstEndpoint().getName(),
					e.getSecondEndpoint().getName(), (Double) e.getData()));
			totalWeight += (Double) e.getData();
		}
		builder.append("Total weight: " + totalWeight);
		return builder.toString();
	}
}