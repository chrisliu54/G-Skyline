package org.optimization;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import org.algorithm.PointWise;
import org.algorithm.UnitGroupWisePlus;
import org.main.ReadData;
import org.struct.BuildDSGMultiDim;
import org.struct.BuildDSGTwoDim;
import org.struct.GraphPoints;
import org.struct.MultiDim;
import org.struct.TwoDim;
import org.struct.Utils;



public class PWiseDFS <T>{
	
	//根据预处理的Pre_dsg，执行pointWiseDFS算法
	public ArrayList<ArrayList<GraphPoints<T>>> pointWiseDFS(ArrayList<ArrayList<GraphPoints<T>>> dsgs,int k){
	
		ArrayList<GraphPoints<T>> pre_points = Utils.preprocessing(dsgs, k);
		//System.out.println("预处理后的节点数为：" + pre_points.size());
		
		ArrayList<ArrayList<GraphPoints<T>>> k_skylineGroups = new ArrayList<>();
		ArrayList<GraphPoints<T>> groups;
		Stack<ArrayList<GraphPoints<T>>> skylineStack = new Stack<>();
		
		//DSG中的所有skyline点
		HashSet<GraphPoints<T>> skylinePoints = new HashSet<GraphPoints<T>>();
		skylinePoints.addAll(dsgs.get(0));
		
		//初始化栈，将第一层元素加入栈中
//		for (int i=dsgs.get(0).size()-1;i>=0;i--) {
//			groups = new ArrayList<>();
//			groups.add(dsgs.get(0).get(i));
//			skylineStack.push(groups);
//		}
		
		for(GraphPoints<T> points:dsgs.get(0)) {
			groups = new ArrayList<>();
			groups.add(points);
			skylineStack.push(groups);
		}
		
		int ans = 0;
		
		//栈不为空，则循环
		while(!skylineStack.empty()) {
			
			groups = new ArrayList<>();
			groups.addAll(skylineStack.pop());
			//System.out.println(skylineStack.size());
			
			int tail = groups.size() > 0 ? groups.get(groups.size()-1).point_index + 1:0;
			int tail_layer = groups.get(groups.size()-1).layer_index;
			
			//得到该groups中每一个节点的孩子结点
			HashSet<GraphPoints<T>> childrenSet = new HashSet<>();
			for(GraphPoints<T> point : groups) {
				childrenSet.addAll(point.children);
			}
			
			//剪枝操作
			//1.如果tail set中的结点不是newGroups的孩子结点或者不是skyline结点，则忽略
			for(int j=tail;j<pre_points.size();j++) {
				
				ans+=1;
				
				//line6-line10 剪枝
				//System.out.println("tail=" + tail + " pre=" + pre_points.size() + " j=" + j);
				GraphPoints<T> cur_point = pre_points.get(j);
				if( !childrenSet.contains(cur_point) && !skylinePoints.contains(cur_point)) continue;
				if( cur_point.layer_index - tail_layer >= 2 ) break;
				
				//line10-line14  入栈
				ArrayList<GraphPoints<T>> newGroups = new ArrayList<>();
				newGroups.addAll(groups);
				newGroups.add(cur_point);
				int k_newGroups = IsSkylineGroup(newGroups);
				
				//运行较慢的程序
//				if (k_newGroups == newGroups.size() && newGroups.size() == k) {
//					k_skylineGroups.add(newGroups);
//				}	
//				else if (k_newGroups < k && newGroups.size() < k) {
//					//tempStack.add(newGroups);
//					skylineStack.push(newGroups);
//				}
				
				if (k_newGroups == groups.size()+1 ) {
					
					if(newGroups.size() == k) {
						k_skylineGroups.add(newGroups);
					}	
					else{
						//tempStack.add(newGroups);
						skylineStack.push(newGroups);
					}
				}
				
			}
		}
		System.out.println("得到的G-Skyline数目为：" + k_skylineGroups.size()  + " 遍历结点的数目:" + ans );
		return k_skylineGroups;
		
	}
		
