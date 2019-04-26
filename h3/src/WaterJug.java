import java.util.*;

class State {
    int cap_jug1;
    int cap_jug2;
    int curr_jug1;
    int curr_jug2;
    int goal;
    int depth;
    State parentPt;

    public State(int cap_jug1, int cap_jug2, int curr_jug1, int curr_jug2, int goal, int depth, State parentPt) {
        this.cap_jug1 = cap_jug1;
        this.cap_jug2 = cap_jug2;
        this.curr_jug1 = curr_jug1;
        this.curr_jug2 = curr_jug2;
        this.goal = goal;
        this.depth = depth;
        this.parentPt = parentPt;
    }

    public State[] getSuccessors() {
    	ArrayList<State> successors = new ArrayList<State>();
    	State[] successorsTemp = new State[1];
        if(this.curr_jug1 != 0) {
        	successors.add(new State(cap_jug1, cap_jug2, 0, curr_jug2, goal, depth + 1, this));
        	if(curr_jug1 + curr_jug2 <= cap_jug2) {
        		successors.add(new State(cap_jug1, cap_jug2, 0, curr_jug1 + curr_jug2, goal, depth + 1, this));
        	}
        	else if(cap_jug2 != curr_jug2) {
        		successors.add(new State(cap_jug1, cap_jug2, curr_jug1 + curr_jug2 - cap_jug2, cap_jug2, goal, depth + 1, this));
        	}
        }
        if(this.curr_jug2 != 0) {
        	successors.add(new State(cap_jug1, cap_jug2, curr_jug1, 0, goal, depth + 1, this));
        }
    	if(this.curr_jug2 != this.cap_jug2) {
    		successors.add(new State(cap_jug1, cap_jug2, curr_jug1, cap_jug2, goal, depth + 1, this));
    	}
    	if(this.curr_jug2 != 0) {
        	if(curr_jug1 + curr_jug2 <= cap_jug1) {
        		successors.add(new State(cap_jug1, cap_jug2, curr_jug1 + curr_jug2, 0, goal, depth + 1, this));
        	}
        	else if(cap_jug1 != curr_jug1) {
        		successors.add(new State(cap_jug1, cap_jug2, cap_jug1, curr_jug1 + curr_jug2 - cap_jug1, goal, depth + 1, this));
        	}
        }
    	if(this.curr_jug1 != this.cap_jug1) {
    		successors.add(new State(cap_jug1, cap_jug2, cap_jug1, curr_jug2, goal, depth + 1, this));
    	}
        return successors.toArray(successorsTemp);
    }

    public boolean isGoalState() {
        return curr_jug1 == goal || curr_jug2 == goal;
    }

    public void printState(int option, int depth) {
    	State[] successors = this.getSuccessors();
    	if(option == 1) {
    		for(int i = 0; i < successors.length; i++) {
    			System.out.println(successors[i].getOrderedPair());
    		}
    	} 
    	else if (option == 2) {
    		for(int i = 0; i < successors.length; i++) {
    			System.out.println(successors[i].getOrderedPair() + " " + successors[i].isGoalState());
    		}
    	}
    	else if (option == 3 | option == 4 | option == 5) {
    		UninformedSearch.run(this, option, depth);
    	}
    }

    public String getOrderedPair() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.curr_jug1);
        builder.append(this.curr_jug2);
        return builder.toString().trim();
    }

}

class MyStack {
	ArrayList<State> stack;
	MyStack() {
		stack = new ArrayList<State>();
	}
	
	boolean isEmpty() {
		return stack.isEmpty();
	}
	
	void add(State item) {
		stack.add(item);
	}
	
	State remove() {
		return stack.remove(stack.size() - 1);
	}
	
	void printStack() {
		if(this.isEmpty()) {
			System.out.print("[]");
			return;
		}
		System.out.print("[" + stack.get(0).getOrderedPair());
		for(int i = 1; i < stack.size(); i++) {
			System.out.print("," + stack.get(i).getOrderedPair());
		}
		System.out.print("]");
	}
	
	boolean compareToStack(State other) {
		boolean exist = false;
		for(int i = 0; i < stack.size(); i++) {
			if (stack.get(i).getOrderedPair().equals(other.getOrderedPair())) {
				exist = true;
				break;
			}
		}
		return exist;
	}
}

