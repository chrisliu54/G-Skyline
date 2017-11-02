package org.main;
import java.util.ArrayList;

import org.algorithm.PointWise;
import org.algorithm.UnitGroupWise;
import org.algorithm.UnitGroupWisePlus;
import org.struct.BuildDSG;
import org.struct.GraphPoints;
import org.struct.TwoDim;

public class Run {
	
	public static void main(String[] args) {
		//读取数据文件
		ReadData readData = new ReadData();
		ArrayList<TwoDim> twodim_points = readData.buildTwoPoints("data/hotel.txt");
		
		long startTime = System.currentTimeMillis();
		BuildDSG bDsg = new BuildDSG();
		ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayerTwoDim = bDsg.BuildSkylineLayerForTwoDim(twodim_points);
		ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg = bDsg.BuildDsgForTwoDim(skylineLayerTwoDim,4);
		
		//bDsg.PrintDSG(dsg);
		long startTime1 = System.currentTimeMillis();
		PointWise<TwoDim> pointWise = new PointWise<TwoDim>();
		pointWise.pointWise(dsg, 4);
		long endTime1 = System.currentTimeMillis();
		
		System.out.println("----------------------");
		
		long startTime2 = System.currentTimeMillis();
		UnitGroupWise.run(dsg, 4);
		long endTime2 = System.currentTimeMillis();

		long startTime3 = System.currentTimeMillis();
		UnitGroupWisePlus.run(dsg, 4);
		long endTime3 = System.currentTimeMillis();
		
		System.out.println("程序pointwise运行时间为：" + (endTime1-startTime1) + "ms" );
		System.out.println("程序unitgroupwise运行时间为：" + (endTime2-startTime2) + "ms" );
		System.out.println("程序unitgroupwise+运行时间为：" + (endTime3-startTime3) + "ms" );
		
 	}
	
	
}
