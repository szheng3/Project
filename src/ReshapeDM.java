/*
 * Author: Josue Galeas
 * Last Edit: Mar 20, 2016
 * Description: Methods for reshaping the distance matrix after intermediate nodes are inserted.
 */

import java.util.ArrayList;
import java.util.List;

public class ReshapeDM
{
	public static void Type1(List<List<Integer>> DM, int[] RCV)
	{
		int entries = DM.size();
		int row = RCV[0] - 1;
		int col = RCV[1] - 1;
		List<Integer> temp = new ArrayList<Integer>();

		for (int i = 0; i < entries; i++)
		{
			if ((i == row) || (i == col))
				DM.get(i).add(GlobalConstants.H);
			else
				DM.get(i).add(0);
		}
		for (int j = 0; j < (entries + 1); j++)
		{
			temp.add(GlobalConstants.TRANS_RANGE);
		}
		DM.add(temp);
	}

	public static void Type2(List<List<Integer>> DM, int[] RCV)
	{
		int entries = DM.size();
		int row = RCV[0] - 1;
		int col = RCV[1] - 1;
		List<Integer> temp = new ArrayList<Integer>();

		for (int i = 0; i < entries; i++)
		{
			if ((i == row) || (i == col))
			{
				DM.get(i).add(GlobalConstants.H);
				DM.get(i).add(GlobalConstants.H);
			}
			else
			{
				DM.get(i).add(0);
				DM.get(i).add(0);
			}
		}
		for (int j = 0; j < (entries + 2); j++)
		{
			temp.add(GlobalConstants.TRANS_RANGE);
		}
		DM.add(temp);
		DM.add(temp);
	}

	public static void Type3(List<List<Integer>> DM)
	{
		int entries = DM.size();
		List<Integer> temp = new ArrayList<Integer>();

		for (int i = 0; i < entries; i++)
		{
			DM.get(i).add(1);
			DM.get(i).add(1);
		}
		for (int j = 0; j < (entries + 2); j++)
		{
			temp.add(GlobalConstants.TRANS_RANGE);
		}
		DM.add(temp);
		DM.add(temp);
	}

	public static void Type4(List<List<Integer>> DM)
	{
		int entries = DM.size();
		List<Integer> temp = new ArrayList<Integer>();

		for (int i = 0; i < entries; i++)
		{
			DM.get(i).add(0);
		}
		for (int j = 0; j < (entries + 1); j++)
		{
			temp.add(GlobalConstants.TRANS_RANGE);
		}
		DM.get(entries - 1).set(entries, GlobalConstants.H);
		DM.get(entries - 2).set(entries, GlobalConstants.H);
		DM.add(temp);
	}
}
