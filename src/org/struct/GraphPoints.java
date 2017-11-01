package org.struct;

import java.util.ArrayList;

public class GraphPoints<T>{
	
	public int layer_index;
	public int point_index;
	public ArrayList<GraphPoints<T>> parents;
	public ArrayList<GraphPoints<T>> children;
	public T value;
	
	public GraphPoints(int layer_index,T value){
		this.layer_index = layer_index;
		this.value = value;
		this.parents = new ArrayList<>();
		this.children = new ArrayList<>();
	}
	public String toString(){
		return "(parentsize=" + parents.size() + ",childsize=" + children.size() + ",layer_index=" + layer_index + ",point_index=" + point_index + ",value=" + value.toString() + ")";
	}

}
