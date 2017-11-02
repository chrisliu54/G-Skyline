package org.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.algorithm.PointWise;
import org.algorithm.UnitGroupWise;
import org.algorithm.UnitGroupWisePlus;
import org.struct.BuildDSGMultiDim;
import org.struct.BuildDSGTwoDim;
import org.struct.GraphPoints;
import org.struct.MultiDim;
import org.struct.TwoDim;


public class Experiments {
	
	//以数据集的维度数量为x轴，y轴为算法运算时间，输入参数dataPreName为数据名字前缀（"anti","corr","inde"）,k为group的数目
	//在data/output文件夹下生成输出的时间，第一列为x轴，第2-4列分别为PointWiseTime,UnitWiseTime,UnitWiseTimePlus
	public static void drawBydimensions(String dataPreName,int k) {
		int maxDim = 2;
		double[] timePoint = new double[maxDim];
		double[] timeUnit = new double[maxDim];
		double[] timeUnitPlus = new double[maxDim];
		int[] xaxis = new int[maxDim];
		long startTime,buildDsgTime,finallyTimePoint,finallyTimeUnit,finallyTimeUnitPlus;
		for(int i=0;i<maxDim;i++) {
			System.out.println("-----------------------------");
			String filepath =  dataPreName + "_" + String.valueOf(i*2+2) + ".txt";
			xaxis[i] = i*2+2;
			ReadData readData = new ReadData();
			startTime = System.currentTimeMillis();
			
			//--------------TwoDim-------------------------
			if(i==2) {
				ArrayList<TwoDim> twodim_points = readData.buildTwoPoints(filepath);
				
				BuildDSGTwoDim bDsg = new BuildDSGTwoDim();
				ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayerTwoDim = bDsg.BuildSkylineLayerForTwoDim(twodim_points);
				ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg = bDsg.BuildDsgForTwoDim(skylineLayerTwoDim,k);
				buildDsgTime = System.currentTimeMillis() - startTime;
				
				startTime = System.currentTimeMillis();
				PointWise<TwoDim> pointWise = new PointWise<TwoDim>();
				pointWise.pointWise(dsg, k);
				finallyTimePoint = System.currentTimeMillis()-startTime+buildDsgTime;
				timePoint[i] = finallyTimePoint;
				System.out.println(filepath + " PointWise Time:" + finallyTimePoint + "ms" );
				
				startTime = System.currentTimeMillis();
				UnitGroupWise.run(dsg, k);
				finallyTimeUnit = System.currentTimeMillis()-startTime+buildDsgTime;
				timeUnit[i] = finallyTimeUnit;
				System.out.println(filepath + " UnitWise Time:" + finallyTimeUnit + "ms" );
				
				startTime = System.currentTimeMillis();
				UnitGroupWisePlus.run(dsg, k);
				finallyTimeUnitPlus = System.currentTimeMillis()-startTime+buildDsgTime;
				timeUnitPlus[i] = finallyTimeUnitPlus;
				System.out.println(filepath + " UnitWisePlus Time:" + finallyTimeUnitPlus + "ms" );
				
			}
			//--------------MultiDim-------------------------
			else {
				ArrayList<MultiDim> multi_dim_points = readData.buildMultiPoints(filepath);
				
				BuildDSGMultiDim dsgMultiDim = new BuildDSGMultiDim();
				ArrayList<ArrayList<GraphPoints<MultiDim>>> skylineLayerMultiDim = dsgMultiDim.BuildSkylineLayerForMultiDim(multi_dim_points);
				ArrayList<ArrayList<GraphPoints<MultiDim>>> dsg_multi_dim = dsgMultiDim.BuildDsgForMultiDim(skylineLayerMultiDim,k);
				buildDsgTime = System.currentTimeMillis() - startTime;
				
				startTime = System.currentTimeMillis();
				PointWise<MultiDim> pointWise = new PointWise<MultiDim>();
				pointWise.pointWise(dsg_multi_dim, k);
				finallyTimePoint = System.currentTimeMillis()-startTime+buildDsgTime;
				timePoint[i] = finallyTimePoint;
				System.out.println(filepath + " PointWise Time:" + finallyTimePoint + "ms" );
				
				startTime = System.currentTimeMillis();
				UnitGroupWise.run(dsg_multi_dim, k);
				finallyTimeUnit = System.currentTimeMillis()-startTime+buildDsgTime;
				timeUnit[i] = finallyTimeUnit;
				System.out.println(filepath + " UnitWise Time:" + finallyTimeUnit + "ms" );
				
				startTime = System.currentTimeMillis();
				UnitGroupWisePlus.run(dsg_multi_dim, k);
				finallyTimeUnitPlus = System.currentTimeMillis()-startTime+buildDsgTime;
				timeUnitPlus[i] = finallyTimeUnitPlus;
				System.out.println(filepath + " UnitWisePlus Time:" + finallyTimeUnitPlus + "ms" );
			}
		}
		ArrayList<double[]> allTime = new ArrayList<>();
		allTime.add(timePoint);
		allTime.add(timeUnit);
		allTime.add(timeUnitPlus);
		String filepath = "output/DimTime_" + dataPreName + ".txt";
		getResult(allTime, xaxis, filepath);
	}
	