class MyQueue {
	ArrayList<State> queue;
	MyQueue() {
		queue = new ArrayList<State>();
	}
	
	boolean isEmpty() {
		return queue.isEmpty();
	}
	
	void add(State item) {
		queue.add(item);
	}
	
	State remove() {
		return queue.remove(0);
	}
	
	void printQueue() {
		if(this.isEmpty()) {
			System.out.print("[]");
			return;
		}
		System.out.print("[" + queue.get(0).getOrderedPair());
		for(int i = 1; i < queue.size(); i++) {
			System.out.print("," + queue.get(i).getOrderedPair());
		}
		System.out.print("]");
	}
	
	boolean compareToQueue(State other) {
		boolean exist = false;
		for(int i = 0; i < queue.size(); i++) {
			if (queue.get(i).getOrderedPair().equals(other.getOrderedPair())) {
				exist = true;
				break;
			}
		}
		return exist;
	}
}

class UninformedSearch {
    private static void bfs(State curr_state) {
    	State[] successors;
    	boolean success = false;
    	boolean repeat = false;
        MyQueue open = new MyQueue();
        ArrayList<State> closed = new ArrayList<State>();
        ArrayList<State> path = new ArrayList<State>();
        System.out.println(curr_state.getOrderedPair());
        open.add(curr_state);
        while(!open.isEmpty()) {
        	curr_state = open.remove();
        	closed.add(curr_state);
        	if(curr_state.isGoalState()) {
        		System.out.println(curr_state.getOrderedPair() + " Goal");
        		path.add(curr_state);
        		success = true;
        		break;
        	}
        	System.out.print(curr_state.getOrderedPair() + " ");
        	successors = curr_state.getSuccessors();
        	for(int i = 0; i < successors.length; i++) {
        		repeat = false;
        		for(int j = 0; j < closed.size(); j++) {
        			if(successors[i].getOrderedPair().equals(closed.get(j).getOrderedPair())) {
        				repeat = true;
        			}
        		}
        		if(open.compareToQueue(successors[i])) {
        			repeat = true;
        		}
        		if(!repeat) {
        			open.add(successors[i]);
        		}
        	}
        	open.printQueue();
        	System.out.print(" [" + closed.get(0).getOrderedPair());
        	for(int i = 1; i < closed.size(); i++) {
        		System.out.print("," + closed.get(i).getOrderedPair());
        	}
        	System.out.println("]");
        }
        if(success == true) {
        	System.out.print("Path");
        	while(path.get(path.size() - 1).parentPt != null) {
        		path.add(path.get(path.size() - 1).parentPt);
        	}
        	for(int i = path.size() - 1; i >= 0; i--) {
        		System.out.print(" " + path.get(i).getOrderedPair());
        	}
        	System.out.println();
        }
    }

    private static void dfs(State curr_state) {
    	State[] successors;
    	boolean success = false;
    	boolean repeat = false;
        MyStack open = new MyStack();
        ArrayList<State> closed = new ArrayList<State>();
        ArrayList<State> path = new ArrayList<State>();
        System.out.println(curr_state.getOrderedPair());
        open.add(curr_state);
        while(!open.isEmpty()) {
        	curr_state = open.remove();
        	closed.add(curr_state);
        	if(curr_state.isGoalState()) {
        		System.out.println(curr_state.getOrderedPair() + " Goal");
        		path.add(curr_state);
        		success = true;
        		break;
        	}
        	System.out.print(curr_state.getOrderedPair() + " ");
        	successors = curr_state.getSuccessors();
        	for(int i = 0; i < successors.length; i++) {
        		repeat = false;
        		for(int j = 0; j < closed.size(); j++) {
        			if(successors[i].getOrderedPair().equals(closed.get(j).getOrderedPair())) {
        				repeat = true;
        			}
        		}
        		if(open.compareToStack(successors[i])) {
        			repeat = true;
        		}
        		if(!repeat) {
        			open.add(successors[i]);
        		}
        	}
        	open.printStack();
        	System.out.print(" [" + closed.get(0).getOrderedPair());
        	for(int i = 1; i < closed.size(); i++) {
        		System.out.print("," + closed.get(i).getOrderedPair());
        	}
        	System.out.println("]");
        }
        if(success == true) {
        	System.out.print("Path");
        	while(path.get(path.size() - 1).parentPt != null) {
        		path.add(path.get(path.size() - 1).parentPt);
        	}
        	for(int i = path.size() - 1; i >= 0; i--) {
        		System.out.print(" " + path.get(i).getOrderedPair());
        	}
        	System.out.println();
        }
    }
    
