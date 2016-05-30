/*
* Author: Ezana Woldegabriel
* Description: Class to package the output of locate
*/
public class LocateOut {
	private Coordinate<Integer> AN,AN2;
	int exflag;
	
	public LocateOut(){}
	
	public Coordinate<Integer> getAN(){
		return this.AN;
	}
	
	public void setAN(Coordinate<Integer> a){
		this.AN = a;
	}
	
	public Coordinate<Integer> getAN2(){
		return this.AN2;
	}
	
	public void setAN2(Coordinate<Integer> a){
		this.AN2 = a;
	}
	
	public int getexFlag(){
		return this.exflag;
	}
	
	public void setexFlag(int b){
		this.exflag = b;
	}
}
