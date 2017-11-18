package hw2.optimization;

import hw2.algorithm.PointWise;
import hw2.algorithm.ReadData;
import hw2.algorithm.UnitGroupWisePlus;
import hw2.struct.BuildDSGMultiDim;
import hw2.struct.GraphPoints;
import hw2.struct.MultiDim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PWiseDFSTest {

    @Test
    public void testPerformance() throws InterruptedException {

        ReadData readData = new ReadData();
        ArrayList<MultiDim> multi_dim_points = readData.buildMultiPoints("inde_2.txt");
        //ArrayList<TwoDim> twodim_points = readData.buildTwoPoints("anti_2.txt");

        int k = 3;

        BuildDSGMultiDim dsgMultiDim = new BuildDSGMultiDim();
        ArrayList<ArrayList<GraphPoints<MultiDim>>> skylineLayerMultiDim = dsgMultiDim.BuildSkylineLayerForMultiDim(multi_dim_points);
        ArrayList<ArrayList<GraphPoints<MultiDim>>> dsg_multi_dim = dsgMultiDim.BuildDsgForMultiDim(skylineLayerMultiDim,k);
        //dsgMultiDim.PrintDSG(dsg_multi_dim);

        long startTime1 = System.currentTimeMillis();
        PointWise<MultiDim> pointWise = new PointWise<MultiDim>();
        pointWise.pointWise(dsg_multi_dim, k);
        long endTime1 = System.currentTimeMillis();

        long startTime2 = System.currentTimeMillis();
        PWisePlus<MultiDim> pWisePlus = new PWisePlus<MultiDim>();
        pWisePlus.pWisePlus(dsg_multi_dim, k);
        long endTime2 = System.currentTimeMillis();

        long startTime3 = System.currentTimeMillis();
        PWiseDFS<MultiDim> pWiseDFS = new PWiseDFS<MultiDim>();
        pWiseDFS.pointWiseDFS(dsg_multi_dim, k);
        long endTime3 = System.currentTimeMillis();

        long startTime4 = System.currentTimeMillis();
        UnitGroupWisePlus.run(dsg_multi_dim, k);
        long endTime4 = System.currentTimeMillis();


//		BuildDSGTwoDim bDsg = new BuildDSGTwoDim();
//		ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayerTwoDim = bDsg.BuildSkylineLayerForTwoDim(twodim_points);
//		ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg = bDsg.BuildDsgForTwoDim(skylineLayerTwoDim,k);
//		//dsgMultiDim.PrintDSG(dsg_multi_dim);
//
//
//		long startTime1 = System.currentTimeMillis();
//		PointWise<TwoDim> pointWise = new PointWise<TwoDim>();
//		pointWise.pointWise(dsg, k);
//		long endTime1 = System.currentTimeMillis();
//
//
//		long startTime2 = System.currentTimeMillis();
//		PWisePlus<TwoDim> pWisePlus = new PWisePlus<TwoDim>();
//		pWisePlus.pWisePlus(dsg, k);
//		long endTime2 = System.currentTimeMillis();
//
//
//		long startTime3 = System.currentTimeMillis();
//		PWiseDFS<TwoDim> pWiseDFS = new PWiseDFS<TwoDim>();
//		pWiseDFS.pointWiseDFS(dsg, k);
//		long endTime3 = System.currentTimeMillis();
//
//
//		long startTime4 = System.currentTimeMillis();
//		UnitGroupWisePlus.run(dsg, k);
//		long endTime4 = System.currentTimeMillis();

        System.out.println("程序pointwise运行时间为：" + (endTime1-startTime1) + "ms" );
        System.out.println("程序pointwisePlus运行时间为：" + (endTime2-startTime2) + "ms" );
        System.out.println("程序pointwiseDFS运行时间为：" + (endTime3-startTime3) + "ms" );
        System.out.println("程序UnitGroupWisePlus运行时间为：" + (endTime4-startTime4) + "ms" );

    }
}