/*
 * Author: Aadil Ahamed
 * Last Edit: Apr 13, 2016
 * Description: Packages the output of InitMax
 */

import java.util.List;

public class InitMaxOut
{
    private List<Coordinate<Integer>> HCSList;
    private List<List<Integer>> DMatrix;
	private List<Coordinate<Integer>> ST;
    
    public InitMaxOut(List<Coordinate<Integer>> HCSList, List<List<Integer>> DMatrix, List<Coordinate<Integer>> ST)
    {
        this.HCSList = HCSList;
        this.DMatrix = DMatrix;
		this.ST = ST;
    }
    
    public List<Coordinate<Integer>> getHCSList()
    {
        return HCSList;
    }
    
    public void setHCSList(List<Coordinate<Integer>> l)
    {
        HCSList = l;
    }
    public List<List<Integer>> getDMatrix()
    {
        return DMatrix;
    }
    
    public void setDMatrix(List<List<Integer>> l)
    {
        DMatrix = l;
    }
    
    public List<Coordinate<Integer>> getST()
	{
		return ST;
	}

	public void setST(List<Coordinate<Integer>> l)
	{
		ST = l;
	}
}
