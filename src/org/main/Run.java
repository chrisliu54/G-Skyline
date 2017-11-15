package org.main;
import java.util.ArrayList;

import org.algorithm.PointWise;
import org.algorithm.UnitGroupWise;
import org.algorithm.UnitGroupWisePlus;
import org.optimization.UWisePlusDFS;
import org.optimization.UWisePlusPlus;
import org.struct.BuildDSGMultiDim;
import org.struct.BuildDSGTwoDim;
import org.struct.GraphPoints;
import org.struct.MultiDim;
import org.struct.TwoDim;

public class Run {
	
	public static void main(String[] args) {
		//读取数据文件
		ReadData readData = new ReadData();
		ArrayList<TwoDim> twodim_points = readData.buildTwoPoints("hotel_2.txt");
		
		System.out.println(twodim_points.size());
		long startTime = System.currentTimeMillis();
		BuildDSGTwoDim bDsg = new BuildDSGTwoDim();
		ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayerTwoDim = bDsg.BuildSkylineLayerForTwoDim(twodim_points);
		ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg = bDsg.BuildDsgForTwoDim(skylineLayerTwoDim,4);
		
		/**
		//bDsg.PrintDSG(dsg);
		long startTime1 = System.currentTimeMillis();
		PointWise<TwoDim> pointWise = new PointWise<TwoDim>();
		pointWise.pointWise(dsg, 4);
		long endTime1 = System.currentTimeMillis();
		**/
		
		System.out.println("----------------------");
		
		long startTime2 = System.currentTimeMillis();
		UnitGroupWise.run(dsg, 4);
		long endTime2 = System.currentTimeMillis();

		//--------------MultiDim-------------------------
		//读取数据文件
		ArrayList<MultiDim> multi_dim_points = readData.buildMultiPoints("hotel_4.txt");

		BuildDSGMultiDim dsgMultiDim = new BuildDSGMultiDim();
		ArrayList<ArrayList<GraphPoints<MultiDim>>> skylineLayerMultiDim = dsgMultiDim.BuildSkylineLayerForMultiDim(multi_dim_points);
		ArrayList<ArrayList<GraphPoints<MultiDim>>> dsg_multi_dim = dsgMultiDim.BuildDsgForMultiDim(skylineLayerMultiDim,4);
		//dsgMultiDim.PrintDSG(dsg_multi_dim);
		
		long startTime1 = System.currentTimeMillis();
		PointWise<MultiDim> pointWise = new PointWise<MultiDim>();
		pointWise.pointWise(dsg_multi_dim, 4);
		long endTime1 = System.currentTimeMillis();
		
		long startTime3 = System.currentTimeMillis();
		UnitGroupWisePlus.run(dsg, 4);
		long endTime3 = System.currentTimeMillis();

		long startTime4 = System.currentTimeMillis();
		UWisePlusDFS<TwoDim> uWisePlusDFS = new UWisePlusDFS<TwoDim>(dsg, 4);
        uWisePlusDFS.run();
        long endTime4 = System.currentTimeMillis();

		long startTime5 = System.currentTimeMillis();
		UWisePlusPlus<TwoDim> uWisePlusPlus = new UWisePlusPlus<>(dsg, 4);
        uWisePlusPlus.run();
		long endTime5 = System.currentTimeMillis();

		System.out.println("程序pointwise运行时间为：" + (endTime1-startTime1) + "ms" );
		System.out.println("程序unitgroupwise运行时间为：" + (endTime2-startTime2) + "ms" );
		System.out.println("程序unitgroupwise+运行时间为：" + (endTime3-startTime3) + "ms" );
		System.out.println("程序uwise+dfs运行时间为：" + (endTime4 - startTime4) + "ms");
        System.out.println("程序uwise++运行时间为：" + (endTime5 - startTime5) + "ms");

		
 	}
	
	
}
