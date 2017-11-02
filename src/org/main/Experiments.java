package org.main;

import java.util.ArrayList;

import org.algorithm.PointWise;
import org.algorithm.UnitGroupWise;
import org.struct.BuildDSGMultiDim;
import org.struct.BuildDSGTwoDim;
import org.struct.GraphPoints;
import org.struct.MultiDim;
import org.struct.TwoDim;


public class Experiments {
	
	//以数据集的维度数量为x轴，y轴为算法运算时间，输入参数dataPreName为数据名字前缀（"anti","corr","inde"）,k为group的数目
	public static void drawBydimensions(String dataPreName,int k) {
		double[] timePoint = new double[4];
		double[] timeUnit = new double[4];
		long startTime,buildDsgTime,startTimePoint,startTimeUnit,finallyTimePoint,finallyTimeUnit;
		for(int i=0;i<4;i++) {
			System.out.println("-----------------------------");
			String filepath = "data/" + dataPreName + "_" + String.valueOf(i*2+2) + ".txt";
			ReadData readData = new ReadData();
			startTime = System.currentTimeMillis();
			
			//--------------TwoDim-------------------------
			if(i==2) {
				ArrayList<TwoDim> twodim_points = readData.buildTwoPoints(filepath);
				
				BuildDSGTwoDim bDsg = new BuildDSGTwoDim();
				ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayerTwoDim = bDsg.BuildSkylineLayerForTwoDim(twodim_points);
				ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg = bDsg.BuildDsgForTwoDim(skylineLayerTwoDim,k);
				buildDsgTime = System.currentTimeMillis() - startTime;
				
				startTimePoint = System.currentTimeMillis();
				PointWise<TwoDim> pointWise = new PointWise<TwoDim>();
				pointWise.pointWise(dsg, 4);
				finallyTimePoint = System.currentTimeMillis()-startTimePoint+buildDsgTime;
				timePoint[i] = finallyTimePoint;
				System.out.println(filepath + " PointWise Time:" + finallyTimePoint + "ms" );
				
				startTimeUnit = System.currentTimeMillis();
				UnitGroupWise.run(dsg, 4);
				finallyTimeUnit = System.currentTimeMillis()-startTimeUnit+buildDsgTime;
				timeUnit[i] = finallyTimeUnit;
				System.out.println(filepath + " UnitWise Time:" + finallyTimePoint + "ms" );
				
			}
			//--------------MultiDim-------------------------
			else {
				ArrayList<MultiDim> multi_dim_points = readData.buildMultiPoints(filepath);
				
				BuildDSGMultiDim dsgMultiDim = new BuildDSGMultiDim();
				ArrayList<ArrayList<GraphPoints<MultiDim>>> skylineLayerMultiDim = dsgMultiDim.BuildSkylineLayerForMultiDim(multi_dim_points);
				ArrayList<ArrayList<GraphPoints<MultiDim>>> dsg_multi_dim = dsgMultiDim.BuildDsgForMultiDim(skylineLayerMultiDim,4);
				buildDsgTime = System.currentTimeMillis() - startTime;
				
				startTimePoint = System.currentTimeMillis();
				PointWise<MultiDim> pointWise = new PointWise<MultiDim>();
				pointWise.pointWise(dsg_multi_dim, 4);
				finallyTimePoint = System.currentTimeMillis()-startTimePoint+buildDsgTime;
				timePoint[i] = finallyTimePoint;
				System.out.println(filepath + " PointWise Time:" + finallyTimePoint + "ms" );
				
				startTimeUnit = System.currentTimeMillis();
				UnitGroupWise.run(dsg_multi_dim, 4);
				finallyTimeUnit = System.currentTimeMillis()-startTimeUnit+buildDsgTime;
				timeUnit[i] = finallyTimeUnit;
				System.out.println(filepath + " UnitWise Time:" + finallyTimePoint + "ms" );
			}
		}
		
	}
	
	//以group数量为x轴，y轴为算法运算时间,输入filepath为data文件名，参数k为最大group数目
	public static void drawByGroup(String filename,int k) {
		double[] timePoint = new double[k];
		double[] timeUnit = new double[k];
		long startTime,buildDsgTime,startTimePoint,startTimeUnit,finallyTimePoint,finallyTimeUnit;
		
		String filepath = "data/" + filename;
		ReadData readData = new ReadData();
		if(filename.contains("2")) {
			for(int i=1;i<=k;i++) {
				System.out.println("-----------------------------");
				startTime = System.currentTimeMillis();
				ArrayList<TwoDim> twodim_points = readData.buildTwoPoints(filepath);
				
				BuildDSGTwoDim bDsg = new BuildDSGTwoDim();
				ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayerTwoDim = bDsg.BuildSkylineLayerForTwoDim(twodim_points);
				ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg = bDsg.BuildDsgForTwoDim(skylineLayerTwoDim,i);
				buildDsgTime = System.currentTimeMillis() - startTime;
				
				startTimePoint = System.currentTimeMillis();
				PointWise<TwoDim> pointWise = new PointWise<TwoDim>();
				pointWise.pointWise(dsg, i);
				finallyTimePoint = System.currentTimeMillis()-startTimePoint+buildDsgTime;
				timePoint[i-1] = finallyTimePoint;
				System.out.println(filepath + " groupSize:" + i + " PointWise Time:" + finallyTimePoint + "ms" );
				
				startTimeUnit = System.currentTimeMillis();
				UnitGroupWise.run(dsg, i);
				finallyTimeUnit = System.currentTimeMillis()-startTimeUnit+buildDsgTime;
				timeUnit[i-1] = finallyTimeUnit;
				System.out.println(filepath + " groupSize:" + i + " UnitWise Time:" + finallyTimeUnit + "ms" );
				
			}
		}else {
			for(int i=1;i<=k;i++) {
				System.out.println("-----------------------------");
				startTime = System.currentTimeMillis();
				ArrayList<MultiDim> multi_dim_points = readData.buildMultiPoints(filepath);
				
				BuildDSGMultiDim dsgMultiDim = new BuildDSGMultiDim();
				ArrayList<ArrayList<GraphPoints<MultiDim>>> skylineLayerMultiDim = dsgMultiDim.BuildSkylineLayerForMultiDim(multi_dim_points);
				ArrayList<ArrayList<GraphPoints<MultiDim>>> dsg_multi_dim = dsgMultiDim.BuildDsgForMultiDim(skylineLayerMultiDim,i);
				buildDsgTime = System.currentTimeMillis() - startTime;
				
				startTimePoint = System.currentTimeMillis();
				PointWise<MultiDim> pointWise = new PointWise<MultiDim>();
				pointWise.pointWise(dsg_multi_dim, i);
				finallyTimePoint = System.currentTimeMillis()-startTimePoint+buildDsgTime;
				timePoint[i-1] = finallyTimePoint;
				System.out.println(filepath + " groupSize:" + i +  " PointWise Time:" + finallyTimePoint + "ms" );
				
				startTimeUnit = System.currentTimeMillis();
				UnitGroupWise.run(dsg_multi_dim, i);
				finallyTimeUnit = System.currentTimeMillis()-startTimeUnit+buildDsgTime;
				timeUnit[i-1] = finallyTimeUnit;
				System.out.println(filepath + " groupSize:" + i + " UnitWise Time:" + finallyTimeUnit + "ms" );
			}
		}
	}
	
	public static void drawPic(ArrayList<Double[]> allTime,double[] xaxis) {
		
	}
	
	public static void main(String[] args) {
		drawByGroup("corr_4.txt", 10);
	}
}
