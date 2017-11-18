package hw2.optimization;

import hw2.struct.GraphPoints;
import hw2.struct.Group;
import hw2.struct.Utils;

import java.util.ArrayList;

public class UWisePlusLayer1Pruning<T> extends UWisePlusDFS<T>{

    public UWisePlusLayer1Pruning(ArrayList<ArrayList<GraphPoints<T>>> _dsg, int _k) {
        super(_dsg, _k);
    }

    @Override
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

            // 检测last集，点数大于k才继续执行
            if (evaluate(g_1)) {
                if (unit.layer_index == 1) {
                    // 从当前的i+1个点中选出k个点
                    ans += Utils.getCombinationNumber(i+1, k);
                    break;
                }
                else {
                    dfs(g_1);
                }
            }
        }
        String fullClazzName = this.getClass().getName();
        String clazzName = fullClazzName.substring(fullClazzName.lastIndexOf(".") + 1);
        System.out.println(" " + clazzName + "的G_skylineGroup数目为:" + ans);
    }
}