    private static boolean dfsWithDepth(State curr_state, int depth) {
    	State[] successors;
    	boolean success = false;
    	boolean repeat = false;
        MyStack open = new MyStack();
        ArrayList<State> closed = new ArrayList<State>();
        ArrayList<State> path = new ArrayList<State>();
        System.out.println(depth + ":" + curr_state.getOrderedPair());
        open.add(curr_state);
        while(!open.isEmpty()) {
        	curr_state = open.remove();
        	closed.add(curr_state);
        	if(curr_state.isGoalState()) {
        		System.out.println(depth + ":" + curr_state.getOrderedPair() + " Goal");
        		path.add(curr_state);
        		success = true;
        		break;
        	}
        	System.out.print(depth + ":" + curr_state.getOrderedPair() + " ");
        	successors = curr_state.getSuccessors();
        	for(int i = 0; i < successors.length; i++) {
        		repeat = false;
        		for(int j = 0; j < closed.size(); j++) {
        			if(successors[i].getOrderedPair().equals(closed.get(j).getOrderedPair())) {
        				repeat = true;
        			}
        		}
        		if(open.compareToStack(successors[i])) {
        			repeat = true;
        		}
        		if(successors[i].depth > depth) {
        			repeat = true;
        		}
        		if(!repeat) {
        			open.add(successors[i]);
        		}
        	}
        	open.printStack();
        	System.out.print(" [" + closed.get(0).getOrderedPair());
        	for(int i = 1; i < closed.size(); i++) {
        		System.out.print("," + closed.get(i).getOrderedPair());
        	}
        	System.out.println("]");
        }
        if(success == true) {
        	System.out.print("Path");
        	while(path.get(path.size() - 1).parentPt != null) {
        		path.add(path.get(path.size() - 1).parentPt);
        	}
        	for(int i = path.size() - 1; i >= 0; i--) {
        		System.out.print(" " + path.get(i).getOrderedPair());
        	}
        	System.out.println();
        }
        return success;
    }

    private static void iddfs(State curr_state, int depth) {
        for(int i = 0; i <= depth; i++) {
        	if(dfsWithDepth(curr_state, i)) {
        		break;
        	}
        }
    }

    public static void run(State curr_state, int option, int depth) {
        if(option == 3) {
        	bfs(curr_state);
        }
        else if(option == 4) {
        	dfs(curr_state);
        }
        else if(option == 5) {
        	iddfs(curr_state, depth);
        }
    }
}

public class WaterJug {
    public static void main(String args[]) {
        if (args.length != 6) {
            System.out.println("Invalid Number of Input Arguments");
            return;
        }
        int flag = Integer.valueOf(args[0]);
        int cap_jug1 = Integer.valueOf(args[1]);
        int cap_jug2 = Integer.valueOf(args[2]);
        int curr_jug1 = Integer.valueOf(args[3]);
        int curr_jug2 = Integer.valueOf(args[4]);
        int goal = Integer.valueOf(args[5]);

        int option = flag / 100;
        int depth = flag % 100;

        if (option < 1 || option > 5) {
            System.out.println("Invalid flag input");
            return;
        }
        if (cap_jug1 > 9 || cap_jug2 > 9 || curr_jug1 > 9 || curr_jug2 > 9) {
            System.out.println("Invalid input: 2-digit jug volumes");
            return;
        }
        if (cap_jug1 < 0 || cap_jug2 < 0 || curr_jug1 < 0 || curr_jug2 < 0) {
            System.out.println("Invalid input: negative jug volumes");
            return;
        }
        if (cap_jug1 < curr_jug1 || cap_jug2 < curr_jug2) {
            System.out.println("Invalid input: jug volume exceeds its capacity");
            return;
        }
        State init = new State(cap_jug1, cap_jug2, curr_jug1, curr_jug2, goal, 0, null);
        init.printState(option, depth);
    }
}
