import java.io.*;
import java.util.*;

public class BodyVsBrain {
	
	public static ArrayList<double[]> getData(){
		String line;
		String[] bodyBrain;
		double[] bodyAndBrain;
		double body;
		double brain;
		ArrayList<double[]> data = new ArrayList<double[]>();
		File file = new File("data.csv");
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				bodyBrain = line.split(",");
				try {
					body = Double.valueOf(bodyBrain[0]);
					brain = Double.valueOf(bodyBrain[1]);
				} catch(NumberFormatException e) {
					continue;
				}
				bodyAndBrain = new double[2];
				bodyAndBrain[0] = body;
				bodyAndBrain[1] = brain;
				data.add(bodyAndBrain);
			}
		} catch(FileNotFoundException e) {
			System.out.println("File not found!");
		}
		return data;
	}
	
	public static double[] getMeanSTD(int n, ArrayList<double[]> data) {
		double bodySum = 0;
		double brainSum = 0;
		double bodyMean;
		double brainMean;
		double bodyMeanSquared = 0;
		double brainMeanSquared = 0;
		double bodySTD;
		double brainSTD;
		double meanSTD[] = new double[4];
		for(int i = 0; i < n; i++) {
			bodySum += data.get(i)[0];
			brainSum += data.get(i)[1];
		}
		bodyMean = bodySum / n;
		brainMean = brainSum / n;
		for(int i = 0; i < n; i++) {
			bodyMeanSquared += Math.pow(data.get(i)[0] - bodyMean, 2.0);
			brainMeanSquared += Math.pow(data.get(i)[1] - brainMean, 2.0);
		}
		bodySTD = Math.sqrt(bodyMeanSquared / (n - 1));
		brainSTD = Math.sqrt(brainMeanSquared / (n - 1));
		meanSTD[0] = bodyMean;
		meanSTD[1] = brainMean;
		meanSTD[2] = bodySTD;
		meanSTD[3] = brainSTD;
		return meanSTD;
	}
	
	public static double[] getGradient(double arg1, double arg2, int n, ArrayList<double[]> data) {
		double MSEd0Sum = 0;
		double MSEd1Sum = 0;
		double MSEd[] = new double[2];
		for(int i = 0; i < n; i++) {
			MSEd0Sum += arg1 + arg2 * data.get(i)[0] - data.get(i)[1];
			MSEd1Sum += (arg1 + arg2 * data.get(i)[0] - data.get(i)[1]) * data.get(i)[0];
		}
		MSEd[0] = MSEd0Sum / n * 2;
		MSEd[1] = MSEd1Sum / n * 2;
		return MSEd;
	}
	
	public static double[] getNewGradient(double arg1, double arg2, int n, ArrayList<double[]> data) {
		double MSEd0Sum = 0;
		double MSEd1Sum = 0;
		double MSEd[] = new double[2];
		Random rnd = new Random();
		int i = rnd.nextInt(n);
		MSEd0Sum = arg1 + arg2 * data.get(i)[0] - data.get(i)[1];
		MSEd1Sum = (arg1 + arg2 * data.get(i)[0] - data.get(i)[1]) * data.get(i)[0];
		return MSEd;
	}
	
	public static double getMSE(double arg1, double arg2, int n, ArrayList<double[]> data) {
		double MSESum = 0;
		double MSE;
		for(int i = 0; i < n; i++) {
			MSESum += Math.pow(arg1 + arg2 * data.get(i)[0] - data.get(i)[1], 2.0);
		}
		MSE = MSESum / n;
		return MSE;
	}
	
	public static ArrayList<double[]> getNormalizedData(int n, ArrayList<double[]> data){
		double[] meanSTD = getMeanSTD(n, data);
		ArrayList<double[]> normalizedData = new ArrayList<double[]>();
		double[] bodyBrain;
		for(int i = 0; i < n; i++) {
			bodyBrain = new double[2];
			bodyBrain[0] = (data.get(i)[0] - meanSTD[0]) / meanSTD[2];
			bodyBrain[1] = data.get(i)[1];
			normalizedData.add(bodyBrain);
		}
		return normalizedData;
	}
	
	public static void main(String[] args) {
		int flag = Integer.valueOf(args[0]);
		ArrayList<double[]> data = getData();
		int n = data.size();
		ArrayList<double[]> normalizedData = getNormalizedData(n, data);
		if(flag == 100) {
			System.out.println(n);
			double[] meanSTD = getMeanSTD(n, data);
			System.out.printf("%.4f %.4f\n", meanSTD[0], meanSTD[2]);
			System.out.printf("%.4f %.4f\n", meanSTD[1], meanSTD[3]);
		}
		if(flag == 200) {
			double arg1 = Double.valueOf(args[1]);
			double arg2 = Double.valueOf(args[2]);
			double MSE = getMSE(arg1, arg2, n, data);
			System.out.printf("%.4f\n", MSE);
		}
		if(flag == 300) {
			double arg1 = Double.valueOf(args[1]);
			double arg2 = Double.valueOf(args[2]);
			double[] MSEd = getGradient(arg1, arg2, n, data);
			System.out.printf("%.4f\n", MSEd[0]);
			System.out.printf("%.4f\n", MSEd[1]);
		}
		if(flag == 400) {
			double arg1 = Double.valueOf(args[1]);
			double arg2 = Double.valueOf(args[2]);
			double beta0 = 0;
			double beta1 = 0;
			double[] MSEd;
			double MSE;
			for(int i = 0; i < arg2; i++) {
				MSEd = getGradient(beta0, beta1, n, data);
				beta0 = beta0 - arg1 * MSEd[0];
				beta1 = beta1 - arg1 * MSEd[1];
				MSE = getMSE(beta0, beta1, n, data);
				System.out.printf("%d %.4f %.4f %.4f\n", i+1, beta0, beta1, MSE);
			}
		}
		if(flag == 500) {
			double[] meanSTD = getMeanSTD(n, data);
			double betaTop = 0;
			double betaBot = 0;
			double beta1;
			double beta0;
			for(int i = 0; i < n; i++) {
				betaTop += (data.get(i)[0] - meanSTD[0]) * (data.get(i)[1] - meanSTD[1]);
				betaBot += Math.pow(data.get(i)[0] - meanSTD[0], 2.0);
			}
			beta1 = betaTop / betaBot;
			beta0 = meanSTD[1] - beta1 * meanSTD[0];
			System.out.printf("%.4f %.4f %.4f\n", beta0, beta1, getMSE(beta0, beta1, n, data));
		}
		if(flag == 600) {
			double arg1 = Double.valueOf(args[1]);
			double[] meanSTD = getMeanSTD(n, data);
			double betaTop = 0;
			double betaBot = 0;
			double beta1;
			double beta0;
			for(int i = 0; i < n; i++) {
				betaTop += (data.get(i)[0] - meanSTD[0]) * (data.get(i)[1] - meanSTD[1]);
				betaBot += Math.pow(data.get(i)[0] - meanSTD[0], 2.0);
			}
			beta1 = betaTop / betaBot;
			beta0 = meanSTD[1] - beta1 * meanSTD[0];
			System.out.printf("%.4f\n", beta0 + beta1 * arg1);
		}
		if(flag == 700) {
			double arg1 = Double.valueOf(args[1]);
			double arg2 = Double.valueOf(args[2]);
			double beta0 = 0;
			double beta1 = 0;
			double[] MSEd;
			double MSE;
			for(int i = 0; i < arg2; i++) {
				MSEd = getGradient(beta0, beta1, n, normalizedData);
				beta0 = beta0 - arg1 * MSEd[0];
				beta1 = beta1 - arg1 * MSEd[1];
				MSE = getMSE(beta0, beta1, n, normalizedData);
				System.out.printf("%d %.4f %.4f %.4f\n", i+1, beta0, beta1, MSE);
			}
		}
		if(flag == 800) {
			double arg1 = Double.valueOf(args[1]);
			double arg2 = Double.valueOf(args[2]);
			double beta0 = 0;
			double beta1 = 0;
			double[] MSEd;
			double MSE;
			for(int i = 0; i < arg2; i++) {
				MSEd = getGradient(beta0, beta1, n, normalizedData);
				beta0 = beta0 - arg1 * MSEd[0];
				beta1 = beta1 - arg1 * MSEd[1];
				MSE = getMSE(beta0, beta1, n, normalizedData);
				System.out.printf("%d %.4f %.4f %.4f\n", i+1, beta0, beta1, MSE);
			}
		}
	}
}
