/*
Author: Aadil Ahamed, Ezana Woldegabriel
Date: 1/12/16
Description: Class containing a library of methods related 	to HCS
*/

public class HCS
{
	private HCS(){}
	
	/*
	 * Converts a HCS coordinate to Cartesian. Port of h2c.m
	 * Assumes HCS coordinates are integers.
	 * @param p HCS Coordinate<Integer>
	 * @param r edge length of hexagon
	 * @return Cartesian Coordinate<double>
	 */
	public static Coordinate<Double> hexToCart(Coordinate<Integer> p)
	{
		double h = 	GlobalConstants.APOTHEM;
		double x = p.getX() * 2 * h + p.getY() * 2 * h * Math.cos(Math.PI/3);
		double y = p.getY() * 2 * h * Math.cos(Math.PI/6);
		return new Coordinate<Double>(x, y);
	}
	
	/*
	 * Converts a Cartesian coordinate to HCS. Port of c2h1.m
	 * Assumes Cartesian coordinates are type Double.
	 * @param p HCS Coordinate<Integer>
	 * @param r edge length of hexagon
	 * @return Hex Coordinate<Integer>
	 */
	public static Coordinate<Integer> cartToHex(Coordinate<Double> p)
	{
		double U = p.getX()-(p.getY()/Math.sqrt(3));
		double V = (2*p.getY())/Math.sqrt(3);
		int u = (int)(Math.round(U/Math.sqrt(3*Math.pow((GlobalConstants.SN_R),2))));
		int v = (int)(Math.round(V/Math.sqrt(3*Math.pow((GlobalConstants.SN_R),2))));
		return new Coordinate<Integer>(u,v);
	}
	
	/*
	* returns the max of 2 integers
	*/
	private static int max2(int a, int b)
	{
		if(a >= b)
		{
			return a;
		}
		else
		{
			return b;
		}
	}
	
	/*
	* COPY OF Distance_matrix.HCS_distance()
	*/
	public static int distance(Coordinate<Integer> p, Coordinate<Integer> q)
	{
		int pX = p.getX();
		int pY = p.getY();
		int qX = q.getX();
		int qY = q.getY();
		return HCS.max2(Math.abs(pX - qX + pY - qY), max2(Math.abs(pX - qX), Math.abs(pY - qY)));
	}

	public static Coordinate<Double> add2coord(Coordinate<Double> a, Coordinate<Double> b)
	{
		double x = a.getX() + b.getX();
		double y = a.getY() + b.getY();

		return new Coordinate<Double>(x, y);
	}
	
	
	/* Unit Testing */
	
	
	public static void testHexToCart()
	{
		System.out.println("Testing Hex to Cart:");
		int x = 0;
		int y = 0;
		Coordinate<Integer> c = new Coordinate<Integer>(x, y);
		Coordinate<Double> d = HCS.hexToCart(c);
		System.out.printf("HCS Coord: " + c + " Cart Coord: (%.2f, %.2f)\n", d.getX(), d.getY()); //verify against matlab code

		x = 7000;
		y = 7000;
		c.setX(x);
		c.setY(y);
		d = HCS.hexToCart(c);
		System.out.printf("HCS Coord: " + c + " Cart Coord: (%.2f, %.2f)\n", d.getX(), d.getY());
		
		x = -3;
		y = 2;
		c.setX(x);
		c.setY(y);
		d = HCS.hexToCart(c);
		System.out.printf("HCS Coord: " + c + " Cart Coord: (%.2f, %.2f)\n", d.getX(), d.getY());
		x = 30;
		y = 22;
		c.setX(x);
		c.setY(y);
		d = HCS.hexToCart(c);
		System.out.printf("HCS Coord: " + c + " Cart Coord: (%.2f, %.2f)\n", d.getX(), d.getY());
		
		x = 3;
		y = -2;
		c.setX(x);
		c.setY(y);
		d = HCS.hexToCart(c);
		System.out.printf("HCS Coord: " + c + " Cart Coord: (%.2f, %.2f)\n", d.getX(), d.getY());
		
		System.out.println("-----------------------------");
		
	}
	
	private static void testCartToHex(double a, double b){
		System.out.println("Testing Cart to Hex:");
		Coordinate<Double> c = new Coordinate<Double>(a,b);
		Coordinate<Integer> d = cartToHex(c);
		System.out.println("{"+d.getX()+", "+d.getY()+"}");
		System.out.println("-----------------------------");
	}
	
	private static void testDistance()
	{
		System.out.println("Testing distance:");
		Coordinate<Integer> p = new Coordinate<Integer>(10, 5);
		Coordinate<Integer> q = new Coordinate<Integer>(3, 6);
		System.out.println("p: " + p + " q: " + q + " distance = "  + HCS.distance(p, q));
		System.out.println("-----------------------------");
	}
	
	public static void main(String[] args)
	{
		HCS.testHexToCart();
		double g,h;
		g = -9889.3;
		h = -614.36;
		testCartToHex(g,h);
		testDistance();
	}
	
}
