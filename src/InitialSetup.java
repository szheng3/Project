
/*
 * Author: Josue Galeas
 * Last Edit: Mar 28, 2016
 * Description: Class for adjusting Mercator data set in relation to the center of mass of the data points, and also converts the data points to HCS coordinates.
 */

import java.util.ArrayList;
import java.util.List;

public class InitialSetup {
	public static List<Coordinate<Integer>> IS(List<Coordinate<Double>> mappedData) {
		List<Coordinate<Integer>> output = new ArrayList<Coordinate<Integer>>();

		int entries = mappedData.size();
		Coordinate<Double> mappedDataCOM = ListOps.getCOM(mappedData);
		double normX, normY;
		Coordinate<Double> temp;

		for (int c = 0; c < entries; c++) {
			normX = mappedData.get(c).getX() - mappedDataCOM.getX();
			normY = mappedData.get(c).getY() - mappedDataCOM.getY();

			temp = new Coordinate<Double>(normX, normY);
			output.add(HCS.cartToHex(temp));
		}

		return output;
	}

	public static void main(String[] args) {
		List<Coordinate<Double>> test1 = MercatorMapping.MM(args[0], false);
		ListOps.getCOM(test1);
		List<Coordinate<Integer>> test2 = IS(test1);

		System.out.println(">> The mapped data has " + test1.size() + " entries.");
		System.out.println(">> The mapped data has COM: ");
		System.out.println(ListOps.getCOM(test1));
		System.out.println(">> The mapped data contains:");
		ListOps.printDoubleCoords(test1);

		System.out.println(">> The HCS data has " + test2.size() + " entries.");
		System.out.println(">> The HCS data contains:");
		ListOps.printIntCoords(test2);
	}
}
