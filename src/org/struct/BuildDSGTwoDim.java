package org.struct;
import java.util.*;



public class BuildDSGTwoDim {
	
	//按照x维升序，y维降序给graphPoints_list排序
	public ArrayList<TwoDim> SortGraphPoints(ArrayList<TwoDim> graphPoints_list){
		TwoDim sort = new TwoDim();
		Collections.sort(graphPoints_list,sort);
		//graphPoints_list.sort(new Mycomparator());
		
		return graphPoints_list;
	}
	
	//二维构造skylineLayer,Layer_index序号从1开始
	public ArrayList<ArrayList<GraphPoints<TwoDim>>> BuildSkylineLayerForTwoDim(ArrayList<TwoDim> graphPoints_list) {
		ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayer = new ArrayList<>();
		ArrayList<GraphPoints<TwoDim>> layers = new ArrayList<>();//每一层的节点
		ArrayList<GraphPoints<TwoDim>> tail_layers = new ArrayList<>(); //记录每一层的tail_points
		ArrayList<TwoDim> sorted_graphPoints = SortGraphPoints(graphPoints_list);
		
		//构造第一个GraphPoints元素
		TwoDim twodim_point = sorted_graphPoints.get(0);
		GraphPoints<TwoDim> first_points = new GraphPoints<TwoDim>(1,twodim_point);;
		int maxlayer = 1;
		layers.add(first_points);
		tail_layers.add(first_points);
		skylineLayer.add(layers);
		
		//System.out.println(sorted_graphPoints.size());
		
		//line5-line10
		for(int i=1;i<sorted_graphPoints.size();i++) {
			TwoDim cur_points = sorted_graphPoints.get(i);
			
			//line6-line8
			//若第一层tail_points无法支配cur_points,则cur_points属于第一层
			if(!IsDominateTwoDim(tail_layers.get(0).value, cur_points)) {
				GraphPoints<TwoDim> cur_graphPoints = new GraphPoints<TwoDim>(tail_layers.get(0).layer_index, cur_points);
				skylineLayer.get(0).add(cur_graphPoints);
				tail_layers.set(0,cur_graphPoints);
			}
			//line9-line11
			//若目前最大层的tail_points支配cur_points,则cur_points属于maxlayer+1层
			else if(IsDominateTwoDim(tail_layers.get(maxlayer-1).value, cur_points)) {
				maxlayer++;
				GraphPoints<TwoDim> cur_graphPoints = new GraphPoints<TwoDim>(maxlayer, cur_points);
				layers = new ArrayList<>();
				layers.add(cur_graphPoints);
				tail_layers.add(cur_graphPoints);
				skylineLayer.add(layers);
			}
			//line12-line15
			//二分查找layer
			else {
				int find_layer = FindLayerByBinary(skylineLayer, cur_points, maxlayer,tail_layers);
				if(find_layer == 0) {
					System.out.print("Error While Binary Find Layers");
				}else {
					GraphPoints<TwoDim> cur_graphPoints = new GraphPoints<TwoDim>(find_layer, cur_points);
					skylineLayer.get(find_layer-1).add(cur_graphPoints);
					tail_layers.set(find_layer-1,cur_graphPoints);
				}
			}
		}
		//PrintSkylineLayer(skylineLayer);
		return skylineLayer;
	}

	//二维判断d1是否支配d2
	public Boolean IsDominateTwoDim(TwoDim d1,TwoDim d2) {
		return ( (d1.x<=d2.x)&&(d1.y<=d2.y) );
	}
	
	//二分查找layer
	public int FindLayerByBinary(ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayer,TwoDim cur_points,int maxlayer,ArrayList<GraphPoints<TwoDim>> tail_layers ) {
		int left = 2;
		int right = maxlayer;
		int mid ;
		while(left < right) {
			mid = (left+right)/2;
			GraphPoints<TwoDim> mid_graphPoints = tail_layers.get(mid-1);
			//若mid不支配
			if(IsDominateTwoDim(mid_graphPoints.value, cur_points) == false ) {
				right = mid;
			}
			//若支配
			else {
				left = mid+1;
			}
		}
		return left;
	}
	
	//使用skylineLayer构建DSG
	public ArrayList<ArrayList<GraphPoints<TwoDim>>> BuildDsgForTwoDim(ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg) {
		
		for(int i=1;i<dsg.size();i++) {
			ArrayList<GraphPoints<TwoDim>> layer = dsg.get(i);
			for(GraphPoints<TwoDim> point : layer) {
				for(int j=0;j<i;j++) {
					ArrayList<GraphPoints<TwoDim>> previous_layer = dsg.get(j);
					for(GraphPoints<TwoDim> previous_point : previous_layer) {
						//前一层的layer中的某个点是否支配，若支配则设置孩子与父亲结点
						if(IsDominateTwoDim(previous_point.value, point.value)) {
							previous_point.children.add(point);
							//将父亲结点的父亲结点全部加入该节点的父亲节点，再将父亲节点加入
							//point.parents.addAll(previous_point.parents);
							point.parents.add(previous_point);
						}
					}
				}
				
			}
		}
		
		return dsg;
	}
	
	//根据DSG打印
	public void PrintDSG(ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg){
		for(ArrayList<GraphPoints<TwoDim>> layer:dsg) {
			String str = "";
			for(GraphPoints<TwoDim> point:layer) {
				str += "index:" + point.value.label + " parents:";
				for(GraphPoints<TwoDim> parent:point.parents) {
					str += parent.value.label + " ";
				}
				str += " children:";
				for(GraphPoints<TwoDim> children:point.children ) {
					str += children.value.label + " "; 
				}
				str += "\n";
			}
			System.out.println(str);
		}
		
	}
	
	
	//根据skylineLayer打印
	public void PrintSkylineLayer(ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayer) {
		System.out.println("层数为：" + skylineLayer.size());
		for(ArrayList<GraphPoints<TwoDim>> layer:skylineLayer) {
			String str_layer = "{";
			for(GraphPoints<TwoDim> point:layer) {
				str_layer += point.toString();
			}
			str_layer += "}";
			System.out.println(str_layer);
		}
		
	}
	
	
}
