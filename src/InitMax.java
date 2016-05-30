
/*
 * Author: Aadil Ahamed, Josue Galeas
 * Last Edit: Apr 13, 2016
 * Description: Port of InitMax.m
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitMax {
	private static boolean D = false; // Debug flag

	private InitMax() {
	}

	/*
	 * reorient: Reorient Coordinates with respect to (w.r.t) Center of Mass
	 * (COM)
	 * 
	 * @param target target coordinate
	 * 
	 * @param com COM coordinate
	 * 
	 * @return Coordinate<Double> target coordinate w.r.t COM
	 */
	private static Coordinate<Double> reorient(Coordinate<Double> target, Coordinate<Double> com) {
		return Coordinate.minus(target, com);
	}

	/*
	 * convertToHCS: Convert all coordinates to HCS w.r.t COM
	 * 
	 * @param listOfCoords List of cartesian coordinates
	 * 
	 * @param com COM coordinate
	 * 
	 * @return List of HCS coordinates
	 */
	private static List<Coordinate<Integer>> convertToHCS(List<Coordinate<Double>> listOfCoords,
			Coordinate<Double> com) {
		List<Coordinate<Integer>> HCSCoords = new ArrayList<Coordinate<Integer>>();
		for (Coordinate<Double> c : listOfCoords) {
			HCSCoords.add(HCS.cartToHex(reorient(c, com)));
		}
		return HCSCoords;
	}

	private static List<List<Integer>> DMCalc1(List<Coordinate<Integer>> a) {
		List<List<Integer>> output = new ArrayList<List<Integer>>();
		int entries = a.size();
		int distance;

		for (int i = 0; i < entries; i++) {
			output.add(new ArrayList<Integer>());
			for (int j = 0; j < entries; j++) {
				output.get(i).add(0);
			}
		}
		for (int x = 0; x < entries; x++) {
			for (int y = (x + 1); y < entries; y++) {
				distance = DistanceMatrix.HCSDistance(a.get(x), a.get(y));
				output.get(x).set(y, distance);
				output.get(y).set(x, distance);
			}
		}

		return output;
	}

	private static List<List<Integer>> DMCalc2(List<Coordinate<Integer>> a) {
		List<List<Integer>> output = new ArrayList<List<Integer>>();
		int entries = a.size();
		int distance;

		for (int i = 0; i < entries; i++) {
			output.add(new ArrayList<Integer>());
			for (int j = 0; j < entries; j++) {
				output.get(i).add(GlobalConstants.TRANS_RANGE);
			}
		}
		for (int x = 0; x < entries; x++) {
			for (int y = (x + 1); y < entries; y++) {
				distance = DistanceMatrix.HCSDistance(a.get(x), a.get(y));
				output.get(x).set(y, distance);
			}
		}

		return output;
	}

	/*
	 * mergeDistMat: Merge new distance matrix with old distance matrix in order
	 * to prevent picking nodes that are already connected
	 * 
	 * @param oldMat old distance matrix
	 * 
	 * @param newMat new distance matrix
	 * 
	 * @return merged distance matrix
	 */
	private static List<List<Integer>> mergeDistMat(List<List<Integer>> oldMat, List<List<Integer>> newMat) {
		for (int i = 0; i < oldMat.size(); i++) {
			for (int j = 0; j < oldMat.get(i).size(); j++) {
				if (oldMat.get(i).get(j) == GlobalConstants.H) {
					newMat.get(i).set(j, GlobalConstants.H);
				}
			}
		}
		return newMat;
	}

	private static List<List<Integer>> mergeDistMat2(List<List<Integer>> oldMat, List<List<Integer>> newMat) {
		for (int i = 0; i < oldMat.size(); i++) {
			for (int j = (i + 1); j < oldMat.get(i).size(); j++) {
				if (oldMat.get(i).get(j) == GlobalConstants.H) {
					newMat.get(i).set(j, GlobalConstants.H);
					newMat.get(j).set(i, GlobalConstants.H);
				}
			}
		}
		return newMat;
	}

	private static List<Coordinate<Integer>> primPrep(int[] a) {
		List<Coordinate<Integer>> output = new ArrayList<Coordinate<Integer>>();

		for (int i = 1; i < a.length; i++) {
			if ((a[i] + 1) < (i + 1))
				output.add(new Coordinate<Integer>((a[i] + 1), (i + 1)));
			if ((a[i] + 1) > (i + 1))
				output.add(new Coordinate<Integer>((i + 1), (a[i] + 1)));
		}

		return output;
	}

	/*
	 * initMax: Port of Initate_MAX.m
	 * 
	 * @param listOfCoords list of coordinates
	 * 
	 * @param com center of mass
	 * 
	 * @param oldDistMat old distance Matrix
	 * 
	 * @return minimum spanning tree
	 */
	public static InitMaxOut initMax(List<Coordinate<Double>> listOfCoords, Coordinate<Double> com,
			List<List<Integer>> oldDistMat, boolean qMST) {
		List<Coordinate<Integer>> HCSCoords = convertToHCS(listOfCoords, com);
		List<List<Integer>> newDistMat = DMCalc2(HCSCoords);
		List<List<Integer>> WM = DMCalc1(HCSCoords);
		List<Coordinate<Integer>> ST;

		newDistMat = mergeDistMat(oldDistMat, newDistMat);
		WM = mergeDistMat2(oldDistMat, WM);

		List<List<Integer>> XM = InitX(WM.size());

		if (D) {
			for (int i = 0; i < newDistMat.size(); i++) {
				System.out.println(Arrays.toString(newDistMat.get(i).toArray(new Integer[0])));
			}
		}

		if (qMST) {
			int[] mst = PrimMST.primMST(ListOps.ll2array(WM));
			ST = primPrep(mst);
			// PrimMST.printMST(mst, newDistMat[0].length, newDistMat); //debug
			// code - comment out
		} else
			ST = Kruskal.MST(XM, WM).getST();

		return new InitMaxOut(HCSCoords, newDistMat, ST);
	}

	public static void test1() {
		List<Coordinate<Double>> listOfCoords = new ArrayList<Coordinate<Double>>();
		listOfCoords.add(new Coordinate<Double>(1.0, 3.0));
		listOfCoords.add(new Coordinate<Double>(20.0, 20.0));
		listOfCoords.add(new Coordinate<Double>(70.0, 5.0));
		new Coordinate<Double>(0.0, 0.0);
	}

	private static List<List<Integer>> InitX(int a) {
		List<List<Integer>> output = new ArrayList<List<Integer>>();

		for (int x = 0; x < a; x++) {
			output.add(new ArrayList<Integer>());
			for (int y = 0; y < a; y++) {
				if (x != y)
					output.get(x).add(1);
				else
					output.get(x).add(0);
			}
		}

		return output;
	}

	public static void test2() {
		List<Coordinate<Double>> listOfCoords = new ArrayList<Coordinate<Double>>();
		listOfCoords.add(new Coordinate<Double>(1.0, 3.0));
		listOfCoords.add(new Coordinate<Double>(20.0, 20.0));
		listOfCoords.add(new Coordinate<Double>(70.0, 5.0));
		Coordinate<Double> com = new Coordinate<Double>(0.0, 0.0);
		List<Integer> row1 = Arrays.asList(new Integer[] { 2, 2 });
		List<Integer> row2 = Arrays.asList(new Integer[] { 2, 2 });
		List<List<Integer>> oldDistMat = new ArrayList<List<Integer>>();
		oldDistMat.add(row1);
		oldDistMat.add(row2);
		InitMaxOut res = InitMax.initMax(listOfCoords, com, oldDistMat, false);
		System.out.println("MST:");
		ListOps.printIntCoords(res.getST());
	}

	public static void main(String[] args) {
		// InitMax.test1();
		InitMax.test2();
	}
}
