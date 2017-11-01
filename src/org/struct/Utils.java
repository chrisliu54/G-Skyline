package org.struct;

import java.util.ArrayList;

public class Utils {
	 public static int MAXINT = (int)1e10;

	    //预处理之后的所有几点集合
	    public static<T> ArrayList<GraphPoints<T>> preprocessing(ArrayList<ArrayList<GraphPoints<T>>> dsg, int k ){

	        ArrayList<GraphPoints<T>> pre_points = new ArrayList<>();
	        //预处理，将|U| > k 的结点删除
	        //循环DSG中每一层
	        for(ArrayList<GraphPoints<T>> layer : dsg) {
	            //循环每一个layer中的每一个节点
	            for(GraphPoints<T> point : layer) {
	                if(point.layer_index <= k && point.parents.size()+1 <= k){
	                    //判断该节点的|U|是否大于k
	                    pre_points.add(point);
	                    //System.out.println(point.toString());
	                }
	            }
	        }
	        //重置预处理后的index，按照在数组中的顺序从0 ~ size()-1
	        for(int i=0;i<pre_points.size();i++) pre_points.get(i).point_index = i;
	        return pre_points;
	    }
}
