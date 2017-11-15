package org.optimization;

import org.struct.GraphPoints;
import org.struct.Group;

import java.util.ArrayList;

public class UWisePlusPlus<T> extends UWisePlusDFS<T>{
    public UWisePlusPlus(ArrayList<ArrayList<GraphPoints<T>>> _dsg, int _k) {
        super(_dsg, _k);
    }
    public void dfs(Group<T> g_cur) {
        if (super.evaluate(g_cur)) {
            super.dfs(g_cur);
        }
    }
}
