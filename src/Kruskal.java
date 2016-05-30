
/*
 * Author: Josue Galeas
 * Last Edit: Feb 21, 2016
 * Description: Port of kruskal.m, generates the MST from the distance matrix.
 */

import java.util.ArrayList;
import java.util.List;

public class Kruskal {
	public static MSTOut MST(List<List<Integer>> a, List<List<Integer>> b) {
		MSTOut output;
		int w_st = 0;
		List<Coordinate<Integer>> ST = new ArrayList<Coordinate<Integer>>();
		List<List<Integer>> X_st = new ArrayList<List<Integer>>();

		boolean isUndirGraph = true;
		int[][] X_ne = ListOps.ll2array(a);
		int[][] w_ne = ListOps.ll2array(b);

		// "Convert logical adjacent matrix to neighbors' matrix"
		boolean cond1 = a.size() == a.get(0).size();
		boolean cond2 = (Sum(a, 0) + Sum(a, 1)) == (a.size() * a.get(0).size());
		if (cond1 && cond2) {
			if (AnyAny(a))
				isUndirGraph = false;
			X_ne = X2ne(a, isUndirGraph);
		} else {
			// TODO: If input 'a' is already a neighbors' matrix
		}

		// "Convert weight matrix from adjacent to neighbors' form"
		if ((b.size() * b.get(0).size()) != b.size()) {
			if (isUndirGraph && AnyAny(b)) {
				System.err.format("ERROR: Weight matrix must be symmetric if it is an undirected graph.");
				System.exit(0);
			}
			w_ne = w2ne(b, X_ne);
		}

		int N = Max(X_ne); // "Number of vertices"
		int Ne = X_ne.length; // "Number of edges"
		int[][] lidx = new int[Ne][1]; // "Logical edge index, 1 for the edges
										// that will be in the MST"

		// "Sort edges w.r.t. weight"
		int[][] idx = Sort(w_ne);
		X_ne = SortI(X_ne, idx);

		// "Initialize: assign each node to itself"
		List<Integer> repr = new ArrayList<Integer>();
		List<Integer> rnk = new ArrayList<Integer>();
		makeset(N, repr, rnk);

		// "Run Kruskal's algorithm"
		int i, j;
		for (int k = 0; k < Ne; k++) {
			i = X_ne[k][0];
			j = X_ne[k][1];
			if (fnd(i, repr) != fnd(j, repr)) {
				lidx[k][0] = 1;
				union(i, j, repr, rnk);
			}
		}

		// "Form the minimum spanning tree"
		List<Integer> treeidx = new ArrayList<Integer>();
		for (int l = 0; l < Ne; l++) {
			if (lidx[l][0] == 1) {
				treeidx.add(l + 1);
			}
		}
		for (int m = 0; m < treeidx.size(); m++) {
			i = X_ne[(treeidx.get(m) - 1)][0];
			j = X_ne[(treeidx.get(m) - 1)][1];
			ST.add(new Coordinate<Integer>(i, j));
		}

		// "Generate adjacency matrix of the minimum spanning tree"
		for (int x = 0; x < N; x++) {
			X_st.add(new ArrayList<Integer>());

			for (int y = 0; y < N; y++) {
				X_st.get(x).add(0);
			}
		}
		for (int p = 0; p < ST.size(); p++) {
			i = ST.get(p).getX() - 1;
			j = ST.get(p).getY() - 1;
			X_st.get(i).set(j, 1);
			X_st.get(j).set(i, 1);
		}

		// "Evaluate the total weight of the minimum spanning tree"
		for (int q = 0; q < treeidx.size(); q++) {
			w_st += w_ne[treeidx.get(q) - 1][0];
		}

		output = new MSTOut(w_st, ST, X_st);
		return output;
	}

