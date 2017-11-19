package hw2.optimization;

import hw2.struct.GraphPoints;
import hw2.struct.Group;
import hw2.struct.Utils;

import java.util.ArrayList;
import java.util.HashSet;

public class UWisePlusLayer1PruningPlus<T> extends UWisePlusLayer1Pruning<T> {

    public UWisePlusLayer1PruningPlus(ArrayList<ArrayList<GraphPoints<T>>> _dsg, int _k) {
        super(_dsg, _k);
    }

    public static Long dfsOnLayer1(int[] prefix_sum, ArrayList<ArrayList<Integer>> segments, int depth, int cur, int goal) {
        int lower_bound;
        if (depth == segments.size() - 1) {
            lower_bound = Math.max(goal - cur, 0);
        }
        else {
            lower_bound = Math.max(cur - prefix_sum[segments.size()-depth-2], 0);
        }

        Long num_sol = new Long(0);
        int upper_bound = Math.min(goal - cur, segments.get(depth).size());
        for (int i = lower_bound; i <= upper_bound; ++i) {
            Long factor = Utils.getCombinationNumber(segments.get(depth).size(), i);
            if (cur + i == goal) {
                num_sol += factor;
            }
            else if (depth < segments.size() - 1) {
                num_sol += factor * dfsOnLayer1(prefix_sum, segments, depth+1, cur+i, goal);
            }
        }
        return num_sol;
    }

    public static Long processLayer1Points(HashSet<Integer> parents_set, int bound, int goal) {
        ArrayList<ArrayList<Integer>> segments = new ArrayList<>();
        ArrayList<Integer> cur_seg = new ArrayList<>();
        for (int i = bound; i >= 0; --i) {
            if (parents_set.contains(i)) {
                segments.add(cur_seg);
                cur_seg = new ArrayList<>();
            }
            else {
                cur_seg.add(i);
            }
        }
        if (cur_seg.size() > 0) {
            segments.add(cur_seg);
        }
        int[] prefix_sum = new int[segments.size()];
        for (int i = 0; i < segments.size(); ++i) {
            prefix_sum[i] = segments.get(i).size();
            if (i > 0) {
                prefix_sum[i] += prefix_sum[i-1];
            }
        }
        return dfsOnLayer1(prefix_sum, segments, 0, 0, goal);
    }

    public static Long getCombinationNumberWithoutAncestors(HashSet<Integer> parents_set, int bound, int goal) {
        int n = bound + 1;
        int m = goal;
        for (int i = 0; i <= bound; ++i) {
            if (parents_set.contains(i)) {
                n--;
            }
        }
        return Utils.getCombinationNumber(n,m);
    }

    @Override
    public void dfs(Group<T> g_cur) {
        for (int idx: g_cur.getCandidateUnitGroupsBackward()) {
            GraphPoints pnt = pre_points.get(idx);
            if (pnt.layer_index == 1) {
//                ans += processLayer1Points(g_cur.getIndicesOfParents(), idx, k - g_cur.getNumberOfPoints());
                ans += getCombinationNumberWithoutAncestors(g_cur.getIndicesOfParents(), idx, k - g_cur.getNumberOfPoints());
                break;
            }
            else {
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
    }
}
