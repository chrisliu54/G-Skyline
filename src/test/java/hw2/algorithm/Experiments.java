package hw2.algorithm;

import hw2.optimization.*;
import hw2.struct.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Experiments {

    //以数据集的维度数量为x轴，y轴为算法运算时间，输入参数dataPreName为数据名字前缀（"anti","corr","inde"）,k为group的数目
    //在data/output文件夹下生成输出的时间，第一列为x轴，第2-4列分别为PointWiseTime,UnitWiseTime,UnitWiseTimePlus
    public static void drawBydimensions(String dataPreName,int k) {
        int maxDim = 4;
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
                System.out.println(filepath + "GroupSize:" + k +  " PointWise Time:" + finallyTimePoint + "ms" );

                startTime = System.currentTimeMillis();
                UnitGroupWise.run(dsg, k);
                finallyTimeUnit = System.currentTimeMillis()-startTime+buildDsgTime;
                timeUnit[i] = finallyTimeUnit;
                System.out.println(filepath + "GroupSize:" + k +  " UnitWise Time:" + finallyTimeUnit + "ms" );

                startTime = System.currentTimeMillis();
                UnitGroupWisePlus.run(dsg, k);
                finallyTimeUnitPlus = System.currentTimeMillis()-startTime+buildDsgTime;
                timeUnitPlus[i] = finallyTimeUnitPlus;
                System.out.println(filepath + "GroupSize:" + k +  " UnitWisePlus Time:" + finallyTimeUnitPlus + "ms" );

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
                System.out.println(filepath + "GroupSize:" + k +  " PointWise Time:" + finallyTimePoint + "ms" );

                startTime = System.currentTimeMillis();
                UnitGroupWise.run(dsg_multi_dim, k);
                finallyTimeUnit = System.currentTimeMillis()-startTime+buildDsgTime;
                timeUnit[i] = finallyTimeUnit;
                System.out.println(filepath + "GroupSize:" + k +  " UnitWise Time:" + finallyTimeUnit + "ms" );

                startTime = System.currentTimeMillis();
                UnitGroupWisePlus.run(dsg_multi_dim, k);
                finallyTimeUnitPlus = System.currentTimeMillis()-startTime+buildDsgTime;
                timeUnitPlus[i] = finallyTimeUnitPlus;
                System.out.println(filepath + "GroupSize:" + k +  " UnitWisePlus Time:" + finallyTimeUnitPlus + "ms" );
            }
        }
        ArrayList<double[]> allTime = new ArrayList<>();
        allTime.add(timePoint);
        allTime.add(timeUnit);
        allTime.add(timeUnitPlus);
        String filepath = "output/DimTime_GroupSize" + k + "_"  + dataPreName + ".txt";
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
        filepath = "output/GroupNumTime_MaxGroupSize" + k + "_" + filename ;
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

    //遍历所有文本进行实验，得到实验输出,其中maxGroup为drawByGroup中最大组数，groups为drawBydimensions每个维度数据运行的group数目；
    public static void AllExperiments(String[] allGroupName,String[] allDimPreName,int maxGroup,int groups) {

        try {
            //按数据group数量进行实验，得到结果
            //String[] allGroupName = {"corr_2.txt","corr_4.txt","inde_2.txt","inde_4.txt","anti_2.txt","anti_4.txt"};
            for(String file:allGroupName) {
                drawByGroup(file, maxGroup);
            }

            //按数据维度进行实验，得到结果
            //String[] allDimPreName = { "anti","corr","inde"};
            for(String name:allDimPreName) {
                drawBydimensions(name, groups);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    //运行一个文件，输入参数String为文件名，k为groupSize,kind为运行的算法（0为pointWise，1为UnitWise，2为UnitWisePlus,3为uWisePlusDFS，4为uWisePlusPlus，5为pWiseDFS，6为pWisePlus）
    public static void RunSingleFile(String filename,int k,int kind) {
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
                System.out.println(filename + "UWisePlusDFS Time:" + (System.currentTimeMillis()-startTime) + "ms");
            }else if(kind == 4) {
                UWisePlusPlus<TwoDim> uWisePlusPlus = new UWisePlusPlus<>(dsg, k);
                uWisePlusPlus.run();
                System.out.println(filename + "UWise++ Time:" + (System.currentTimeMillis()-startTime) + "ms");
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
                System.out.println(filename + "UWisePlusLayer1Pruning Time:" + (System.currentTimeMillis()-startTime) + "ms");
            }else if(kind == 8) {
                UWisePlusLayer1PruningPlus<TwoDim> uWisePlusLayer1PruningPlus = new UWisePlusLayer1PruningPlus<>(dsg, k);
                uWisePlusLayer1PruningPlus.run();
                System.out.println(filename + "UWisePlusLayer1PruningPlus Time:" + (System.currentTimeMillis()-startTime) + "ms");
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
                System.out.println(filename + "UWisePlusDFS Time:" + (System.currentTimeMillis()-startTime) + "ms");
            }else if(kind == 4) {
                UWisePlusPlus<MultiDim> uWisePlusPlus = new UWisePlusPlus<>(dsg, k);
                uWisePlusPlus.run();
                System.out.println(filename + "UWise++ Time:" + (System.currentTimeMillis()-startTime) + "ms");
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

    public static void main(String[] args) {

        //drawByGroup("corr_2.txt", 4);
        //drawBydimensions("corr", 3);
        //在当前目录生成output并存储输出文本
// 		Scanner input = new Scanner(System.in);
// 		System.out.println("比较group数目对时间的影响，输入需要运行的最大group数目:");
// 		int maxGroup = input.nextInt();
// 		System.out.println("比较group数目对时间的影响，输入需要运行的数据文件名，以\",\"隔开");
// 		String groupName =  input.next();
// 		String[] groupNameArr = groupName.split(",");
// 		System.out.println("比较不同维度对时间的影响，输入需要运行不同维度数据文本的group数目:");
// 		int groupNum = input.nextInt();
// 		System.out.println("比较不同维度对时间的影响，输入需要运行的数据文件名前缀(inde，anti，corr)，以\",\"隔开");
// 		String preName =  input.next();
// 		String[] preNameArr = preName.split(",");
// 		AllExperiments(groupNameArr,preNameArr,maxGroup,groupNum);

// 		System.out.println("输入要跑的文件名：");
// 		String name = input.next();
// 		System.out.println("输入GroupSize：");
// 		int k = input.nextInt();
// 		System.out.println("输入算法（0位pointWise，1位UnitWise，2为UnitWisePlus）");
// 		int kind = input.nextInt();
// 		RunSingleFile(name,k,kind );

        System.out.println("3个参数，data_filename,group_size,algorithm_kind");
        System.out.println("kind为运行的算法（0为pointWise，1为UnitWise，2为UnitWisePlus,3为uWisePlusDFS，4为uWisePlusPlus，5为pWiseDFS，6为pWisePlus，7为UWisePlusLayer1Pruning，8为UWisePlusLayer1PruningPlus）");
        int k = Integer.parseInt(args[1]);
        int kind = Integer.parseInt(args[2]);
        RunSingleFile(args[0],k,kind );

        //String pathname  = "inde_4.txt";
        //int k = 4;
        //int kind = 2;
        //RunSingleFile(pathname,k,kind );

    }
}
