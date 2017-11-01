package org.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.struct.MultiDim;
import org.struct.TwoDim;

public class ReadData {
	
	//读取二维数据文件中的数据
	public ArrayList<TwoDim> buildTwoPoints(String filepath){
		ArrayList<TwoDim> twoDim_points = new ArrayList<>();
		
		try {
			File file = new File(filepath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"utf-8");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = null;
			String numbers[];
			double x,y;
			int label = 0;
			
			//读取数据中的每一行
			while( (line = bufferedReader.readLine()) != null  )
			{
				numbers = line.split(" ");
				x = Double.parseDouble(numbers[0]);
				y = Double.parseDouble(numbers[1]);
				++label;
				TwoDim twoDim = new TwoDim(x,y,label);
				twoDim_points.add(twoDim);
			}
			System.out.println("总的节点数为：" + twoDim_points.size());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return twoDim_points;
	}
	
	public ArrayList<MultiDim> buildMulPoints(String filepath){
		ArrayList<MultiDim> multiDim_points = new ArrayList<>();
		
		try {
			
			File file = new File(filepath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"utf-8");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = null;
			String numbers[];
			int label = 0;
			
			while( (line = bufferedReader.readLine()) != null ) {
				numbers = line.split(" ");
				double[] multiDimValue = new double[numbers.length];
				for(int i=0;i<numbers.length;i++) {
					multiDimValue[i] = Double.parseDouble(numbers[i]);
				}
				label++;
				MultiDim multiDim = new MultiDim(multiDimValue, label);
				multiDim_points.add(multiDim);
			}
			System.out.println("总的节点数为：" + multiDim_points.size());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return multiDim_points;
	}
}
