/*
 * Author: Josue Galeas
 * Last Edit: Apr 21, 2016
 * Description: Class for reading lines from a text file and storing each line into an ArrayList of strings.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadFile
{
	public static List<String> RF(String inputFile)
	{
		List<String> output = new ArrayList<String>();

		Path filePath = Paths.get(inputFile);
		try
		{
			BufferedReader reader = Files.newBufferedReader(filePath);
			try
			{
				String line = null;

				while ((line = reader.readLine()) != null)
				{
					output.add(line);
				}
			}
			catch (IOException x)
			{
				System.err.format("ERROR: Could not open the input file. [%s]\n", x);
				System.exit(2);
			}
			finally
			{
				try
				{
					if (reader != null)
						reader.close();
				}
				catch (IOException x)
				{
					System.err.format("ERROR: Could not close the input file. [%s]\n", x);
					System.exit(3);
				}
			}
		}
		catch (IOException x)
		{
			System.err.format("ERROR: Could not find the input file. Use --help for usage details. [%s]\n", x);
			System.exit(1);
		}

		return output;
	}

	private static void printFD(List<String> fileData)
	{
		for (int c = 0; c < fileData.size(); c++)
		{
			System.out.println(fileData.get(c));
		}
	}

	public static void main(String[] args)
	{
		List<String> test = RF(args[0]);

		System.out.println(">> The input file has " + test.size() + " entries.");
		System.out.println(">> The input file contains:");
		printFD(test);
	}
}
