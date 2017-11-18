package hw2.algorithm;

import java.util.*;

import hw2.struct.GraphPoints;
import hw2.struct.Utils;

public class PointWise<T> {

//	//预处理之后的所有几点集合
//	public ArrayList<GraphPoints<T>> preprocessing(ArrayList<ArrayList<GraphPoints<T>>> dsgs,int k ){
//
//		ArrayList<GraphPoints<T>> pre_points = new ArrayList<GraphPoints<T>>();
//		//预处理，将|U| > k 的结点删除
//		//循环DSG中每一层
//		for(ArrayList<GraphPoints<T>> layer : dsgs) {
//			//循环每一个layer中的每一个节点
//			for(GraphPoints<T> point : layer) {
//				if(point.layer_index <= k && point.parents.size()+1 <= k){
//					//判断该节点的|U|是否大于k
//					pre_points.add(point);
//					//System.out.println(point.toString());
//				}
//			}
//		}
//		//重置预处理后的index，按照在数组中的顺序从0 ~ size()-1
//		for(int i=0;i<pre_points.size();i++) pre_points.get(i).point_index = i;
//		return pre_points;
//	}

	public Long nodeNum = Long.parseUnsignedLong("0");
	public Long gans = Long.parseUnsignedLong("0");

	//根据预处理的Pre_dsg，执行pointWise算法
	public int pointWise(ArrayList<ArrayList<GraphPoints<T>>> dsgs,int k){


		ArrayList<GraphPoints<T>> pre_points = Utils.preprocessing(dsgs, k);
		//System.out.println("预处理后的节点数为：" + pre_points.size());

		//最终结果,groups的集合
		ArrayList<ArrayList<ArrayList<GraphPoints<T>>>> k_skylineGgroups = new ArrayList<ArrayList<ArrayList<GraphPoints<T>>>>();
		ArrayList<ArrayList<GraphPoints<T>>> levelGroup = new ArrayList<>();
		ArrayList<GraphPoints<T>> group = new ArrayList<GraphPoints<T>>();

		//DSG中的所有skyline点
		HashSet<GraphPoints<T>> skylinePoints = new HashSet<GraphPoints<T>>();
		skylinePoints.addAll(dsgs.get(0));
		//System.out.println("skyline points (first layer) number: "+ skylinePoints.size());

		//初始化k_skylineGgroups(0),第一层group,每个group包含一个节点
		for(GraphPoints<T> point:dsgs.get(0)) {
			group = new ArrayList<>();
			group.add(point);
			levelGroup.add(group);
		}
		//System.out.println("第一层Groups:");
		//PrintGroup(levelGroup);
		k_skylineGgroups.add(levelGroup);


		//line2 循环每一个k
		for(int i=1;i<k;i++) {

			//System.out.println("开始计算第" + (i+1) + "层Groups:");

			levelGroup = new ArrayList<ArrayList<GraphPoints<T>>>();
			//line3-line14
			//循环i-1层中的每一个group集合
			for(ArrayList<GraphPoints<T>> groups:k_skylineGgroups.get(i-1)) {

				//line4 - line5
				//得到该groups中每一个节点的孩子结点
				HashSet<GraphPoints<T>> childrenSet = new HashSet<>();
				for(GraphPoints<T> point : groups) {
					childrenSet.addAll(point.children);
				}

				//line6-line14
				int tail = 0;
				if(groups.size()>0) tail = groups.get(groups.size()-1).point_index + 1;

				for(int j=tail;j<pre_points.size();j++) {

					nodeNum+=1;

					//line6-line10 剪枝
					//System.out.println("tail=" + tail + " pre=" + pre_points.size() + " j=" + j);
					GraphPoints<T> cur_point = pre_points.get(j);
					if( !childrenSet.contains(cur_point) && !skylinePoints.contains(cur_point)) continue;
					if( cur_point.layer_index - groups.get(groups.size()-1).layer_index >= 2 ) continue;

					//line10-line14  判断是否为 K-skylineGgroups并加入levelGroup
					ArrayList<GraphPoints<T>> newGroup = new ArrayList<GraphPoints<T>>();
					newGroup.addAll(groups);
					newGroup.add(cur_point);
					if( IsSkylineGroup(newGroup,i+1)) {
						gans += i+1 == k?1:0;
						levelGroup.add(newGroup);
					}
				}
				//将levelGroup加入k_skylineGgroups
			}
			k_skylineGgroups.add(levelGroup);
			//PrintGroup(levelGroup);
		}
		//PrintGroup(k_skylineGgroups.get(k_skylineGgroups.size()-1));
		System.out.println("得到的G-Skyline数目为：" + gans + " 遍历结点的数目:" + nodeNum );
		return k_skylineGgroups.get(k_skylineGgroups.size()-1).size();

	}

	public boolean IsSkylineGroup(ArrayList<GraphPoints<T>> groups , int k ) {
		HashSet<GraphPoints<T>> hashSet = new HashSet<>();
		for(GraphPoints<T> tail_points: groups) {
			hashSet.addAll(tail_points.parents);
			hashSet.add(tail_points);
		}
		return (hashSet.size() == k);
	}

	public void PrintGroup(ArrayList<ArrayList<GraphPoints<T>>> levelgroups) {

		for(ArrayList<GraphPoints<T>> group : levelgroups) {
			String group_str = "{";
			for(GraphPoints<T> points : group) {
				if(group_str.length() != 1) group_str += ",";
				group_str += "(layer:" + points.layer_index + "," + points.value.toString() + ")";
			}
			group_str += "}";
			System.out.println(group_str);
		}

	}

}
