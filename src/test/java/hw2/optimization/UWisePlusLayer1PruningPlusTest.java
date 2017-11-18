package hw2.optimization;

import hw2.algorithm.Experiments;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UWisePlusLayer1PruningPlusTest {

    @Test
    public void testDfs() {
        ArrayList<Integer> s1 = new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Integer> s2 = new ArrayList<>(Arrays.asList(5, 6));
        ArrayList<Integer> s3 = new ArrayList<>(Arrays.asList(8));
        ArrayList<Integer> s4 = new ArrayList<>(Arrays.asList(10, 11, 12, 13));

        ArrayList<ArrayList<Integer>> segs = new ArrayList<>(Arrays.asList(s1, s2, s3, s4));

        int[] prefix_sum = {3, 5, 6, 10};

        int res = UWisePlusLayer1PruningPlus.dfsOnLayer1(prefix_sum, segs, 0, 0, 1);
        assertEquals(10, res);

        res = UWisePlusLayer1PruningPlus.dfsOnLayer1(prefix_sum, segs, 0, 0, 2);
        assertEquals(45, res);

        res = UWisePlusLayer1PruningPlus.dfsOnLayer1(prefix_sum, segs, 0, 0, 3);
        assertEquals(120, res);
    }

    @Test
    public void testProcessLayer1Points() {
        // 与上例相同
        HashSet<Integer> parents_set = new HashSet<>(Arrays.asList(3, 4, 7, 9));
        int bound = 13;

        int res = UWisePlusLayer1PruningPlus.processLayer1Points(parents_set, bound, 1);
        assertEquals(10, res);

        res = UWisePlusLayer1PruningPlus.processLayer1Points(parents_set, bound, 2);
        assertEquals(45, res);

        res = UWisePlusLayer1PruningPlus.processLayer1Points(parents_set, bound, 3);
        assertEquals(120, res);

        // 以下是完全没有分组的情况
        res = UWisePlusLayer1PruningPlus.processLayer1Points(new HashSet<Integer>(), 4, 2);
        assertEquals(10, res);

        res = UWisePlusLayer1PruningPlus.processLayer1Points(new HashSet<Integer>(), 9, 3);
        assertEquals(120, res);
    }

    @Test
    public void testMain() {
        Experiments.RunSingleFile("anti_2.txt", 4, 2);
        Experiments.RunSingleFile("anti_2.txt", 4, 7);
        Experiments.RunSingleFile("anti_2.txt", 4, 8);
    }
}