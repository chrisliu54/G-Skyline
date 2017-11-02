package org.struct;
import java.util.*;



public class BuildDSGMultiDim {

    //从第一维度开始给graphPoints_list排序
    public ArrayList<MultiDim> SortGraphPoints(ArrayList<MultiDim> graphPoints_list){
        MultiDim sort = new MultiDim();
        Collections.sort(graphPoints_list,sort);
        return graphPoints_list;
    }

    //多维构造skylineLayer,Layer_index序号从1开始
    public ArrayList<ArrayList<GraphPoints<MultiDim>>> BuildSkylineLayerForMultiDim(ArrayList<MultiDim> graphPoints_list) {
        ArrayList<ArrayList<GraphPoints<MultiDim>>> skylineLayer = new ArrayList<>();
        ArrayList<MultiDim> sorted_graphPoints = SortGraphPoints(graphPoints_list);
        for (int i = 0; i < graphPoints_list.size(); i++) skylineLayer.add(new ArrayList<>());
        //构造第一个GraphPoints元素
        MultiDim multi_dim_point = sorted_graphPoints.get(0);
        GraphPoints<MultiDim> first_points = new GraphPoints<MultiDim>(1,multi_dim_point);
        skylineLayer.get(0).add(first_points);
        int maxlayer = 1;

        //line5-line10
        for(int i=1;i<sorted_graphPoints.size();i++) {
            MultiDim cur_points = sorted_graphPoints.get(i);

            //line6-line8
            //若第一层layers无法支配cur_points,则cur_points属于第一层
            boolean cantDominate = false;
            for(GraphPoints<MultiDim> point: skylineLayer.get(0)){
                if (IsDominateMultiDim(point.value,cur_points)){
                    cantDominate = true;
                    break;
                }
            }
            if (!cantDominate) {
                GraphPoints<MultiDim> cur_graphPoints = new GraphPoints<MultiDim>(1, cur_points);
                skylineLayer.get(0).add(cur_graphPoints);
                continue;
            }

            for (int idx = 1; idx < skylineLayer.size(); idx++) {
                boolean flag = false;
                for(GraphPoints<MultiDim> point: skylineLayer.get(idx)){
                    if (IsDominateMultiDim(point.value,cur_points)){
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    GraphPoints<MultiDim> cur_graphPoints = new GraphPoints<MultiDim>(idx +1, cur_points);
                    skylineLayer.get(idx).add(cur_graphPoints);
                    maxlayer =  maxlayer < idx ? idx : maxlayer;
                    break;
                }
            }
        }

        //去除空元素
        ArrayList<ArrayList<GraphPoints<MultiDim>>> realSkylineLayer = new ArrayList<>();
        for(int i =0;i<=maxlayer;i++){
            realSkylineLayer.add(skylineLayer.get(i));
        }
        //PrintSkylineLayer(realSkylineLayer);
        return realSkylineLayer;
    }

    //多维判断p1是否支配p2
    public Boolean IsDominateMultiDim(MultiDim p1,MultiDim p2) {
        for (int i = 0; i < p1.values.length; i++) if (p1.values[i] > p2.values[i]) return false;
        return true;
    }

    //使用skylineLayer构建DSG
    public ArrayList<ArrayList<GraphPoints<MultiDim>>> BuildDsgForMultiDim(ArrayList<ArrayList<GraphPoints<MultiDim>>> dsg) {

        for(int i=1;i<dsg.size();i++) {
            ArrayList<GraphPoints<MultiDim>> layer = dsg.get(i);
            for(GraphPoints<MultiDim> point : layer) {
                for(int j=0;j<i;j++) {
                    ArrayList<GraphPoints<MultiDim>> previous_layer = dsg.get(j);
                    for(GraphPoints<MultiDim> previous_point : previous_layer) {
                        //前一层的layer中的某个点是否支配，若支配则设置孩子与父亲结点
                        if(IsDominateMultiDim(previous_point.value, point.value)) {
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
    public void PrintDSG(ArrayList<ArrayList<GraphPoints<MultiDim>>> dsg){
        for(ArrayList<GraphPoints<MultiDim>> layer:dsg) {
            String str = "";
            for(GraphPoints<MultiDim> point:layer) {
                str += "index:" + point.value.label + " parents:";
                for(GraphPoints<MultiDim> parent:point.parents) {
                    str += parent.value.label + " ";
                }
                str += " children:";
                for(GraphPoints<MultiDim> children:point.children ) {
                    str += children.value.label + " ";
                }
                str += "\n";
            }
            System.out.println(str);
        }

    }


    //根据skylineLayer打印
    public void PrintSkylineLayer(ArrayList<ArrayList<GraphPoints<MultiDim>>> skylineLayer) {
        System.out.println("层数为：" + skylineLayer.size());
        for(ArrayList<GraphPoints<MultiDim>> layer:skylineLayer) {
            String str_layer = "{";
            for(GraphPoints<MultiDim> point:layer) {
                str_layer += point.toString();
            }
            str_layer += "}";
            System.out.println(str_layer);
        }

    }


}
