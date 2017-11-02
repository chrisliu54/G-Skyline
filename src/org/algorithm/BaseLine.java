package org.algorithm;

import java.util.ArrayList;


public class BaseLine <T>{

	//返回一个包含Cnk个ArrayList<T>的集合，ArrayList<T>中T的个数为k
	public void BFSbuildCnkGroup(int k,int start,ArrayList<T> graphPoints,ArrayList<T> now,ArrayList<ArrayList<T>> ans) {
		if(now.size() == k) {
			ans.add(now);
		}else {
			for(int i=start;i<graphPoints.size();i++) {
				ArrayList<T> temp = new ArrayList<>(now);
				temp.add(graphPoints.get(i));
				BFSbuildCnkGroup(k,i+1,graphPoints,temp,ans);
			}
		}
	}
}
