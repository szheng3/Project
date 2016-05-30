/*
 * Author: Josue Galeas
 * Last Edit: Feb 21, 2016
 * Description: Common operations for the data structures involving Lists.
 */

import java.util.ArrayList;
import java.util.List;

public class ListOps
{
	public static int[][] ll2array(List<List<Integer>> ll)
	{
		int xEntries = ll.size();
		int yEntries = ll.get(0).size();
		int[][] output = new int[xEntries][yEntries];

		for (int x = 0; x < xEntries; x++)
		{
			for (int y = 0; y < yEntries; y++)
			{
				output[x][y] = ll.get(x).get(y);
			}
		}

		return output;
	}

	// Only used once in MSTOut.java
	public static List<List<Integer>> array2ll(int[][] array)
	{
		int xEntries = array.length;
		int yEntries = array[0].length;
		List<List<Integer>> output = new ArrayList<List<Integer>>();

		for (int x = 0; x < xEntries; x++)
		{
			output.add(new ArrayList<Integer>());

			for (int y = 0; y < yEntries; y++)
			{
				output.get(x).add(array[x][y]);
			}
		}

		return output;
	}

	// Only used once in MSTOut.java
	public static int[][] lc2array(List<Coordinate<Integer>> lc)
	{
		int entries = lc.size();
		int[][] output = new int[entries][2];

		for (int c = 0; c < entries; c++)
		{
			output[c][0] = lc.get(c).getX();
			output[c][1] = lc.get(c).getY();
		}

		return output;
	}

	// Only used once in MSTOut.java
	public static List<Coordinate<Integer>> array2lc(int[][] array)
	{
		List<Coordinate<Integer>> output = new ArrayList<Coordinate<Integer>>();

		if (array[0].length != 2)
		{
			System.err.format("ERROR: Input array does not have exactly 2 columns. Use ListOps.array2ll instead.");
			System.exit(0);
			return output;
		}

		for (int c = 0; c < array.length; c++)
		{
			output.add(new Coordinate<Integer>(array[c][0], array[c][1]));
		}

		return output;
	}

	public static Coordinate<Double> getCOM(List<Coordinate<Double>> lc)
	{
		int entries = lc.size();
		double total_x = 0.0, total_y = 0.0;

		for (int c = 0; c < entries; c++)
		{
			total_x += lc.get(c).getX();
			total_y += lc.get(c).getY();
		}

		return new Coordinate<Double>(total_x/entries, total_y/entries);
	}

	public static void printIntCoords(List<Coordinate<Integer>> lc)
	{
		for (int c = 0; c < lc.size(); c++)
		{
			System.out.println(lc.get(c));
		}
	}

	public static void printDoubleCoords(List<Coordinate<Double>> lc)
	{
		for (int c = 0; c < lc.size(); c++)
		{
			System.out.println(lc.get(c));
		}
	}

	public static void printMatrix(List<List<Integer>> ll)
	{
		int xEntries = ll.size();
		int yEntries = ll.get(0).size();

		for (int x = 0; x < xEntries; x++)
		{
			for (int y = 0; y < yEntries; y++)
			{
				System.out.print(ll.get(x).get(y));
				if (y < (yEntries - 1))
					System.out.print(", ");
			}
			System.out.println();
		}
	}
}
