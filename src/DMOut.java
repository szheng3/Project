
/*
 * Author: Josue Galeas
 * Last Edit: Apr 13, 2016
 * Description: Packages the four distance matrices of DistanceMatrix.
 */

import java.util.List;

public class DMOut {
	private List<List<Integer>> DM;
	private List<List<Integer>> SP0M;
	private List<List<Integer>> XM;
	private List<List<Integer>> wM;

	public DMOut(List<List<Integer>> DM, List<List<Integer>> SP0M, List<List<Integer>> XM, List<List<Integer>> wM) {
		this.DM = DM;
		this.SP0M = SP0M;
		this.XM = XM;
		this.wM = wM;
	}

	public List<List<Integer>> getDM() {
		return DM;
	}

	public List<List<Integer>> getSP0M() {
		return SP0M;
	}

	public List<List<Integer>> getXM() {
		return XM;
	}

	public List<List<Integer>> getwM() {
		return wM;
	}

	public void printAll() {
		System.out.println(">> DM is:");
		ListOps.printMatrix(DM);
		System.out.println(">> SP0M is:");
		ListOps.printMatrix(SP0M);
		System.out.println(">> XM is:");
		ListOps.printMatrix(XM);
		System.out.println(">> wM is:");
		ListOps.printMatrix(wM);
	}
}