	//以group数量为x轴，y轴为算法运算时间,输入filepath为data文件名，参数k为最大group数目
	//在data/output文件夹下生成输出的时间，第一列为x轴，第2-4列分别为PointWiseTime,UnitWiseTime,UnitWiseTimePlus
	public static void drawByGroup(String filename,int k) {
		double[] timePoint = new double[k];
		double[] timeUnit = new double[k];
		double[] timeUnitPlus = new double[k];
		int[] xaxis = new int[k];
		long startTime,buildDsgTime,finallyTimePoint,finallyTimeUnit,finallyTimeUnitPlus;
		
		String filepath = filename;
		ReadData readData = new ReadData();
		if(filename.contains("2")) {
			for(int i=1;i<=k;i++) {
				xaxis[i-1] = i;
				System.out.println("-----------------------------");
				startTime = System.currentTimeMillis();
				ArrayList<TwoDim> twodim_points = readData.buildTwoPoints(filepath);
				
				BuildDSGTwoDim bDsg = new BuildDSGTwoDim();
				ArrayList<ArrayList<GraphPoints<TwoDim>>> skylineLayerTwoDim = bDsg.BuildSkylineLayerForTwoDim(twodim_points);
				ArrayList<ArrayList<GraphPoints<TwoDim>>> dsg = bDsg.BuildDsgForTwoDim(skylineLayerTwoDim,i);
				buildDsgTime = System.currentTimeMillis() - startTime;
				
				startTime = System.currentTimeMillis();
				PointWise<TwoDim> pointWise = new PointWise<TwoDim>();
				pointWise.pointWise(dsg, i);
				finallyTimePoint = System.currentTimeMillis()-startTime+buildDsgTime;
				timePoint[i-1] = finallyTimePoint;
				System.out.println(filepath + " groupSize:" + i + " PointWise Time:" + finallyTimePoint + "ms" );
				
				startTime = System.currentTimeMillis();
				UnitGroupWise.run(dsg, i);
				finallyTimeUnit = System.currentTimeMillis()-startTime+buildDsgTime;
				timeUnit[i-1] = finallyTimeUnit;
				System.out.println(filepath + " groupSize:" + i + " UnitWise Time:" + finallyTimeUnit + "ms" );
				
				startTime = System.currentTimeMillis();
				UnitGroupWisePlus.run(dsg, i);
				finallyTimeUnitPlus = System.currentTimeMillis()-startTime+buildDsgTime;
				timeUnitPlus[i-1] = finallyTimeUnitPlus;
				System.out.println(filepath + " groupSize:" + i + " UnitWisePlus Time:" + finallyTimeUnitPlus + "ms" );
				
				
			}
		}else {
			for(int i=1;i<=k;i++) {
				xaxis[i-1] = i;
				System.out.println("-----------------------------");
				startTime = System.currentTimeMillis();
				ArrayList<MultiDim> multi_dim_points = readData.buildMultiPoints(filepath);
				
				BuildDSGMultiDim dsgMultiDim = new BuildDSGMultiDim();
				ArrayList<ArrayList<GraphPoints<MultiDim>>> skylineLayerMultiDim = dsgMultiDim.BuildSkylineLayerForMultiDim(multi_dim_points);
				ArrayList<ArrayList<GraphPoints<MultiDim>>> dsg_multi_dim = dsgMultiDim.BuildDsgForMultiDim(skylineLayerMultiDim,i);
				buildDsgTime = System.currentTimeMillis() - startTime;
				
				startTime = System.currentTimeMillis();
				PointWise<MultiDim> pointWise = new PointWise<MultiDim>();
				pointWise.pointWise(dsg_multi_dim, i);
				finallyTimePoint = System.currentTimeMillis()-startTime+buildDsgTime;
				timePoint[i-1] = finallyTimePoint;
				System.out.println(filepath + " groupSize:" + i +  " PointWise Time:" + finallyTimePoint + "ms" );
				
				startTime = System.currentTimeMillis();
				UnitGroupWise.run(dsg_multi_dim, i);
				finallyTimeUnit = System.currentTimeMillis()-startTime+buildDsgTime;
				timeUnit[i-1] = finallyTimeUnit;
				System.out.println(filepath + " groupSize:" + i + " UnitWise Time:" + finallyTimeUnit + "ms" );
				
				startTime = System.currentTimeMillis();
				UnitGroupWisePlus.run(dsg_multi_dim, i);
				finallyTimeUnitPlus = System.currentTimeMillis()-startTime+buildDsgTime;
				timeUnitPlus[i-1] = finallyTimeUnitPlus;
				System.out.println(filepath + " groupSize:" + i + " UnitWisePlus Time:" + finallyTimeUnitPlus + "ms" );
			}
		}
		
		ArrayList<double[]> allTime = new ArrayList<>();
		allTime.add(timePoint);
		allTime.add(timeUnit);
		allTime.add(timeUnitPlus);
		filepath = "output/GroupNumTime_" + filename ;
		getResult(allTime, xaxis, filepath);
	}
	