	public int IsSkylineGroup(ArrayList<GraphPoints<T>> groups) {
		HashSet<GraphPoints<T>> hashSet = new HashSet<>();
		for(GraphPoints<T> tail_points: groups) {
			hashSet.addAll(tail_points.parents);
			hashSet.add(tail_points);
		}
		return hashSet.size();
	}
	
	
	public static void main(String[] args) {
		
		ReadData readData = new ReadData();
		//ArrayList<MultiDim> multi_dim_points = readData.buildMultiPoints("inde_2.txt");
		ArrayList<TwoDim> twodim_points = readData.buildTwoPoints("inde_2.txt");

//		BuildDSGMultiDim dsgMultiDim = new BuildDSGMultiDim();
//		ArrayList<ArrayList<GraphPoints<MultiDim>>> skylineLayerMultiDim = dsgMultiDim.BuildSkylineLayerForMultiDim(multi_dim_points);
//		ArrayList<ArrayList<GraphPoints<MultiDim>>> dsg_multi_dim = dsgMultiDim.BuildDsgForMultiDim(skylineLayerMultiDim,4);
//		//dsgMultiDim.PrintDSG(dsg_multi_dim);
//		
//		long startTime1 = System.currentTimeMillis();
//		PointWise<MultiDim> pointWise = new PointWise<MultiDim>();
//		pointWise.pointWise(dsg_multi_dim, 12);
//		long endTime1 = System.currentTimeMillis();
//		
//		long startTime2 = System.currentTimeMillis();
//		PWisePlus<MultiDim> pWisePlus = new PWisePlus<MultiDim>();
//		pWisePlus.pWisePlus(dsg_multi_dim, 12);
//		long endTime2 = System.currentTimeMillis();
//		
//		long startTime3 = System.currentTimeMillis();
//		PWiseDFS<MultiDim> pWiseDFS = new PWiseDFS<MultiDim>();
//		//pWiseDFS.pointWiseDFS(dsg_multi_dim, 12);
//		long endTime3 = System.currentTimeMillis();
//		
//		long startTime4 = System.currentTimeMillis();
//		UnitGroupWisePlus.run(dsg_multi_dim, 12);
//		long endTime4 = System.currentTimeMillis();
		
		//BuildDSGMultiDim dsgMultiDim = new BuildDSGMultiDim();
		int k = 12;
		
		BuildDSGTwoDim bDsg = new BuildDSGTwoDim();
		ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayerTwoDim = bDsg.BuildSkylineLayerForTwoDim(twodim_points);
		ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg = bDsg.BuildDsgForTwoDim(skylineLayerTwoDim,k);
		//dsgMultiDim.PrintDSG(dsg_multi_dim);
		
		long startTime1 = System.currentTimeMillis();
		PointWise<TwoDim> pointWise = new PointWise<TwoDim>();
		pointWise.pointWise(dsg, k);
		long endTime1 = System.currentTimeMillis();
		
		long startTime2 = System.currentTimeMillis();
		PWisePlus<TwoDim> pWisePlus = new PWisePlus<TwoDim>();
		pWisePlus.pWisePlus(dsg, k);
		long endTime2 = System.currentTimeMillis();
		
		long startTime3 = System.currentTimeMillis();
		PWiseDFS<TwoDim> pWiseDFS = new PWiseDFS<TwoDim>();
		pWiseDFS.pointWiseDFS(dsg, k);
		long endTime3 = System.currentTimeMillis();
		
		long startTime4 = System.currentTimeMillis();
		UnitGroupWisePlus.run(dsg, k);
		long endTime4 = System.currentTimeMillis();
	
		System.out.println("程序pointwise运行时间为：" + (endTime1-startTime1) + "ms" );
		System.out.println("程序pointwisePlus运行时间为：" + (endTime2-startTime2) + "ms" );
		System.out.println("程序pointwiseDFS运行时间为：" + (endTime3-startTime3) + "ms" );
		System.out.println("程序UnitGroupWisePlus运行时间为：" + (endTime4-startTime4) + "ms" );
		
	}
}