	private static int[][] X2ne(List<List<Integer>> a, boolean b) {
		int[][] output;
		int entries = a.size(), v_entries;
		int total = 0, cnt = 1, uppercnt = 1;
		List<Integer> v = new ArrayList<Integer>();
		List<Coordinate<Integer>> edge = new ArrayList<Coordinate<Integer>>();

		if (b) {
			for (int x = 0; x < entries; x++) {
				for (int y = (x + 1); y < entries; y++) {
					total += a.get(x).get(y);
				}
			}
			output = new int[total][2];
		} else {
			// TODO: If undirected graph
			output = new int[1][2]; // Temporary to avoid errors
		}

		for (int i = 1; i < entries; i++) {
			for (int j = 0; j < entries; j++) {
				if (a.get(i - 1).get(j) != 0)
					v.add(j + 1);
			}

			if (b) {
				v_entries = v.size();
				for (int n = 0; n < v_entries; n++) {
					if (v.get(n) <= i) {
						v.remove(n);
						v_entries--;
						n--;
					}
				}
			}

			for (int k = 0; k < v.size(); k++) {
				edge.add(new Coordinate<Integer>(i, v.get(k)));
			}

			uppercnt = cnt + edge.size() - 1;
			for (int l = cnt; l <= uppercnt; l++) {
				output[l - 1][0] = edge.get(l - cnt).getX();
				output[l - 1][1] = edge.get(l - cnt).getY();
			}

			cnt = cnt + edge.size();
			v.clear();
			edge.clear();
		}

		return output;
	}

	private static int[][] w2ne(List<List<Integer>> a, int[][] b) {
		int entries = b.length;
		int[][] output = new int[entries][1];
		int cnt = 1;

		for (int x = 0; x < entries; x++) {
			output[cnt - 1][0] = a.get(b[x][0] - 1).get(b[x][1] - 1);
			cnt++;
		}

		return output;
	}

	private static void makeset(int a, List<Integer> b, List<Integer> c) {
		for (int x = 0; x < a; x++) {
			b.add(x + 1);
			c.add(0);
		}
	}

	private static int fnd(int a, List<Integer> b) {
		int i = a;

		while (i != b.get(i - 1)) {
			i = b.get(i - 1);
		}

		return i;
	}

	private static void union(int a, int b, List<Integer> c, List<Integer> d) {
		int r_i = fnd(a, c);
		int r_j = fnd(b, c);
		int temp = 0;

		if (d.get(r_i - 1) > d.get(r_j - 1)) {
			c.set((r_j - 1), r_i);
		} else {
			c.set((r_i - 1), r_j);
			if (d.get(r_i - 1) == d.get(r_j - 1)) {
				temp = d.get(r_j - 1);
				d.set(r_j - 1, (temp + 1));
			}
		}
	}

	private static int Sum(List<List<Integer>> a, int b) {
		int output = 0;
		int x_entries = a.size();
		int y_entries = a.get(0).size();

		for (int x = 0; x < x_entries; x++) {
			for (int y = 0; y < y_entries; y++) {
				if (a.get(x).get(y) == b)
					output++;
			}
		}

		return output;
	}

	private static boolean AnyAny(List<List<Integer>> a) {
		boolean output = false;
		int entries = a.size();

		for (int x = 0; x < entries; x++) {
			for (int y = 0; y < entries; y++) {
				if ((a.get(x).get(y) - a.get(y).get(x)) != 0)
					output = true;
			}
		}

		return output;
	}

	private static int Max(int[][] a) {
		int output = 0;
		int x_entries = a.length;
		int y_entries = a[0].length;

		for (int x = 0; x < x_entries; x++) {
			for (int y = 0; y < y_entries; y++) {
				if (a[x][y] > output)
					output = a[x][y];
			}
		}

		return output;
	}

	private static int[][] Sort(int[][] a) {
		int entries = a.length;
		int[][] output = new int[entries][1];
		List<Coordinate<Integer>> data = new ArrayList<Coordinate<Integer>>();
		int startScan, minIndex;
		Coordinate<Integer> minValue;

		for (int x = 0; x < entries; x++) {
			data.add(new Coordinate<Integer>((x + 1), a[x][0]));
		}

		for (startScan = 0; startScan < (entries - 1); startScan++) {
			minIndex = startScan;
			minValue = data.get(startScan);
			for (int index = startScan + 1; index < entries; index++) {
				if (data.get(index).getY() < minValue.getY()) {
					minValue = data.get(index);
					minIndex = index;
				}
			}
			data.set(minIndex, data.get(startScan));
			data.set(startScan, minValue);
		}

		for (int y = 0; y < entries; y++) {
			a[y][0] = data.get(y).getY();
			output[y][0] = data.get(y).getX();
		}

		data.clear();
		return output;
	}

	private static int[][] SortI(int[][] a, int[][] b) {
		int entries = a.length;
		int[][] output = new int[entries][2];

		for (int x = 0; x < entries; x++) {
			output[x][0] = a[b[x][0] - 1][0];
			output[x][1] = a[b[x][0] - 1][1];
		}

		return output;
	}
}
