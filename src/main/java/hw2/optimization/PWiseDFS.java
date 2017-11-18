package hw2.optimization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import hw2.struct.GraphPoints;
import hw2.struct.MultiDim;
import hw2.struct.Utils;


public class PWiseDFS <T>{

	public Long nodeNum = Long.parseUnsignedLong("0");
	public Long gans = Long.parseUnsignedLong("0");

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

				nodeNum+=1;

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
						gans += 1;
					}
					else{
						//tempStack.add(newGroups);
						skylineStack.push(newGroups);
					}
				}
			}

		}
		System.out.println("得到的G-Skyline数目为：" +  gans  + " 遍历结点的数目:" + nodeNum );
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
}
