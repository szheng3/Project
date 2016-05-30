/*
 * Author: Josue Galeas
 * Last Edit: Apr 21, 2016
 * Description: Class which uses an MST algorithm and generates the corresponding data structures.
 */

import java.util.ArrayList;
import java.util.List;

public class MSTCalc
{
	public static MSTOut MC(DMOut DMatrices, boolean mstalgo)
	{
		MSTOut output;

		if (mstalgo)
		{
			int[][] graph = ListOps.ll2array(DMatrices.getwM());
			int[] parent = PrimMST.primMST(graph);

			output = primMapper(parent, graph, DMatrices.getDM().size());
		}
		else
			output = Kruskal.MST(DMatrices.getXM(), DMatrices.getwM());

		return output;
	}

	private static MSTOut primMapper(int[] parent, int[][] graph, int entries)
	{
		int w_st = 0;
		List<Coordinate<Integer>> ST = new ArrayList<Coordinate<Integer>>();
		List<List<Integer>> X_st = new ArrayList<List<Integer>>();

		for (int x = 0 ; x < entries; x++)
		{
			X_st.add(new ArrayList<Integer>());

			for (int y = 0; y < entries; y++)
			{
				X_st.get(x).add(0);
			}
		}

		for (int i = 1; i < parent.length; i++)
		{
			X_st.get(parent[i]).set(i, 1);
			X_st.get(i).set(parent[i], 1);
			w_st += graph[parent[i]][i];
		}

		for (int j = 1; j < parent.length; j++)
		{
			if ((parent[j] + 1) < (j + 1))
				ST.add(new Coordinate<Integer>((parent[j] + 1), (j + 1)));
			if ((parent[j] + 1) > (j + 1))
				ST.add(new Coordinate<Integer>((j + 1), (parent[j] + 1)));
		}

		return new MSTOut(w_st, ST, X_st);
	}

	public static void main(String[] args)
	{
		List<Coordinate<Double>> test1 = MercatorMapping.MM(args[0], true);
		List<Coordinate<Integer>> test2 = InitialSetup.IS(test1);
		DMOut test3 = DistanceMatrix.DM(test2);
		MSTOut test4 = MC(test3, false);
		// True is Prim, false is Kruskal

		System.out.println(">> The mapped data has " + test1.size() + " entries.");
		System.out.println(">> The mapped data has COM:");
		System.out.println(ListOps.getCOM(test1));
		System.out.println(">> The mapped data contains:");
		ListOps.printDoubleCoords(test1);

		System.out.println(">> The HCS data has " + test2.size() + " entries.");
		System.out.println(">> The HCS data contains:");
		ListOps.printIntCoords(test2);

		System.out.println(">> The DM is:");
		ListOps.printMatrix(test3.getDM());

		System.out.println(">> The MST information is:");
		test4.printAll();
	}
}
