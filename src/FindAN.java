/*
*Author: Aadil Ahamed
*Date: 1/25/16
*Description: Port of FindAN1.m and FindAN2.m
*/

import java.util.Arrays;
import Jama.Matrix;

public class FindAN
{
	private static final int[] CASE1 = {-1, 1, 0};
	private static final int[] CASE2 = {1, -1, 0};
	private static final int[] CASE3 = {0, 1, 1}; // Duplicate case - 2 different versions found in findAN1 and findAN2
	private static final int[] CASE4 = {0, -1, -1}; //Duplicate case - exact same in findAN1 and findAN2
	private static final int[] CASE5 = {1, 0, -1};
	private static final int[] CASE6 = {0, 1, -1};
	private static final int[] CASE7 = {1, 1, 0};
	private static final int[] CASE8 = {1, 0, 1};
	private static final int[] CASE9 = {-1, 0, 1};
	private static final int[] CASE10 = {0, -1, 1};
	private static final int[] CASE11 = {-1, -1, 0};
	private static final int[] CASE12 = {-1, 0, -1};
	private static final double THRESHOLD = 1E-6;
	
	private FindAN(){}
	
	private static boolean perturb(double[][] result)
	{
		if( (Math.abs( Math.round(result[0][0]) - result[0][0] ) < FindAN.THRESHOLD) && 
					(Math.abs( Math.round(result[1][0]) - result[1][0] ) < FindAN.THRESHOLD))
		{
			return false;
		}
		return true;
	}
	
	private static FindANOut computeFlag(Matrix A, Matrix B, Matrix C, Matrix D)
	{
		int flag = 0;
		double[][] result = (A.solve(B)).getArray();
		if(!FindAN.perturb(result))
		{
			flag = 1;
		}
		else
		{
			Matrix BplusC = B.plus(C);
			result = (A.solve(BplusC)).getArray();
			if(!FindAN.perturb(result))
			{
				flag = 1;
			}
			else
			{
				Matrix BplusD = B.plus(D);
				result = (A.solve(BplusD)).getArray();
				if(!FindAN.perturb(result))
				{
					flag = 1;
				}
				else
				{
					flag = 0;
				}
			}
		}
		return new FindANOut(result, flag);
	}
	
	
	private static FindANOut case1(Coordinate<Integer> I, Coordinate<Integer> J)
	{
		double[][] A_ji = {{0.5, 1}, {1, 0.5}};
		double[][] b_ji = {{0.5*J.getX() +J.getY()},{I.getX() + (0.5*I.getY())}};
		Matrix mat_A_ji = new Matrix(A_ji);
		Matrix mat_b_ji = new Matrix(b_ji);
		Matrix mat_b_yk = new Matrix(new double[][]{{-0.5},{0}}); 
		Matrix mat_b_xK	 = new Matrix(new double[][]{{0},{0.5}});
		return FindAN.computeFlag(mat_A_ji, mat_b_ji, mat_b_yk, mat_b_xK); 
	}
	
	private static FindANOut case2(Coordinate<Integer> I, Coordinate<Integer> J)
	{
		double[][] A_ji = {{0.5, 1}, {1, 0.5}};
		double[][] b_ji = {{0.5*J.getX() +J.getY()},{I.getX() + (0.5*I.getY())}};
		Matrix mat_A_ji = new Matrix(A_ji);
		Matrix mat_b_ji = new Matrix(b_ji);
		Matrix mat_b_yk = new Matrix(new double[][]{{0.5},{0}}); 
		Matrix mat_b_xK	 = new Matrix(new double[][]{{0},{-0.5}});
		return FindAN.computeFlag(mat_A_ji, mat_b_ji, mat_b_yk, mat_b_xK); 
	}
	
	private static FindANOut case3(Coordinate<Integer> J, Coordinate<Integer> K)
	{
		double[][] A_jk = {{0.5, 1}, {-0.5, 0.5}};
		double[][] b_jk = {{0.5*J.getX() +J.getY()},{(-0.5*K.getX()) + (0.5*K.getY())}};
		Matrix mat_A_jk = new Matrix(A_jk);
		Matrix mat_b_jk = new Matrix(b_jk);
		Matrix mat_b_xI = new Matrix(new double[][]{{-0.5},{0}}); 
		Matrix mat_b_kI	 = new Matrix(new double[][]{{0},{-1}});
		return FindAN.computeFlag(mat_A_jk, mat_b_jk, mat_b_xI, mat_b_kI); 
	}
	
	private static FindANOut case4(Coordinate<Integer> J, Coordinate<Integer> K)
	{
		double[][] A_kj = {{0.5, 1}, {-0.5, 0.5}};
		double[][] b_kj = {{0.5*J.getX() +J.getY()},{(-0.5*K.getX()) + (0.5*K.getY())}};
		Matrix mat_A_kj = new Matrix(A_kj);
		Matrix mat_b_kj = new Matrix(b_kj);
		Matrix mat_b_xI = new Matrix(new double[][]{{0},{0.5}}); 
		Matrix mat_b_kI	 = new Matrix(new double[][]{{0.5},{0.5}});
		return FindAN.computeFlag(mat_A_kj, mat_b_kj, mat_b_xI, mat_b_kI); 
	}
	