	//获取实验的输出文本
	public static void getResult(ArrayList<double[]> allTime,int[] xaxis,String filepath) {
		
		try {
			//判断目录下是否有output文件夹,若不存在则创建目录
			File file = new File("output");
			if(!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			
			file = new File(filepath);
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			int len = xaxis.length;
			String output = "";
			for(int i=0;i<len;i++) {
				output += String.valueOf(xaxis[i]) + "	" + String.valueOf(allTime.get(0)[i]) + "	" + String.valueOf(allTime.get(1)[i]) + "	" + String.valueOf(allTime.get(2)[i]) + "\n";	
			}
			writer.write(output);
			writer.flush();
			writer.close();	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//遍历所有文本进行实验，得到实验输出
	public static void AllExperiments() {
		
		//按数据group数量进行实验，得到结果
		String[] allGroupName = {"corr_2.txt","corr_6.txt","inde_2.txt","inde_6.txt","anti_2.txt","anti_6.txt"};
		for(String file:allGroupName) {
			drawByGroup(file, 10);
		}
		
		//按数据维度进行实验，得到结果
		String[] allDimPreName = { "anti","corr","inde"};
		for(String name:allDimPreName) {
			drawBydimensions(name, 4);
		}
		
		
	}
	
	public static void main(String[] args) {
		
		//drawByGroup("hotel_2.txt", 4);
		AllExperiments();
		
	}
}
