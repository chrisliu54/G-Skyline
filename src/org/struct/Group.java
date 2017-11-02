package org.struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Group<T> {

    public HashSet<Integer> point_indices;
    public List<GraphPoints<T>> units;

    public Group(GraphPoints<T> unit) {
        units = new ArrayList(Arrays.asList(unit));
        point_indices = new HashSet<>(Arrays.asList(unit.point_index));
        for (GraphPoints<T> parent: unit.parents) {
            point_indices.add(parent.point_index);
        }
    }

    public Group(Group<T> g) {
        units = new ArrayList<>();
        point_indices = new HashSet<>();
        for (Integer i: g.point_indices) {
            point_indices.add(i);
        }
        for (GraphPoints<T> unit: g.units) {
            units.add(unit);
        }
    }

    public void print() {
        String output = point_indices.stream()
                .map(x -> x.toString())
                .collect(Collectors.joining(", "));
        System.out.println(output);
    }

    public int getNumberOfPoints() {
        return point_indices.size();
    }

    public int getNumberOfUnits() {
        return units.size();
    }

    public HashSet<Integer> getIndicesOfParents() {
        HashSet<Integer> parents_set = new HashSet<>();
        for (GraphPoints<T> unit: units) {
            for (GraphPoints<T> parent: unit.parents) {
                parents_set.add(parent.point_index);
            }
        }
        return parents_set;
    }

    public List<Integer> getCandidateUnitGroupsBackward() {
        int min_index = Utils.MAXINT;
        for (GraphPoints<T> unit: units) {
            min_index = min(min_index, unit.point_index);
        }
        List<Integer> candidate = new ArrayList<>();
        HashSet<Integer> parents_set = getIndicesOfParents();
        for (int i = 0; i < min_index; ++i) {
            if (!parents_set.contains(i)) {
                candidate.add(i);
            }
        }
        return candidate;
    }

    public HashSet<Integer> getIndicesOfChildren() {
        HashSet<Integer> children_set = new HashSet<>();
        for (GraphPoints<T> unit: units) {
            for (GraphPoints<T> parent: unit.children) {
                children_set.add(parent.point_index);
            }
        }
        return children_set;
    }

    public List<Integer> getCandidateUnitGroupsForward(int total_point_size) {
        int max_index = Utils.MININT;
        for (GraphPoints<T> unit: units) {
            max_index = max(max_index, unit.point_index);
        }
        List<Integer> candidate = new ArrayList<>();
        HashSet<Integer> children_set = getIndicesOfChildren();
        for (int i = max_index+1; i < total_point_size; ++i) {
            if (!children_set.contains(i)) {
                candidate.add(i);
            }
        }
        return candidate;
    }

    public Group mergeUnitGroup(GraphPoints<T> unit) {
        units.add(unit);
        point_indices.add(unit.point_index);
        for (GraphPoints<T> parent: unit.parents) {
            point_indices.add(parent.point_index);
        }
        return this;
    }
}
