/*
* Author: Aadil Ahamed
* Date: 1/15/16
* Description: Class to package the output of Connect.connect()
* TODO: Describe all fields in this class -> need to ask Kai / read paper
*/

public class ConnectOut
{
	private double num;
	private double theta;
	private int pointer;
	private int[] direction;
    
	
	public ConnectOut(double num, double theta, int pointer, int[] direction)
	{
		this.num = num;
		this.theta = theta;
		this.pointer = pointer;
		this.direction = direction;
	}
	
	public double getNum()
	{
		return num;
	}

	public void setNum(double a)
	{
		this.num = a;
	}
	
	public double getTheta()
	{
		return theta;
	}
	
	public int getPointer()
	{
		return pointer;
	}

	public void setPointer(int a)
	{
		this.pointer = a;
	}
	
	public int[] getDirection()
	{
		return direction;
	}
	
	public String toString()
	{
		return String.format("num: %.2f theta: %.2f pointer: %d  direction: [%d, %d]", num, theta, pointer, direction[0], direction[1]);
	}
}
