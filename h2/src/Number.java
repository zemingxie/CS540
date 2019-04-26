import java.util.*;

class MyQueue<T> {
	ArrayList<T> queue;
	MyQueue() {
		queue = new ArrayList<T>();
	}
	
	boolean isEmpty() {
		return queue.isEmpty();
	}
	
	void add(T item) {
		queue.add(item);
	}
	
	T remove() {
		return queue.remove(0);
	}
	
	T peek() {
		return queue.get(0);
	}
}

class NumberWithDepth{
	int number;
	int depth;
	NumberWithDepth(int number, int depth) {
		this.number = number;
		this.depth = depth;
	}
	
	int getNumber() {
		return this.number;
	}
	
	int getDepth() {
		return this.depth;
	}
}

public class Number{
           
    public static String getStep(int x, int y) {
        MyQueue<NumberWithDepth> myQueue = new MyQueue<NumberWithDepth>();
        String result = "";
        NumberWithDepth currNum;
        myQueue.add(new NumberWithDepth(x, 0));
        while(!myQueue.isEmpty()) {
        	currNum = myQueue.remove();
        	if(currNum.getNumber() == y) {
        		return "" + currNum.getDepth();
        	}
        	addSuccs(myQueue, currNum);
        }
        return result;
    }
    
    public static void addSuccs(MyQueue<NumberWithDepth> myQueue, NumberWithDepth currNum) {
    	if(currNum.getNumber() < 99) {
    		myQueue.add(new NumberWithDepth(currNum.getNumber() + 1, currNum.getDepth() + 1));
    	}
    	if(currNum.getNumber() > 0) {
    		myQueue.add(new NumberWithDepth(currNum.getNumber() - 1, currNum.getDepth() + 1));
    	}
    	if(currNum.getNumber() * 3 < 100) {
    		myQueue.add(new NumberWithDepth(currNum.getNumber() * 3, currNum.getDepth() + 1));
    	}
    	if(currNum.getNumber() * currNum.getNumber() < 100) {
    		myQueue.add(new NumberWithDepth(currNum.getNumber() * currNum.getNumber(), 
    				currNum.getDepth() + 1));
    	}
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }
        
        System.out.println(getStep(Integer.parseInt(args[0]), Integer.parseInt(args[1])));

    }
}