	private static FindANOut case5(Coordinate<Integer> I, Coordinate<Integer> K)
	{
		double[][] A_ik = {{1, 0.5}, {-0.5, 0.5}};
		double[][] b_ik = {{I.getX() +0.5*I.getY()},{(-0.5*K.getX()) + (0.5*K.getY())}};
		Matrix mat_A_ik = new Matrix(A_ik);
		Matrix mat_b_ik = new Matrix(b_ik);
		Matrix mat_b_xJ = new Matrix(new double[][]{{-0.5},{0.5}}); 
		Matrix mat_b_kJ	 = new Matrix(new double[][]{{-0.5},{0}});
		return FindAN.computeFlag(mat_A_ik, mat_b_ik, mat_b_xJ, mat_b_kJ); 
	}
	
	private static FindANOut case6(Coordinate<Integer> J, Coordinate<Integer> K)
	{
		double[][] A_jk = {{0.5, 1}, {-0.5, 0.5}};
		double[][] b_jk = {{0.5*J.getX() +J.getY()},{(-0.5*K.getX()) + (0.5*K.getY())}};
		Matrix mat_A_jk = new Matrix(A_jk);
		Matrix mat_b_jk = new Matrix(b_jk);
		Matrix mat_b_xI = new Matrix(new double[][]{{0},{0.5}}); 
		Matrix mat_b_kI	 = new Matrix(new double[][]{{-0.5},{0}});
		return FindAN.computeFlag(mat_A_jk, mat_b_jk, mat_b_xI, mat_b_kI); 
	}
	
	private static FindANOut case7(Coordinate<Integer> I, Coordinate<Integer> J)
	{
		double[][] A_jk = {{0.5, 1}, {1, 0.5}};
		double[][] b_jk = {{0.5*J.getX() +J.getY()},{(I.getX()) + (0.5*I.getY())}};
		Matrix mat_A_jk = new Matrix(A_jk);
		Matrix mat_b_jk = new Matrix(b_jk);
		Matrix mat_b_xI = new Matrix(new double[][]{{-1},{0}}); 
		Matrix mat_b_kI	 = new Matrix(new double[][]{{0},{-0.5}});
		return FindAN.computeFlag(mat_A_jk, mat_b_jk, mat_b_xI, mat_b_kI); 
	}
	
	private static FindANOut case8(Coordinate<Integer> I, Coordinate<Integer> K)
	{
		double[][] A_jk = {{1, 0.5}, {-0.5, 0.5}};
		double[][] b_jk = {{I.getX() +0.5*I.getY()},{(-0.5*K.getX()) + (0.5*K.getY())}};
		Matrix mat_A_jk = new Matrix(A_jk);
		Matrix mat_b_jk = new Matrix(b_jk);
		Matrix mat_b_xI = new Matrix(new double[][]{{-0.5},{0}}); 
		Matrix mat_b_kI	 = new Matrix(new double[][]{{0},{-0.5}});
		return FindAN.computeFlag(mat_A_jk, mat_b_jk, mat_b_xI, mat_b_kI); 
	}
	
	private static FindANOut case9(Coordinate<Integer> I, Coordinate<Integer> K)
	{
		double[][] A_jk = {{-0.5, 0.5}, {1, 0.5}};
		double[][] b_jk = {{-0.5*K.getX() +0.5*K.getY()},{(I.getX()) + (0.5*I.getY())}};
		Matrix mat_A_jk = new Matrix(A_jk);
		Matrix mat_b_jk = new Matrix(b_jk);
		Matrix mat_b_xI = new Matrix(new double[][]{{-0.5},{0}}); 
		Matrix mat_b_kI	 = new Matrix(new double[][]{{-0.5},{0.5}});
		return FindAN.computeFlag(mat_A_jk, mat_b_jk, mat_b_xI, mat_b_kI); 
	}
	
	private static FindANOut case10(Coordinate<Integer> J, Coordinate<Integer> K)
	{
		double[][] A_jk = {{-0.5, 0.5}, {0.5, 1}};
		double[][] b_jk = {{-0.5*K.getX() +0.5*K.getY()},{(0.5*J.getX()) + (J.getY())}};
		Matrix mat_A_jk = new Matrix(A_jk);
		Matrix mat_b_jk = new Matrix(b_jk);
		Matrix mat_b_xI = new Matrix(new double[][]{{0},{0.5}}); 
		Matrix mat_b_kI	 = new Matrix(new double[][]{{-0.5},{0}});
		return FindAN.computeFlag(mat_A_jk, mat_b_jk, mat_b_xI, mat_b_kI); 
	}
	
