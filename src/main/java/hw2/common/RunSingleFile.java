package hw2.common;

import hw2.algorithm.PointWise;
import hw2.algorithm.UnitGroupWise;
import hw2.algorithm.UnitGroupWisePlus;
import hw2.optimization.*;
import hw2.struct.*;

import java.util.ArrayList;

public class RunSingleFile {

    // 3个参数，data_filename,group_size,algorithm_kind
    // kind为运行的算法（0为pointWise，1为UnitWise，2为UnitWisePlus,3为uWisePlusDFS，4为uWisePlusPlus，5为pWiseDFS，6为pWisePlus，7为UWisePlusLayer1Pruning，8为UWisePlusLayer1PruningPlus）
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        int kind = Integer.parseInt(args[2]);
        run(filename, k, kind);
    }

    public static void run(String filename, int k, int kind) {
        ReadData readData = new ReadData();
        long startTime ;
        if(filename.contains("2")) {
            ArrayList<TwoDim> twodim_points = readData.buildTwoPoints(filename);

            startTime = System.currentTimeMillis();
            BuildDSGTwoDim bDsg = new BuildDSGTwoDim();
            ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayerTwoDim = bDsg.BuildSkylineLayerForTwoDim(twodim_points);
            ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg = bDsg.BuildDsgForTwoDim(skylineLayerTwoDim,k);

            //运行pointWise算法
            if(kind == 0) {
                PointWise<TwoDim> pointWise = new PointWise<TwoDim>();
                pointWise.pointWise(dsg, k);
                System.out.println(filename + " PointWise Time:" + (System.currentTimeMillis()-startTime) + "ms" );
            }else if(kind == 1) {
                UnitGroupWise.run(dsg, k);
                System.out.println(filename + " UnitWise Time:" + (System.currentTimeMillis()-startTime) + "ms" );
            }else if(kind == 2) {
                UnitGroupWisePlus.run(dsg, k);
                System.out.println(filename + " UnitWisePlus Time:" + (System.currentTimeMillis()-startTime) + "ms" );
            }else if(kind == 3) {
                UWisePlusDFS<TwoDim> uWisePlusDFS = new UWisePlusDFS<>(dsg, k);
                uWisePlusDFS.run();
                System.out.println(filename + " UWisePlusDFS Time:" + (System.currentTimeMillis()-startTime) + "ms");
            }else if(kind == 4) {
                UWisePlusPlus<TwoDim> uWisePlusPlus = new UWisePlusPlus<>(dsg, k);
                uWisePlusPlus.run();
                System.out.println(filename + " UWise++ Time:" + (System.currentTimeMillis()-startTime) + "ms");
            }else if(kind == 5) {
                PWiseDFS<TwoDim> pWiseDFS = new PWiseDFS<>();
                pWiseDFS.pointWiseDFS(dsg, k);
                System.out.println(filename + " PointWiseDFS Time" + (System.currentTimeMillis()-startTime) + "ms" );
            }else if(kind == 6) {
                PWisePlus<TwoDim> pWisePlus = new PWisePlus<>();
                pWisePlus.pWisePlus(dsg, k);
                System.out.println(filename + " PointWisePles Time" + (System.currentTimeMillis()-startTime) + "ms" );
            }else if(kind == 7) {
                UWisePlusLayer1Pruning<TwoDim> uWisePlusLayer1Pruning = new UWisePlusLayer1Pruning<>(dsg, k);
                uWisePlusLayer1Pruning.run();
                System.out.println(filename + " UWisePlusLayer1Pruning Time:" + (System.currentTimeMillis()-startTime) + "ms");
            }else if(kind == 8) {
                UWisePlusLayer1PruningPlus<TwoDim> uWisePlusLayer1PruningPlus = new UWisePlusLayer1PruningPlus<>(dsg, k);
                uWisePlusLayer1PruningPlus.run();
                System.out.println(filename + " UWisePlusLayer1PruningPlus Time:" + (System.currentTimeMillis()-startTime) + "ms");
            }
        }else {
            ArrayList<MultiDim> multidim_points = readData.buildMultiPoints(filename);

            startTime = System.currentTimeMillis();
            BuildDSGMultiDim bDsg = new BuildDSGMultiDim();
            ArrayList<ArrayList<GraphPoints<MultiDim>>> skylineLayerTwoDim = bDsg.BuildSkylineLayerForMultiDim(multidim_points);
            ArrayList<ArrayList<GraphPoints<MultiDim>>> dsg = bDsg.BuildDsgForMultiDim(skylineLayerTwoDim,k);

            //运行pointWise算法
            if(kind == 0) {
                PointWise<MultiDim> pointWise = new PointWise<>();
                pointWise.pointWise(dsg, k);
                System.out.println(filename + " PointWise Time:" + (System.currentTimeMillis()-startTime) + "ms" );
            }else if(kind == 1) {
                UnitGroupWise.run(dsg, k);
                System.out.println(filename + " UnitWise Time:" + (System.currentTimeMillis()-startTime) + "ms" );
            }else if(kind == 2) {
                UnitGroupWisePlus.run(dsg, k);
                System.out.println(filename + " UnitWisePlus Time:" + (System.currentTimeMillis()-startTime) + "ms" );
            }else if(kind == 3) {
                UWisePlusDFS<MultiDim> uWisePlusDFS = new UWisePlusDFS<>(dsg, k);
                uWisePlusDFS.run();
                System.out.println(filename + " UWisePlusDFS Time:" + (System.currentTimeMillis()-startTime) + "ms");
            }else if(kind == 4) {
                UWisePlusPlus<MultiDim> uWisePlusPlus = new UWisePlusPlus<>(dsg, k);
                uWisePlusPlus.run();
                System.out.println(filename + " UWise++ Time:" + (System.currentTimeMillis()-startTime) + "ms");
            }else if(kind == 5) {
                PWiseDFS<MultiDim> pWiseDFS = new PWiseDFS<>();
                pWiseDFS.pointWiseDFS(dsg, k);
                System.out.println(filename + " PointWiseDFS Time" + (System.currentTimeMillis()-startTime) + "ms" );
            }else if(kind == 6) {
                PWisePlus<MultiDim> pWisePlus = new PWisePlus<>();
                pWisePlus.pWisePlus(dsg, k);
                System.out.println(filename + " PointWisePles Time" + (System.currentTimeMillis()-startTime) + "ms" );
            }else if(kind == 7) {
                UWisePlusLayer1Pruning<MultiDim> uWisePlusLayer1Pruning = new UWisePlusLayer1Pruning<>(dsg, k);
                uWisePlusLayer1Pruning.run();
                System.out.println(filename + " UWisePlusLayer1Pruning Time:" + (System.currentTimeMillis()-startTime) + "ms");
            }else if(kind == 8) {
                UWisePlusLayer1PruningPlus<MultiDim> uWisePlusLayer1PruningPlus = new UWisePlusLayer1PruningPlus<>(dsg, k);
                uWisePlusLayer1PruningPlus.run();
                System.out.println(filename + " UWisePlusLayer1PruningPlus Time:" + (System.currentTimeMillis()-startTime) + "ms");
            }
        }
    }
}
