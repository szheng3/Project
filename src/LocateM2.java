
/*
* Author: Ezana Woldegebriel
* Edited: Aadil Ahamed - Overloaded Constructor
* Description: Port of locateC1.m
*/

import java.util.ArrayList;

import Jama.Matrix;

public class LocateM2 {
	double[][] i;
	double[][] j, k, d;
	double[] lb, ub;
	double[] x = new double[5];
	double theta, h;
	ArrayList<Double[]> Set1 = new ArrayList<Double[]>();
	ArrayList<Double[]> Set2 = new ArrayList<Double[]>();
	Matrix i1;
	Matrix j1;
	Matrix k1;
	Matrix d1;
	LocateOut out = new LocateOut();
	boolean c1return = false, c2return = false, c3return = false, c4return = false;

	public static void main(String[] args) {
		/*
		 * double[][] p1 = {{-1324,385},{0,0}}; double[][] q1 =
		 * {{-67,990},{0,0}}; double theta = 0.3241;
		 */

		/*
		 * double[][] p1 = {{-199,2578},{0,0}}; double[][] q1 =
		 * {{282,600},{0,0}}; double theta = 0.2353;
		 */

		/*
		 * double[][] p1 = {{-324,137},{0,0}}; double[][] q1 =
		 * {{388,-1307},{0,0}}; double theta = 0.0392;
		 */

		/*
		 * double[][] p1 = {{1691,-847},{0,0}}; double[][] q1 =
		 * {{480,103},{0,0}}; double theta = 0.8410;
		 */

		/*
		 * double[][] p1 = {{250,-1734},{0,0}}; double[][] q1 =
		 * {{881,-1178},{0,0}}; double theta = 0.3355;
		 */

		double[][] p1 = { { -719, -954 }, { 0, 0 } };
		double[][] q1 = { { -778, 1022 }, { 0, 0 } };
		double theta = 0.02624390841691486;

		Matrix p = new Matrix(p1);
		Matrix q = new Matrix(q1);
		int H = 91;
		LocateM2 l = new LocateM2(p, q, H, theta);
		System.out.println("AN1 Coordinate: " + l.out.getAN().getX() + " , " + l.out.getAN().getY());
		System.out.println("AN2 Coordinate: " + l.out.getAN2().getX() + " , " + l.out.getAN2().getY());
	}

	public LocateM2(Matrix p, Matrix q, int H, double theta) {
		i = new double[][] { { 1, 0 }, { 0, 0 } };
		j = new double[][] { { 0, 1 }, { 0, 0 } };
		k = new double[][] { { -1, 1 }, { 0, 0 } };
		h = (H + 1) / 2;
		lb = new double[] { -100, -100, -100, -100 };
		ub = new double[] { 100, 100, 100, 100 };

		d = new double[][] { { (q.get(0, 0) - p.get(0, 0)), (q.get(0, 1) - p.get(0, 1)) }, { 0, 0 } };
		i1 = new Matrix(i);
		j1 = new Matrix(j);
		k1 = new Matrix(k);
		d1 = new Matrix(d);

		if (this.d[0][0] > 0 && this.d[0][1] >= 0) {
			case1(p, q, H, theta);
		} else if (d[0][0] < 0 && d[0][1] <= 0) {
			case2(p, q, H, theta);
			if (c2return)
				return;
		}
		if (this.d[0][0] <= 0 && this.d[0][1] > 0) {
			case3(p, q, H, theta);
		} else if (d[0][0] >= 0 && d[0][1] < 0) {
			case4(p, q, H, theta);
			if (c4return)
				return;
		}

		solutionSelect();
	}

	/*
	 * Overloaded Constructor: Added support for taking cluster coordinates p
	 * and q as type Coordinate<Integer>
	 */
	public LocateM2(Coordinate<Integer> p, Coordinate<Integer> q, int H, double theta) {
		this(new Matrix(new double[][] { { p.getX(), p.getY() }, { 0, 0 } }),
				new Matrix(new double[][] { { q.getX(), q.getY() }, { 0, 0 } }), H, theta);
	}

