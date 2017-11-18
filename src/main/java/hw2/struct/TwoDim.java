package hw2.struct;

import java.util.Comparator;

public class TwoDim implements Comparator<TwoDim>{
	
	public double x;
	public double y;
	public int label;
	
	public TwoDim() {
		
	}
	
	public TwoDim(double x,double y,int label) {
		this.x = x;
		this.y = y;
		this.label = label;
	}
	
	public String toString() {
		String string = "x=" + x + ",y=" + y;
		return "label=" + label;
	}

	@Override
	public int compare(TwoDim o1, TwoDim o2) {
		// TODO Auto-generated method stub
		if(o1.x > o2.x) return 1;
		else if(o1.x < o2.x) return -1;
		else {
			if(o1.y > o2.y) return 1;
			else return -1;
		}
	}

}
