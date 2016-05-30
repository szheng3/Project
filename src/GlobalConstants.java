/*
Author: Aadil Ahamed
Date: 1/13/16
Description: Class contating all global constants

Ex:
GlobalConstants.R
*/

public class GlobalConstants
{
	public static int n; // Initial number of clusters
	public static final int TRANS_RANGE = 500000; //transmission range
	public static final int NL = 7;
	public static final int SN_R = 5; // SN transmission range
	public static final int R = (12 * NL + 7) * SN_R; // AN transmission range
	public static final double APOTHEM = SN_R * Math.cos(Math.PI/6);
	public static final int H = R / SN_R;
}
