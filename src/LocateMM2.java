
/*
* Author: Ezana Woldegebriel
* Description: Port of locateC1.m
*/

import java.util.ArrayList;

import Jama.Matrix;

public class LocateMM2 {
	double[][] i;
	double[][] j, k, d;
	double[] lb, ub;
	double[] x = new double[5];
	double theta, h;
	ArrayList<double[]> Set1 = new ArrayList<double[]>();
	ArrayList<double[]> Set2 = new ArrayList<double[]>();
	Matrix i1;
	Matrix j1;
	Matrix k1;
	Matrix d1;
	LocateOut out = new LocateOut();
	boolean c1return = false, c2return = false, c3return = false, c4return = false;

	public static void main(String[] args) {
		double[][] p1 = { { 348, -863 }, { 0, 0 } };
		double[][] q1 = { { 56, -663 }, { 0, 0 } };
		double theta = 0.734;

		Matrix p = new Matrix(p1);
		Matrix q = new Matrix(q1);
		int H = 91;
		LocateMM2 l = new LocateMM2(p, q, H, theta);
		System.out.println("AN1 Coordinate: " + l.out.getAN().getX() + " , " + l.out.getAN().getY());
		System.out.println("AN2 Coordinate: " + l.out.getAN2().getX() + " , " + l.out.getAN2().getY());
	}

	public LocateMM2(Matrix p, Matrix q, int H, double theta) {
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

		solutionSelect(q);
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
					Set1.add(new double[] { x[0], x[1] });
					x[1] = x[1] + 2;
				}
				// Find Solution Set2 for Qi
				x[3] = Qi.get(0, 1) - h;
				while (x[3] <= h + Qi.get(0, 1)) {
					x[2] = Qi.get(0, 0) + 0.5 * Qi.get(0, 1) - 0.5 * x[3];
					Set2.add(new double[] { x[2], x[3] });
					x[3] = x[3] + 2;
				}
			} else {
				Pj = p.plus(j1.times(H));
				Qj = q.minus(j1.times(H));
				x[0] = Pj.get(0, 0) - h;
				while (x[0] <= h + Pj.get(0, 0)) {
					x[1] = 0.5 * Pj.get(0, 0) + Pj.get(0, 1) - 0.5 * x[0];
					Set1.add(new double[] { x[0], x[1] });
					x[0] = x[0] + 2;
				}
				// Solve Set2 for Qj
				x[2] = Qj.get(0, 0) - h;
				while (x[2] <= h + Qj.get(0, 0)) {
					x[3] = 0.5 * Qj.get(0, 0) + Qj.get(0, 1) - 0.5 * x[2];
					Set2.add(new double[] { x[2], x[3] });
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
			LocateMM2 v = new LocateMM2(p, q, H, theta);
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
					Set1.add(new double[] { x[0], x[1] });
					x[0] = x[0] + 2;
				}
				// Find Solution Set2 for Qj
				x[2] = Qj.get(0, 0) - h;
				while (x[2] <= h + Qj.get(0, 0)) {
					x[3] = 0.5 * Qj.get(0, 0) + Qj.get(0, 1) - 0.5 * x[2];
					Set2.add(new double[] { x[2], x[3] });
					x[2] = x[2] + 2;
				}
			} else if (Math.abs(d[0][0]) <= Math.abs(d[0][1]) && theta > Math.PI / 6) {
				Pk = p.plus(k1.times(H));
				Qk = q.minus(k1.times(H));
				x[0] = Math.ceil(Pk.get(0, 0) - h / 2);
				while (x[0] <= Math.floor(h / 2 + Pk.get(0, 0))) {
					x[1] = x[0] - Pk.get(0, 0) + Pk.get(0, 1);
					Set1.add(new double[] { x[0], x[1] });
					x[0] = x[0] + 1;
				}
				x[2] = Math.ceil(Qk.get(0, 0) - h / 2);
				while (x[2] <= Math.floor(h / 2 + Qk.get(0, 0))) {
					x[3] = x[2] - Qk.get(0, 0) + Qk.get(0, 1);
					Set2.add(new double[] { x[2], x[3] });
					x[2] = x[2] + 1;
				}
			} else if (Math.abs(d[0][0]) > Math.abs(d[0][1]) && theta > Math.PI / 6) {
				Pk = p.plus(k1.times(H));
				Qk = q.minus(k1.times(H));

				x[0] = Math.ceil(Pk.get(0, 0) - h / 2);
				while (x[0] <= Math.floor(h / 2 + Pk.get(0, 0))) {
					x[1] = x[0] - Pk.get(0, 0) + Pk.get(0, 1);
					Set1.add(new double[] { x[0], x[1] });
					x[0] = x[0] + 1;
				}
				x[2] = Math.ceil(Qk.get(0, 0) - h / 2);
				while (x[2] <= Math.floor(h / 2 + Qk.get(0, 0))) {
					x[3] = x[2] - Qk.get(0, 0) + Qk.get(0, 1);
					Set2.add(new double[] { x[2], x[3] });
					x[2] = x[2] + 1;
				}
			} else if (Math.abs(d[0][0]) > Math.abs(d[0][1]) && theta <= Math.PI / 6) {
				Pri = p.minus(i1.times(H));
				Qri = q.plus(i1.times(H));

				x[1] = Pri.get(0, 1) - h;
				while (x[1] <= h + Pri.get(0, 1)) {
					x[0] = Pri.get(0, 0) + 0.5 * Pri.get(0, 1) - 0.5 * x[1];
					Set1.add(new double[] { x[0], x[1] });
					x[1] = x[1] + 2;
				}
				x[3] = Qri.get(0, 1) - h;
				while (x[3] <= Qri.get(0, 1) + h) {
					x[2] = Qri.get(0, 0) + 0.5 * Qri.get(0, 1) - 0.5 * x[3];
					Set2.add(new double[] { x[2], x[3] });
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
			LocateMM2 v = new LocateMM2(p, q, H, theta);
			out.setAN(v.out.getAN2());
			out.setAN2(v.out.getAN());
			c4return = true;
			return;
		}
	}

	private void solutionSelect(Matrix q) {
		double M = 0.0;
		double[] a1;
		Coordinate<Integer> c1, c2;
		double min = GlobalConstants.TRANS_RANGE;
		int Pc = -1;
		for (int ind1 = 0; ind1 < Set1.size(); ind1++) {
			a1 = Set1.get(ind1);
			c1 = new Coordinate<Integer>((int) a1[0], (int) a1[1]);
			c2 = new Coordinate<Integer>((int) q.get(0, 0), (int) q.get(0, 1));
			M = HCS.distance(c1, c2);

			if (M < min) {
				min = M;
				Pc = ind1;
			}

		}
		double[][] l1temp = new double[][] { { Set1.get(Pc)[0], Set1.get(Pc)[1] }, { 0, 0 } };
		Matrix l1mat = new Matrix(l1temp);
		Locate1 l1 = new Locate1(l1mat, q, GlobalConstants.H);
		LocateC1 lC1 = new LocateC1(l1mat, q, GlobalConstants.H);
		out.setAN(l1.out.getAN());
		Coordinate<Integer> O = new Coordinate<Integer>(0, 0);
		if (l1.out.getexFlag() != 0 && lC1.out.getexFlag() != 0) {
			if (HCS.distance(out.getAN(), O) > HCS.distance(lC1.out.getAN(), O)) {
				out.setAN2(lC1.out.getAN());
			} else {
				out.setAN2(out.getAN());
			}
		}
		double[] p1 = Set1.get(Pc);
		out.setAN(new Coordinate<Integer>((int) p1[0], (int) p1[1]));
	}

}
