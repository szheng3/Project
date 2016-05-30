/*
Author: Aadil Ahamed
Date: 1/12/16
Description: Port of connect.m from Kai's repository
*/

public class Connect {
	public static final char CONNECT_FLAG = 'a';
	public static final char CONNECT_C_FLAG = 'c';
	public static final char CONNECT_O_FLAG = 'o';

	public Connect() {
	}

	/*
	 * calculateNum: Not sure what this does
	 * 
	 * @param theta angle - of what?
	 * 
	 * @param cart_distance - of what?
	 * 
	 * @return num
	 */
	private static double calculateNum(double theta, double cart_distance, char flag) {
		final double PI = Math.PI;
		double num;
		if (theta > PI / 6) {
			num = (cart_distance * Math.cos(PI / 3 - theta)) / (2 * GlobalConstants.R * Math.cos(PI / 6));
		} else {
			num = (cart_distance * Math.cos(theta)) / (2 * GlobalConstants.R * Math.cos(PI / 6));
		}
		if (flag == Connect.CONNECT_FLAG) {
			num = Connect.incrementNum(num, theta);
		}
		return num;
	}

	private static double incrementNum(double num, double theta) {
		if ((Math.abs(theta - Math.PI / 6) < 0.03) && (Math.abs(num - Math.ceil(num)) < 0.1)
				|| (num - Math.floor(num) < 0.02)) {
			num += 0.15;
		}
		return num;
	}

	private static int calculatePtr(double num) {
		if (num <= 1.02) {
			return 1;
		} else {
			return 0;
		}
	}

	/*
	 * @param p HCS coordinate of first cluster
	 * 
	 * @param q HCS coordinate of second cluster
	 * 
	 * @param flag {connect - 'a', connect_C - 'c', connect_O - 'o'}
	 * 
	 * @return ConnectOut - look at ConnectOut.java for details
	 */
	public static ConnectOut connect(Coordinate<Integer> p, Coordinate<Integer> q, char flag) {
		Coordinate<Double> a = HCS.hexToCart(p);
		Coordinate<Double> b = HCS.hexToCart(q);
		// double[] cart_vector = new double[2];
		// cart_vector[0] = a.getX() - b.getX();
		// cart_vector[1] = a.getY() - b.getY();
		double cart_distance = Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2)); // calculate
																												// cartesian
																												// distance
		double[] pq_vector = new double[2];
		pq_vector[0] = q.getX() - p.getX(); // represents x component of vector
											// PQ
		pq_vector[1] = q.getY() - p.getY(); // represents y component of vector
											// PQ
		double theta, num;
		int pointer;
		int[] direction = new int[2];
		// CASE 1: Theta w.r.t x+ axis is in 1st or 3rd quadrant
		if (pq_vector[0] * pq_vector[1] > 0) {
			theta = Math.atan(Math.sqrt(3) / ((2 * Math.abs(pq_vector[0]) / Math.abs((pq_vector[1])) + 1)));
			direction[0] = 1;
			direction[1] = 0;
			num = Connect.calculateNum(theta, cart_distance, flag);
			pointer = Connect.calculatePtr(num);
		}
		// CASE 2: Theta w.r.t x+ axis is in 2nd or 4th quadrant
		else if (pq_vector[0] * pq_vector[1] < 0) {
			// Case 1: w.r.t y+ axis
			if (Math.abs(pq_vector[0]) <= Math.abs(pq_vector[1])) {
				theta = Math.atan((-1 * Math.sqrt(3)) / ((2 * pq_vector[1] / pq_vector[0]) + 1));
				direction[0] = 0;
				direction[1] = 1;
				num = Connect.calculateNum(theta, cart_distance, flag);
				pointer = Connect.calculatePtr(num);
			}
			// Case 2: w.r.t x+ axis
			else {
				theta = Math.atan((-1 * Math.sqrt(3)) / ((2 * pq_vector[0] / pq_vector[1]) + 1));
				direction[0] = -1;
				direction[1] = 0;
				num = Connect.calculateNum(theta, cart_distance, flag);
				pointer = Connect.calculatePtr(num);
			}
		}
		// CASE 3: pq_vector[0] * pq_vector [1] == 0
		else {
			theta = 0;
			if (pq_vector[0] == 0) {
				direction[0] = 0;
				direction[1] = 1;
			} else {
				direction[0] = 1;
				direction[1] = 0;
			}
			num = Connect.calculateNum(theta, cart_distance, Connect.CONNECT_C_FLAG); // Force
																						// exclusion
																						// of
																						// incrementNum()
			pointer = Connect.calculatePtr(num);
		}
		return new ConnectOut(num, theta, pointer, direction);
	}

	private static void test1() {

		Coordinate<Integer> p6 = new Coordinate<Integer>(0, 0);
		Coordinate<Integer> q6 = new Coordinate<Integer>(0, 10);
		System.out.println(Connect.connect(p6, q6, Connect.CONNECT_FLAG)); // x*y=0
																			// x=0

		Coordinate<Integer> p7 = new Coordinate<Integer>(-524, 382);
		Coordinate<Integer> q7 = new Coordinate<Integer>(-585, 502);
		System.out.println(Connect.connect(p7, q7, Connect.CONNECT_C_FLAG)); // x*y=0
																				// x=0

		Coordinate<Integer> p8 = new Coordinate<Integer>(-10, -10);
		Coordinate<Integer> q8 = new Coordinate<Integer>(100, 10);
		int exp = 0;
		int res = Connect.connect(p8, q8, Connect.CONNECT_C_FLAG).getPointer();
		System.out.println("res: " + res + " exp:" + exp); // x*y=0 x=0
	}

	public static void main(String[] args) {
		Connect.test1();
	}
}