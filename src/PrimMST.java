
/* 
	PrimMST.java: This class uses Prim's algorithm to generate a PrimMST from an adjacency matrix.
	Credit: Code is taken from geeksforgeeks.org: http://www.geeksforgeeks.org/greedy-algorithms-set-5-prims-minimum-spanning-tree-mst-2/
*/

public class PrimMST {
	// Number of vertices in the graph
	private static int V;

	// A utility function to find the vertex with minimum key
	// value, from the set of vertices not yet included in PrimMST
	private static int minKey(int key[], Boolean mstSet[]) {
		// Initialize min value
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < V; v++)
			if (mstSet[v] == false && key[v] < min) {
				min = key[v];
				min_index = v;
			}

		return min_index;
	}

	// A utility function to print the constructed PrimMST stored in
	// parent[]
	public static void printMST(int parent[], int n, int graph[][]) {
		System.out.println("Edge   Weight");
		for (int i = 1; i < V; i++)
			System.out.println(parent[i] + " - " + i + "    " + graph[i][parent[i]]);
	}

	// Converts edges of mst to string
	public static String MSTtoString(int[] parent) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < parent.length; i++) {
			sb.append(parent[i] + " - " + i + "\n");
		}
		return sb.toString();
	}

	// Function to construct and print PrimMST for a graph represented
	// using adjacency matrix representation
	// returns an array of parent pointers. if array[i] = null then i is the
	// root of MST
	public static int[] primMST(int graph[][]) {
		// Array to store constructed PrimMST
		V = graph.length;
		int parent[] = new int[V];

		// Key values used to pick minimum weight edge in cut
		int key[] = new int[V];

		// To represent set of vertices not yet included in PrimMST
		Boolean mstSet[] = new Boolean[V];

		// Initialize all keys as INFINITE
		for (int i = 0; i < V; i++) {
			key[i] = Integer.MAX_VALUE;
			mstSet[i] = false;
		}

		// Always include first 1st vertex in PrimMST.
		key[0] = 0; // Make key 0 so that this vertex is
					// picked as first vertex
		parent[0] = -1; // First node is always root of PrimMST

		// The PrimMST will have V vertices
		for (int count = 0; count < V - 1; count++) {
			// Pick thd minimum key vertex from the set of vertices
			// not yet included in PrimMST
			int u = minKey(key, mstSet);

			// Add the picked vertex to the PrimMST Set
			mstSet[u] = true;

			// Update key value and parent index of the adjacent
			// vertices of the picked vertex. Consider only those
			// vertices which are not yet included in PrimMST
			for (int v = 0; v < V; v++)

				// graph[u][v] is non zero only for adjacent vertices of m
				// mstSet[v] is false for vertices not yet included in PrimMST
				// Update the key only if graph[u][v] is smaller than key[v]
				if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
					parent[v] = u;
					key[v] = graph[u][v];
				}
		}

		// print the constructed PrimMST
		// printMST(parent, V, graph);

		return parent;
	}

	private static void test1() {
		/*
		 * Let us create the following graph 2 3 (0)--(1)--(2) | / \ | 6| 8/ \5
		 * |7 | / \ | (3)-------(4) 9
		 */
		// PrimMST t = new PrimMST();
		int graph[][] = new int[][] { { 0, 2, 0, 6, 0 }, { 2, 0, 3, 8, 5 }, { 0, 3, 0, 0, 7 }, { 6, 8, 0, 0, 9 },
				{ 0, 5, 7, 9, 0 }, };

		// Print the solution
		int[] mst = PrimMST.primMST(graph);
		PrimMST.printMST(mst, PrimMST.V, graph);
	}

	private static void test2() {
		int graph[][] = new int[][] { { 0, 7, 0, 3, 0 }, { 7, 0, 5, 8, 5 }, { 0, 5, 0, 0, 7 }, { 3, 8, 0, 0, 14 },
				{ 0, 5, 7, 14, 0 }, };

		// Print the solution
		int[] mst = PrimMST.primMST(graph);
		PrimMST.printMST(mst, PrimMST.V, graph);
	}

	private static void test3() {
		int graph[][] = new int[][] { { 0, 7, 3 }, { 7, 0, 5 }, { 3, 5, 0 }, };

		// Print the solution
		int[] mst = PrimMST.primMST(graph);
		PrimMST.printMST(mst, PrimMST.V, graph);
	}

	public static void main(String[] args) {
		PrimMST.test1();
		PrimMST.test2();
		PrimMST.test3();
	}
}
