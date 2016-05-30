
/*
 * Author: Josue Galeas
 * Last Edit: Apr 25, 2016
 * Description: Class that maps latitude and longitude values to a Mercator projection, a 2D Cartesian plane.
 */

import java.util.ArrayList;
import java.util.List;

public class MercatorMapping {
	private static final double WGS84_A = 6378137.0;
	private static final double WGS84_F = 1.0 / 298.257223563;
	private static final double WGS84_B = WGS84_A * (1.0 - WGS84_F);
	private static final double SCALE = 100.0;

	public static List<Coordinate<Double>> MM(String inputFile, boolean matlab) {
		List<Coordinate<Double>> output = new ArrayList<Coordinate<Double>>();
		List<Coordinate<Double>> parsedData = ParseData.PD(inputFile, matlab);

		int entries = parsedData.size();
		double mX, mY;

		if (matlab) {
			for (int c = 0; c < entries; c++) {
				mX = parsedData.get(c).getX();
				mY = parsedData.get(c).getY();

				output.add(new Coordinate<Double>(mX, mY));
			}

		} else {
			for (int c = 0; c < entries; c++) {
				mX = lon2mercX(parsedData.get(c).getY());
				mY = lat2mercY(parsedData.get(c).getX());

				output.add(new Coordinate<Double>(mX, mY));
			}
		}

		return output;
	}

	private static double lon2mercX(double lon) {
		return WGS84_A * Math.toRadians(lon) / SCALE;
	}

	private static double lat2mercY(double lat) {
		if (lat > 89.999)
			lat = 89.999;
		if (lat < -89.999)
			lat = -89.999;

		double temp = WGS84_B / WGS84_A;
		double es = 1.0 - (temp * temp);
		double eccent = Math.sqrt(es);
		double phi = Math.toRadians(lat);
		double sin_phi = Math.sin(phi);
		double con = eccent * sin_phi;
		double com = 0.5 * eccent;
		con = Math.pow(((1.0 - con) / (1.0 + con)), com);
		double ts = Math.tan(0.5 * ((Math.PI * 0.5) - phi)) / con;
		double y = 0.0 - WGS84_A * Math.log(ts);

		return y / SCALE;
	}

	public static void main(String[] args) {
		List<Coordinate<Double>> test = MM(args[0], false);
		// True is Matlab, false is GPS

		System.out.println(">> The mapped data has " + test.size() + " entries.");
		System.out.println(">> The mapped data has COM: ");
		System.out.println(ListOps.getCOM(test));
		System.out.println(">> The mapped data contains:");
		ListOps.printDoubleCoords(test);
	}
}
