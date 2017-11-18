package hw2.algorithm;

import hw2.struct.GraphPoints;
import hw2.struct.Group;
import hw2.struct.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class UnitGroupWisePlus {

    public static<T> void run(ArrayList<ArrayList<GraphPoints<T>>> dsg, int k) {
        // 预处理dsg
        ArrayList<GraphPoints<T>> pre_points = Utils.preprocessing(dsg, k);
        int ans = 0;
        long startTime,Time1 = 0,Time2 = 0,Time3 = 0;

        // 逆序遍历数组
        for (int i = pre_points.size()-1; i >= 0; --i) {
        	
        	startTime = System.currentTimeMillis();
        	GraphPoints<T> unit = pre_points.get(i);
            Group<T> g_1 = new Group<>(unit);
            // 当前unit的元素个数为k
            if (g_1.getNumberOfPoints() == k) {
                //g_1.print();
                ans++;
                continue;
            }
            Time1 += System.currentTimeMillis() - startTime;
            
            startTime = System.currentTimeMillis();
            // 求g_1的last集
            Group<T> g_last = new Group<>(g_1);
            for (Integer idx: g_last.getCandidateUnitGroupsBackward()) {
                g_last.mergeUnitGroup(pre_points.get(idx));
            }
            int number_of_points = g_last.getNumberOfPoints();
            if (number_of_points == k) {
                //g_last.print();
                ans++;
                break;
            }
            else if (number_of_points < k) {
                break;
            }
            Time2 += System.currentTimeMillis() - startTime;
            
            startTime = System.currentTimeMillis();
            Queue<Group> queue = new LinkedList<>();
            queue.offer(g_1);
            while (!queue.isEmpty()) {
                Group<T> g = queue.poll();
                for (int idx: g.getCandidateUnitGroupsBackward()) {
                    Group g_plus = new Group(g);
                    g_plus.mergeUnitGroup(pre_points.get(idx));
                    int point_number = g_plus.getNumberOfPoints();
                    if (point_number == k) {
                        //g_plus.print();
                        ans++;
                    }
                    else if (point_number < k) {
                        queue.offer(g_plus);
                    }
                }
            }
            Time3 += System.currentTimeMillis() - startTime;
            
        }
        //System.out.println("判读单独的U是否等于K，花费时间为：" + Time1);
        //System.out.println("判断Glast是否大于等于K，花费时间为：" + Time2);
        //System.out.println("TailSet以及SuperSet剪枝，花费时间为：" + Time3);
        System.out.println("UnitGroupWise+的G_skylineGroup数目为:" + ans);
    }
}
