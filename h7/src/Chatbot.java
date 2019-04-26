
import java.util.*;
import java.io.*;

public class Chatbot{
    private static String filename = "./corpus.txt";
    private static ArrayList<Integer> readCorpus(){
        ArrayList<Integer> corpus = new ArrayList<Integer>();
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                if(sc.hasNextInt()){
                    int i = sc.nextInt();
                    corpus.add(i);
                }
                else{
                    sc.next();
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found.");
        }
        return corpus;
    }
    static private double[] uni(double r, ArrayList<Integer> corpus, int V, int n) {
    	int count;
        double prob;
        double curr = 0;
        double prev = 0;
        int i = 0;
        for(; i < V; i++) {
        	count = Collections.frequency(corpus, i);
        	prob = (double)(count+1)/(V+n);
        	prev = curr;
        	curr += prob;
        	if(r <= curr) {
        		break;
        	}
        }
        double[] value = {i, prev, curr};
        return value;
    }
    static private double[] bi(double r, ArrayList<Integer> corpus, int V, int n, int h) {
    	double prob;
        double curr = 0;
        double prev = 0;
        int k = 0;
        int c = 0;
        int count;
        ArrayList<Integer> words_after_h = new ArrayList<Integer>();
        for(int i = 0; i < V; i++) {
        	count = 0;
        	for(int j = 0; j < n - 1; j++) {
        		if(corpus.get(j) == h && corpus.get(j+1) == i) {
        			c++;
        			count++;
        		}
        	}
        	words_after_h.add(count);
        }
        for(; k < V; k++) {
        	prob = (double)(words_after_h.get(k)+1)/(V+c);
        	prev = curr;
        	curr += prob;
        	if(r <= curr) {
        		break;
        	}
        }
        double[] value = {k, prev, curr};
        return value;
    }
    static private double[] tri(double r, ArrayList<Integer> corpus, int V, int n, int h1, int h2) {
    	double prob;
        double curr = 0;
        double prev = 0;
        int k = 0;
        int c = 0;
        int count;
        ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
        for(int i = 0; i < V; i++) {
        	count = 0;
        	for(int j = 0; j < n - 2; j++) {
        		if(corpus.get(j) == h1 && corpus.get(j+1) == h2 && corpus.get(j+2) == i) {
        			count++;
        			c++;
        		}
        	}
        	words_after_h1h2.add(count);
        }
        for(; k < V; k++) {
        	prob = (double)(words_after_h1h2.get(k)+1)/(V+c);
        	prev = curr;
        	curr += prob;
        	if(r <= curr) {
        		break;
        	}
        }
        double[] value = {k, prev, curr};
        return value;
    }
    static public void main(String[] args){
        ArrayList<Integer> corpus = readCorpus();
		int flag = Integer.valueOf(args[0]);
		final int V = 4700;
		final int n = 228548;
        
        if(flag == 100){
			int w = Integer.valueOf(args[1]);
            int count = Collections.frequency(corpus, w);
            double prob = (double)(count+1)/(V+n);
            System.out.println(count);
            System.out.printf("%.7f\n", prob);
        }
        else if(flag == 200){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            double r = (double)n1/n2;
            double[] value = uni(r, corpus, V, n);
            System.out.println((int)value[0]);
            System.out.printf("%.7f\n", value[1]);
            System.out.printf("%.7f\n", value[2]);
        }
        else if(flag == 300){
            int h = Integer.valueOf(args[1]);
            int w = Integer.valueOf(args[2]);
            int count = 0;
            int c = 0;
            ArrayList<Integer> words_after_h = new ArrayList<Integer>();
            for(int i = 0; i < V; i++) {
            	count = 0;
            	for(int j = 0; j < n - 1; j++) {
            		if(corpus.get(j) == h && corpus.get(j+1) == i) {
            			count++;
            			c++;
            		}
            	}
            	words_after_h.add(count);
            }
            System.out.println(words_after_h.get(w));
            System.out.println(c);
            System.out.printf("%.7f\n", (double)(words_after_h.get(w) + 1)/(c+V));
        }
        else if(flag == 400){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h = Integer.valueOf(args[3]);
            double r = (double)n1/n2;
            double[] value = bi(r, corpus, V, n, h);
            System.out.println((int)value[0]);
            System.out.printf("%.7f\n", value[1]);
            System.out.printf("%.7f\n", value[2]);
        }
        else if(flag == 500){
            int h1 = Integer.valueOf(args[1]);
            int h2 = Integer.valueOf(args[2]);
            int w = Integer.valueOf(args[3]);
            int count = 0;
            int c = 0;
            ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
            for(int i = 0; i < V; i++) {
            	count = 0;
            	for(int j = 0; j < n - 2; j++) {
            		if(corpus.get(j) == h1 && corpus.get(j+1) == h2 && corpus.get(j+2) == i) {
            			count++;
            			c++;
            		}
            	}
            	words_after_h1h2.add(count);
            }
            System.out.println(words_after_h1h2.get(w));
            System.out.println(c);
            System.out.printf("%.7f\n", (double)(words_after_h1h2.get(w) + 1)/(c+V));
        }
        else if(flag == 600){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h1 = Integer.valueOf(args[3]);
            int h2 = Integer.valueOf(args[4]);
            double r = (double)n1/n2;
            double[] value = tri(r, corpus, V, n, h1, h2);
            System.out.println((int)value[0]);
            System.out.printf("%.7f\n", value[1]);
            System.out.printf("%.7f\n", value[2]);
        }
        else if(flag == 700){
            int seed = Integer.valueOf(args[1]);
            int t = Integer.valueOf(args[2]);
            int h1=0,h2=0;

            Random rng = new Random();
            if (seed != -1) rng.setSeed(seed);

            if(t == 0){
                double r = rng.nextDouble();
                h1 = (int)uni(r, corpus, V, n)[0];
                System.out.println(h1);
                if(h1 == 9 || h1 == 10 || h1 == 12){
                    return;
                }

                r = rng.nextDouble();
                h2 = (int)bi(r, corpus, V, n, h1)[0];
                System.out.println(h2);
            }
            else if(t == 1){
                h1 = Integer.valueOf(args[3]);
                double r = rng.nextDouble();
                h2 = (int)bi(r, corpus, V, n, h1)[0];
                System.out.println(h2);
            }
            else if(t == 2){
                h1 = Integer.valueOf(args[3]);
                h2 = Integer.valueOf(args[4]);
            }

            while(h2 != 9 && h2 != 10 && h2 != 12){
                double r = rng.nextDouble();
                int w  = (int)tri(r, corpus, V, n, h1, h2)[0];;
                System.out.println(w);
                h1 = h2;
                h2 = w;
            }
        }

        return;
    }
}
