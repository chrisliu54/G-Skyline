package org.main;
import java.util.ArrayList;

import org.algorithm.BaseLine;
import org.struct.BuildDSG;
import org.struct.GraphPoints;
import org.struct.TwoDim;

public class Run {
	
	public static void main(String[] args) {
		//读取数据文件
		ReadData readData = new ReadData();
		ArrayList<TwoDim> twodim_points = readData.buildTwoPoints("data/hotel_2.txt");
		
		long startTime = System.currentTimeMillis();
		BuildDSG bDsg = new BuildDSG();
		ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayerTwoDim = bDsg.BuildSkylineLayerForTwoDim(twodim_points);
		ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg = bDsg.BuildDsgForTwoDim(skylineLayerTwoDim,4);
		
		/**
		//bDsg.PrintDSG(dsg);
		long startTime1 = System.currentTimeMillis();
		PointWise<TwoDim> pointWise = new PointWise<TwoDim>();
		pointWise.pointWise(dsg, 4);
		long endTime1 = System.currentTimeMillis();
		
		System.out.println("----------------------");
		
		long startTime2 = System.currentTimeMillis();
		UnitGroupWise.run(dsg, 4);
		long endTime2 = System.currentTimeMillis();
		
		//long endTime = System.currentTimeMillis();
		System.out.println("程序pointwise运行时间为：" + (endTime1-startTime1) + "ms" );
		System.out.println("程序unitwise运行时间为：" + (endTime2-startTime2) + "ms" );

		System.out.println("----------分隔符----------");

		//--------------MultiDim-------------------------
		//读取数据文件
		ArrayList<MultiDim> multi_dim_points = readData.buildMultiPoints("data/hotel_4.txt");

		BuildDSGMultiDim dsgMultiDim = new BuildDSGMultiDim();
		ArrayList<ArrayList<GraphPoints<MultiDim>>> skylineLayerMultiDim = dsgMultiDim.BuildSkylineLayerForMultiDim(multi_dim_points);
		ArrayList<ArrayList<GraphPoints<MultiDim>>> dsg_multi_dim = dsgMultiDim.BuildDsgForMultiDim(skylineLayerMultiDim);
		dsgMultiDim.PrintDSG(dsg_multi_dim);
		
		**/
		
		BaseLine<TwoDim> bLine = new BaseLine<>();
		ArrayList<TwoDim> now = new ArrayList<>();
		ArrayList<ArrayList<TwoDim>> ans = new ArrayList<>();
		bLine.BFSbuildCnkGroup(2, 0, twodim_points, now, ans);
		
		for(ArrayList<TwoDim> list : ans) {
			System.out.print("-------");
			for(TwoDim twoDim:list) {
				System.out.print(twoDim.toString());
			}
			System.out.println("-------");
		}
 	}
	
	
}
