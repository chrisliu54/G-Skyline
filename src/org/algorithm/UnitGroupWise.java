package org.algorithm;

import org.struct.GraphPoints;
import org.struct.Group;
import org.struct.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class UnitGroupWise {

    public static<T> void run(ArrayList<ArrayList<GraphPoints<T>>> dsg, int k) {
        // 预处理dsg
        ArrayList<GraphPoints<T>> pre_points = Utils.preprocessing(dsg, k);
        
        int ans = 0;
        int total_point_size = pre_points.size();

        // 逆序遍历数组
        for (int i = 0; i < total_point_size; ++i) {
            GraphPoints<T> unit = pre_points.get(i);
            Group<T> g_1 = new Group<>(unit);
            // 当前unit里元素个数大于等于k
            int number_of_point = g_1.getNumberOfPoints();
            if (number_of_point >= k) {
                //g_1.print();
                if (number_of_point == k) {
                    ans++;
                }
                continue;
            }

            Queue<Group> queue = new LinkedList<>();
            queue.offer(g_1);
            while (!queue.isEmpty()) {
                Group<T> g = queue.poll();
                for (int idx: g.getCandidateUnitGroupsForward(total_point_size)) {
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
        }
        System.out.println("UnitGroupWise的G_skylineGroup数目为:" + ans);
    }
}