	private void case1(Matrix p, Matrix q, int H, double theta) {
		Matrix Pi, Qi, Pj, Qj;
		if (this.d[0][0] > 0 && this.d[0][1] >= 0) {
			if (theta <= Math.PI / 6) {
				Pi = p.plus(i1.times((double) H));
				Qi = q.minus(i1.times((double) H));
				x[1] = Pi.get(0, 1) - h;
				while (x[1] <= h + Pi.get(0, 1)) {
					x[0] = Pi.get(0, 0) + 0.5 * Pi.get(0, 1) - 0.5 * x[1];
					Set1.add(new Double[] { x[0], x[1] });
					x[1] = x[1] + 2;
				}
				// Find Solution Set2 for Qi
				x[3] = Qi.get(0, 1) - h;
				while (x[3] <= h + Qi.get(0, 1)) {
					x[2] = Qi.get(0, 0) + 0.5 * Qi.get(0, 1) - 0.5 * x[3];
					Set2.add(new Double[] { x[2], x[3] });
					x[3] = x[3] + 2;
				}
			} else {
				Pj = p.plus(j1.times(H));
				Qj = q.minus(j1.times(H));
				x[0] = Pj.get(0, 0) - h;
				while (x[0] <= h + Pj.get(0, 0)) {
					x[1] = 0.5 * Pj.get(0, 0) + Pj.get(0, 1) - 0.5 * x[0];
					Set1.add(new Double[] { x[0], x[1] });
					x[0] = x[0] + 2;
				}
				// Solve Set2 for Qj
				x[2] = Qj.get(0, 0) - h;
				while (x[2] <= h + Qj.get(0, 0)) {
					x[3] = 0.5 * Qj.get(0, 0) + Qj.get(0, 1) - 0.5 * x[2];
					Set2.add(new Double[] { x[2], x[3] });
					x[2] = x[2] + 2;
				}
			}
		}
	}

	private void case2(Matrix p, Matrix q, int H, double theta) {
		if (d[0][0] < 0 && d[0][1] <= 0) {
			Matrix c;
			c = p;
			p = q;
			q = c;
			LocateM2 v = new LocateM2(p, q, H, theta);
			out.setAN(v.out.getAN2());
			out.setAN2(v.out.getAN());
			c2return = true;
			return;
		}
	}

	private void case3(Matrix p, Matrix q, int H, double theta) {
		Matrix Pj, Qj, Pk, Qk, Pri, Qri;
		if (this.d[0][0] <= 0 && this.d[0][1] > 0) {
			if (Math.abs(d[0][0]) <= Math.abs(d[0][1]) && theta <= Math.PI / 6) {
				Pj = p.plus(j1.times((double) H));
				Qj = q.minus(j1.times((double) H));
				x[0] = Pj.get(0, 0) - h;
				while (x[0] <= h + Pj.get(0, 0)) {
					x[1] = 0.5 * Pj.get(0, 0) + Pj.get(0, 1) - 0.5 * x[0];
					Set1.add(new Double[] { x[0], x[1] });
					x[0] = x[0] + 2;
				}
				// Find Solution Set2 for Qj
				x[2] = Qj.get(0, 0) - h;
				while (x[2] <= h + Qj.get(0, 0)) {
					x[3] = 0.5 * Qj.get(0, 0) + Qj.get(0, 1) - 0.5 * x[2];
					Set2.add(new Double[] { x[2], x[3] });
					x[2] = x[2] + 2;
				}
			} else if (Math.abs(d[0][0]) <= Math.abs(d[0][1]) && theta > Math.PI / 6) {
				Pk = p.plus(k1.times(H));
				Qk = q.minus(k1.times(H));
				x[0] = Math.ceil(Pk.get(0, 0) - h / 2);
				while (x[0] <= Math.floor(h / 2 + Pk.get(0, 0))) {
					x[1] = x[0] - Pk.get(0, 0) + Pk.get(0, 1);
					Set1.add(new Double[] { x[0], x[1] });
					x[0] = x[0] + 1;
				}
				x[2] = Math.ceil(Qk.get(0, 0) - h / 2);
				while (x[2] <= Math.floor(h / 2 + Qk.get(0, 0))) {
					x[3] = x[2] - Qk.get(0, 0) + Qk.get(0, 1);
					Set2.add(new Double[] { x[2], x[3] });
					x[2] = x[2] + 1;
				}
			} else if (Math.abs(d[0][0]) > Math.abs(d[0][1]) && theta > Math.PI / 6) {
				Pk = p.plus(k1.times(H));
				Qk = q.minus(k1.times(H));

				x[0] = Math.ceil(Pk.get(0, 0) - h / 2);
				while (x[0] <= Math.floor(h / 2 + Pk.get(0, 0))) {
					x[1] = x[0] - Pk.get(0, 0) + Pk.get(0, 1);
					Set1.add(new Double[] { x[0], x[1] });
					x[0] = x[0] + 1;
				}
				x[2] = Math.ceil(Qk.get(0, 0) - h / 2);
				while (x[2] <= Math.floor(h / 2 + Qk.get(0, 0))) {
					x[3] = x[2] - Qk.get(0, 0) + Qk.get(0, 1);
					Set2.add(new Double[] { x[2], x[3] });
					x[2] = x[2] + 1;
				}
			} else if (Math.abs(d[0][0]) > Math.abs(d[0][1]) && theta <= Math.PI / 6) {
				Pri = p.minus(i1.times(H));
				Qri = q.plus(i1.times(H));

				x[1] = Pri.get(0, 1) - h;
				while (x[1] <= h + Pri.get(0, 1)) {
					x[0] = Pri.get(0, 0) + 0.5 * Pri.get(0, 1) - 0.5 * x[1];
					Set1.add(new Double[] { x[0], x[1] });
					x[1] = x[1] + 2;
				}
				x[3] = Qri.get(0, 1) - h;
				while (x[3] <= Qri.get(0, 1) + h) {
					x[2] = Qri.get(0, 0) + 0.5 * Qri.get(0, 1) - 0.5 * x[3];
					Set2.add(new Double[] { x[2], x[3] });
					x[3] = x[3] + 2;
				}
			}

		}

	}

