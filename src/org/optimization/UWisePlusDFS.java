package org.optimization;

import org.struct.GraphPoints;
import org.struct.Group;
import org.struct.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class UWisePlusDFS<T> {

    public int k;
    public Long ans;
    public ArrayList<ArrayList<GraphPoints<T>>> dsg;
    public ArrayList<GraphPoints<T>> pre_points;

    public UWisePlusDFS(ArrayList<ArrayList<GraphPoints<T>>> _dsg, int _k) {
        k = _k;
        dsg = _dsg;
        ans = Long.parseUnsignedLong("0");
    }

    public void dfs(Group<T> g_cur) {
        for (int idx: g_cur.getCandidateUnitGroupsBackward()) {
            Group g_plus = new Group(g_cur);
            g_plus.mergeUnitGroup(pre_points.get(idx));
            int point_number = g_plus.getNumberOfPoints();
            if (point_number == k) {
                //g_plus.print();
                ans++;
            }
            else if (point_number < k) {
                dfs(g_plus);
            }
        }
    }

    /*
    检查g的g_last，如果g_last点数小于等于k，则不用继续执行，返回false；否则返回true
    其中，当g_last点等于k时，方案数+1
     */
    public boolean evaluate(Group<T> g) {
        Group<T> g_last = new Group<>(g);
        for (Integer idx: g_last.getCandidateUnitGroupsBackward()) {
            g_last.mergeUnitGroup(pre_points.get(idx));
        }
        int number_of_points = g_last.getNumberOfPoints();
        if (number_of_points <= k) {
            if (number_of_points == k) {
                ans++;
            }
            return false;
        }
        return true;
    }

    public void run() {
        // 预处理dsg
        pre_points = Utils.preprocessing(dsg, k);

        // 逆序遍历数组
        for (int i = pre_points.size()-1; i >= 0; --i) {
            GraphPoints<T> unit = pre_points.get(i);
            Group<T> g_1 = new Group<>(unit);
            // 当前unit的元素个数为k
            if (g_1.getNumberOfPoints() == k) {
                //g_1.print();
                ans++;
                continue;
            }

            // 检测last集，点数小于k才继续执行
            if (evaluate(g_1)) {
                dfs(g_1);
            }
       }
       String fullClazzName = this.getClass().getName();
       String clazzName = fullClazzName.substring(fullClazzName.lastIndexOf(".") + 1);
       System.out.println(clazzName + "的G_skylineGroup数目为:" + ans);
    }
}

