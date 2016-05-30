/*
 * Author: Josue Galeas
 * Last Edit: March 28, 2016
 * Description: Class for parsing and converting GPS coordinates or MATLAB coordinates into latitude and longitude format.
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ParseData
{
	private static Scanner scanner;
	private static Scanner scanner2;

	public static List<Coordinate<Double>> PD(String inputFile, boolean matlab)
	{
		List<Coordinate<Double>> output = new ArrayList<Coordinate<Double>>();
		List<String> fileData = ReadFile.RF(inputFile);

		int entries = fileData.size();

		if (matlab)
		{
			double mat1, mat2;

			for (int c = 0; c < entries; c++)
			{
				scanner = new Scanner(fileData.get(c));
				Scanner line = scanner.useDelimiter(",");

				mat1 = Double.parseDouble(line.next());
				mat2 = Double.parseDouble(line.next());

				line.close();
				output.add(new Coordinate<Double>(mat1, mat2));
			}
		}
		else
		{
			int deg1, min1, deg2, min2;
			double sec1, sec2, lat, lon;
			char dir1, dir2;

			for (int c = 0; c < entries; c++)
			{
				scanner2 = new Scanner(fileData.get(c));
				Scanner line = scanner2.useDelimiter("[°\'\" ]");

				deg1 = Integer.parseInt(line.next());
				min1 = Integer.parseInt(line.next());
				sec1 = Double.parseDouble(line.next());
				dir1 = line.next().charAt(0);

				deg2 = Integer.parseInt(line.next());
				min2 = Integer.parseInt(line.next());
				sec2 = Double.parseDouble(line.next());
				dir2 = line.next().charAt(0);

				lat = deg1/1.0 + min1/60.0 + sec1/3600.0;
				if (dir1 == 'S' || dir1 == 's')
					lat *= -1;

				lon = deg2/1.0 + min2/60.0 + sec2/3600.0;
				if (dir2 == 'W' || dir2 == 'w')
					lon *= -1;

				line.close();
				output.add(new Coordinate<Double>(lat, lon));
			}
		}

		return output;
	}

	public static void main(String[] args)
	{
		List<Coordinate<Double>> test = PD(args[0], false);
		// True is Matlab, false is GPS

		System.out.println(">> The parsed data has " + test.size() + " entries.");
		System.out.println(">> The parsed data contains:");
		ListOps.printDoubleCoords(test);
	}
}
