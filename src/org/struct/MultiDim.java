package org.struct;

import java.util.Comparator;

public class MultiDim implements Comparator<MultiDim> {
	public double[] values;
	
	public int label;

	public MultiDim(){

	}

	public MultiDim(double[] values,int label) {
		this.values = values;
		this.label = label;
	}

	public String toString() {
		return this.values.toString(); 
	}

	@Override
	public int compare(MultiDim p1, MultiDim p2) {
		for (int i = 0; i < p1.values.length; i++) {
			if (p1.values[i] == p2.values[i]) continue;
			else if (p1.values[i] >= p2.values[i]) return 1;
			else return -1;
		}
		return -1;
	}
}
