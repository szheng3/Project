
/*
 * Author: Josue Galeas
 * Last Edit: Apr 13, 2016
 * Description: Packages the three data structures of MSTCalc.
 */

import java.util.List;

public class MSTOut {
	private int w_st;
	private List<Coordinate<Integer>> ST;
	private List<List<Integer>> X_st;

	public MSTOut(int a, List<Coordinate<Integer>> b, List<List<Integer>> c) {
		w_st = a;
		ST = b;
		X_st = c;
	}

	public int getwst() {
		return w_st;
	}

	public List<Coordinate<Integer>> getST() {
		return ST;
	}

	public int[][] getSTArray() {
		return ListOps.lc2array(ST);
	}

	public List<List<Integer>> getXst() {
		return X_st;
	}

	public int[][] getXstArray() {
		return ListOps.ll2array(X_st);
	}

	public void setwst(int a) {
		w_st = a;
	}

	public void setST(List<Coordinate<Integer>> a) {
		ST = a;
	}

	public void setSTArray(int[][] a) {
		ST = ListOps.array2lc(a);
	}

	public void setXst(List<List<Integer>> a) {
		X_st = a;
	}

	public void setXstArray(int[][] a) {
		X_st = ListOps.array2ll(a);
	}

	public void printwst() {
		System.out.println(">> w_st is:");
		System.out.println(w_st);
	}

	public void printST() {
		System.out.println(">> ST is:");
		ListOps.printIntCoords(ST);
	}

	public void printXst() {
		System.out.println(">> X_st is:");
		ListOps.printMatrix(X_st);
	}

	public void printAll() {
		printwst();
		printST();
		printXst();
	}
}