	private void case4(Matrix p, Matrix q, int H, double theta) {
		if (d[0][0] >= 0 && d[0][1] < 0) {
			Matrix c;
			c = p;
			p = q;
			q = c;
			LocateM2 v = new LocateM2(p, q, H, theta);
			out.setAN(v.out.getAN2());
			out.setAN2(v.out.getAN());
			c4return = true;
			return;
		}
	}

	private void solutionSelect() {
		double d0 = 0.0;
		double d_new = 0.0;
		double M = 0.0;
		Double[] a1;
		Double[] a2;
		Double[] dtemp, dnewtemp, qtemp, qnewtemp;
		Coordinate<Integer> c1, c2, dtemp1, dtemp2, dnewtemp1, dnewtemp2, zero;
		double min = GlobalConstants.TRANS_RANGE;
		int Pc = -1;
		int Qc = -1;

		for (int ind1 = 0; ind1 < Set1.size(); ind1++) {
			for (int ind2 = 0; ind2 < Set2.size(); ind2++) {
				a1 = Set1.get(ind1);
				a2 = Set2.get(ind2);
				c1 = new Coordinate<Integer>(a1[0].intValue(), a1[1].intValue());
				c2 = new Coordinate<Integer>(a2[0].intValue(), a2[1].intValue());
				M = HCS.distance(c1, c2);

				if (M < min && M >= h) {
					min = M;
					Pc = ind1;
					Qc = ind2;
				} else if (M == min) {
					dtemp = Set1.get(Pc);
					qtemp = Set2.get(Qc);
					dnewtemp = Set1.get(ind1);
					qnewtemp = Set2.get(ind2);
					dtemp1 = new Coordinate<Integer>(dtemp[0].intValue(), dtemp[1].intValue());
					dtemp2 = new Coordinate<Integer>(qtemp[0].intValue(), qtemp[1].intValue());
					dnewtemp1 = new Coordinate<Integer>(dnewtemp[0].intValue(), dnewtemp[1].intValue());
					dnewtemp2 = new Coordinate<Integer>(qnewtemp[0].intValue(), qnewtemp[1].intValue());
					zero = new Coordinate<Integer>(0, 0);
					d0 = HCS.distance(dtemp1, zero) + HCS.distance(dtemp2, zero);
					d_new = HCS.distance(dnewtemp1, zero) + HCS.distance(dnewtemp2, zero);
					if (d_new < d0) {
						Pc = ind1;
						Qc = ind2;
					}

				}
			}
		}
		int tempa1 = Set1.get(Pc)[0].intValue();
		int tempa2 = Set1.get(Pc)[1].intValue();
		int tempb1 = Set2.get(Qc)[0].intValue();
		int tempb2 = Set2.get(Qc)[1].intValue();
		Coordinate<Integer> AN1 = new Coordinate<Integer>(tempa1, tempa2);
		Coordinate<Integer> AN2 = new Coordinate<Integer>(tempb1, tempb2);
		out.setAN(AN1);
		out.setAN2(AN2);
	}

}
