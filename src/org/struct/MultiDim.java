package org.struct;

import java.util.Comparator;

public class MultiDim implements Comparator<MultiDim> {
	public double[] values;
	public int label;
	
	public MultiDim() {
		
	}
	
	public MultiDim(double[] values,int label) {
		this.values = values;
		this.label = label;
	}
	
	public String toString() {
		return this.values.toString() + ",label:" + label;
	}

	@Override
	public int compare(MultiDim o1, MultiDim o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