	private static FindANOut case11(Coordinate<Integer> I, Coordinate<Integer> J)
	{
		double[][] A_ji = {{0.5, 1}, {1, 0.5}};
		double[][] b_ji = {{0.5*J.getX() +J.getY()},{I.getX() + (0.5*I.getY())}};
		Matrix mat_A_ji = new Matrix(A_ji);
		Matrix mat_b_ji = new Matrix(b_ji);
		Matrix mat_b_yk = new Matrix(new double[][]{{0.5},{0.5}}); 
		Matrix mat_b_xK	 = new Matrix(new double[][]{{0},{0.5}});
		return FindAN.computeFlag(mat_A_ji, mat_b_ji, mat_b_yk, mat_b_xK); 
	}
	
	private static FindANOut case12(Coordinate<Integer> I, Coordinate<Integer> K)
	{
		double[][] A_ji = {{1, 0.5}, {-0.5, 0.5}};
		double[][] b_ji = {{I.getX() + 0.5*I.getY()},{ -0.5 * K.getX() + (0.5*K.getY())}};
		Matrix mat_A_ji = new Matrix(A_ji);
		Matrix mat_b_ji = new Matrix(b_ji);
		Matrix mat_b_yk = new Matrix(new double[][]{{0},{1}}); 
		Matrix mat_b_xK	 = new Matrix(new double[][]{{1},{0}});
		return FindAN.computeFlag(mat_A_ji, mat_b_ji, mat_b_yk, mat_b_xK); 
	}
	
	/*
	* Port of FindAN1.m and FindAN2.m -- both are merged into this method
	*/
	public static FindANOut findANm(Coordinate<Integer> I, Coordinate<Integer> J, Coordinate<Integer> K, int[] indicator)
	{
		if(Arrays.equals(indicator, FindAN.CASE1))
		{
			return FindAN.case1(I, J);
		}
		else if(Arrays.equals(indicator, FindAN.CASE2))
		{
			return FindAN.case2(I, J);
		}
		else if(Arrays.equals(indicator, FindAN.CASE3))
		{
			return FindAN.case3(J, K);
		}
		else if(Arrays.equals(indicator, FindAN.CASE4))
		{
			return FindAN.case4(J, K);
		}
		else if(Arrays.equals(indicator, FindAN.CASE5))
		{
			return FindAN.case5(I, K);
		}
		else if(Arrays.equals(indicator, FindAN.CASE6))
		{
			return FindAN.case6(J, K);
		}
		else if(Arrays.equals(indicator, FindAN.CASE7))
		{
			return FindAN.case7(I, J);
		}
		else if(Arrays.equals(indicator, FindAN.CASE8))
		{
			return FindAN.case8(I, K);
		}
		else if(Arrays.equals(indicator, FindAN.CASE9))
		{
			return FindAN.case9(I, K);
		}
		else if(Arrays.equals(indicator, FindAN.CASE10))
		{
			return FindAN.case10(J, K);
		}
		else if(Arrays.equals(indicator, FindAN.CASE11))
		{
			return FindAN.case11(I, J);
		}
		else if(Arrays.equals(indicator, FindAN.CASE12))
		{
			return FindAN.case12(I, K);
		}
		else
		{
			throw new Error("Invalid Indicator: " + Arrays.toString(indicator));
		}
	}

	
	private static void print2DArray(double[][] arr)
	{
		System.out.println("Array Contents:");
		for(int i = 0; i < arr.length; i++)
		{
			System.out.println(Arrays.toString(arr[i]));
		}
	}
	
	/*
		Inputs to test case 1 - indicator = [-1, 1, 0]
		Please make a different method for different indicators
	*/
	private static void testCase1()
	{
		/*int[] indicator = {-1, 1, 0};
		Coordinate<Integer> I = new Coordinate<Integer>(0, -1);
		Coordinate<Integer> J = new Coordinate<Integer>(1, 0);
		Coordinate<Integer> K = new Coordinate<Integer>(0, 0);
		FindANOut res = FindAN.findANm(I, J, K, indicator);
		System.out.println("flag: " + res.getFlag());
		print2DArray(res.getResult());
		
		int[] indicator = {-1, -1, 0};
		Coordinate<Integer> I = new Coordinate<Integer>(185,492);
		Coordinate<Integer> J = new Coordinate<Integer>(148,530);
		Coordinate<Integer> K = new Coordinate<Integer>(0, 0);
		FindANOut res = FindAN.findANm(I, J, K, indicator);
		System.out.println("flag: " + res.getFlag());
		print2DArray(res.getResult());*/
		
		int[] indicator = {-1,0,-1};
		Coordinate<Integer> I = new Coordinate<Integer>(143,-795);
		Coordinate<Integer> J = new Coordinate<Integer>(0, 0);
		Coordinate<Integer> K = new Coordinate<Integer>(147, -754);
		FindANOut res = FindAN.findANm(I, J, K, indicator);
		System.out.println(res);
		System.out.println("flag: " + res.getFlag());
		print2DArray(res.getResult());
	}
	
	public static void main(String[] args)
	{
		FindAN.testCase1();
	}
}