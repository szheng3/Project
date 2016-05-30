
/*
 * Author: Josue Galeas
 * Last Edit: Apr 21, 2016
 * Description: Class that gets normalized data set and calculates four distance matrices to be interpreted by the MST algorithm.
 */

import java.util.ArrayList;
import java.util.List;

public class DistanceMatrix {
	public static DMOut DM(List<Coordinate<Integer>> HCSData) {
		List<List<Integer>> DM = new ArrayList<List<Integer>>();
		List<List<Integer>> SP0M = new ArrayList<List<Integer>>();
		List<List<Integer>> XM = new ArrayList<List<Integer>>();
		List<List<Integer>> wM = new ArrayList<List<Integer>>();

		int entries = HCSData.size();
		int distance;

		for (int x = 0; x < entries; x++) {
			DM.add(new ArrayList<Integer>());
			SP0M.add(new ArrayList<Integer>());
			XM.add(new ArrayList<Integer>());
			wM.add(new ArrayList<Integer>());

			for (int y = 0; y < entries; y++) {
				DM.get(x).add(GlobalConstants.TRANS_RANGE);
				SP0M.get(x).add(0);
				if (x != y)
					XM.get(x).add(1);
				else
					XM.get(x).add(0);
				wM.get(x).add(0);
			}
		}

		for (int i = 0; i < entries; i++) {
			for (int j = (i + 1); j < entries; j++) {
				distance = HCSDistance(HCSData.get(i), HCSData.get(j));
				DM.get(i).set(j, distance);
				SP0M.get(i).set(j, distance);
				wM.get(i).set(j, distance);
				wM.get(j).set(i, distance);
			}
		}

		return new DMOut(DM, SP0M, XM, wM);
	}

	public static int HCSDistance(Coordinate<Integer> a, Coordinate<Integer> b) {
		int u = Math.abs(a.getX() - b.getX());
		int v = Math.abs(a.getY() - b.getY());
		int uv = Math.abs(a.getX() - b.getX() + a.getY() - b.getY());

		return Math.max(Math.max(u, v), uv);
	}

	public static void main(String[] args) {
		List<Coordinate<Double>> test1 = MercatorMapping.MM(args[0], false);
		ListOps.getCOM(test1);
		List<Coordinate<Integer>> test2 = InitialSetup.IS(test1);
		DMOut test3 = DM(test2);

		System.out.println(">> The mapped data has " + test1.size() + " entries.");
		System.out.println(">> The mapped data has COM: ");
		System.out.println(ListOps.getCOM(test1));
		System.out.println(">> The mapped data contains:");
		ListOps.printDoubleCoords(test1);

		System.out.println(">> The HCS data has " + test2.size() + " entries.");
		System.out.println(">> The HCS data contains:");
		ListOps.printIntCoords(test2);

		System.out.println(">> The DM is:");
		ListOps.printMatrix(test3.getDM());
	}
}
