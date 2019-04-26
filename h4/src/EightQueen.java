import java.util.*;
import java.lang.Math;

class State {
    char[] board;
    ArrayList<State> bestMoves;
    State firstBetter;

    public State(char[] arr) {
        this.board = Arrays.copyOf(arr, arr.length);
        this.bestMoves = new ArrayList<State>();
    }

    public void printState(int option, int iteration, int seed) {
    	int bestMove;
    	if(option == 1) {
    		System.out.println(getCost(this.board));
    	}
    	if(option == 2) {
    		bestMove = getBestMove(this);
    		if(bestMove < 0) {
    			return;
    		}
    		for(int i = 0; i < this.bestMoves.size(); i++) {
    			System.out.println(bestMoves.get(i).board);
    		}
    		System.out.println(bestMove);
    	}
    	if(option == 3) {
    		HillClimbing(iteration, seed);
    	}
    	if(option == 4) {
    		FirstChoice(iteration);
    	}
    	if(option == 5) {
    		Siann(iteration, seed);
    	}
    }
    
    private State Siann(int iteration, int seed) {
    	Random rng = new Random();
    	if (seed != -1) rng.setSeed(seed);
    	State current = this;
    	State next;
    	int deltaE;
    	int Temperature;
    	char[] boardCopy;
    	double p;
    	for(int i = 0; i <= iteration; i++) {
    		Temperature = current.getCost(current.board);
    		System.out.print(i + ":");
    		System.out.print(current.board);
    		System.out.println(" " + Temperature);
    		if(Temperature == 0) {
    			System.out.println("Solved");
    			return current;
    		}
    		int index = rng.nextInt(7);
    		int value = rng.nextInt(7);
    		double prob = rng.nextDouble();
    		boardCopy = current.board.clone();
    		boardCopy[index] = (char)(value + 48);
    		next = new State(boardCopy);
    		deltaE = next.getCost(next.board) - current.getCost(current.board);
    		if(deltaE > 0) {
    			current = next;
    		}
    		else {
    			p = Math.exp(deltaE/Temperature);
    			if(p > prob) {
    				current = next;
    			}
    		}
    	}
    	return current;
    }
    
    private void FirstChoice(int iteration) {
    	boolean solved = false;
    	boolean localMax = false;
    	int betterMove;
    	State move = this;
    	System.out.print("0:");
    	System.out.print(board);
    	System.out.println(" " + getCost(board));
    	if(getCost(board) == 0) {
    		System.out.println("Solved");
    		return;
    	}
    	for(int i = 1; i <= iteration; i++) {
    		betterMove = getFirstBetter(move);
    		if(betterMove == -2) {
    			localMax = true;
    			break;
    		}
    		System.out.print(i + ":");
    		move = move.firstBetter;
    		System.out.print(move.board);
        	System.out.println(" " + betterMove);
        	if(betterMove == 0) {
        		solved = true;
        		break;
        	}
    	}
    	if(localMax) {
    		System.out.println("Local optimum");
    		return;
    	}
    	if(solved) {
    		System.out.println("Solved");
    	}
    }
    
    private int getFirstBetter(State state) {
    	int betterMove = getCost(state.board);
    	char[] boardTemp;
    	char charTemp;
    	int currMove;
    	if(betterMove == 0) {
    		return -1;
    	}
    	for(int i = 0; i < board.length; i++) {
    		boardTemp = state.board.clone();
    		charTemp = boardTemp[i];
    		for(char a = '0'; a < '0' + board.length; a++) {
    			if(charTemp == a) {
    				continue;
    			}
    			boardTemp = state.board.clone();
    			boardTemp[i] = a;
    			currMove = getCost(boardTemp);
    			if(betterMove > currMove) {
    				betterMove = currMove;
    				state.firstBetter = new State(boardTemp);
    				return betterMove;
    			}
    		}
    	}
    	return -2;
    }
    
    private void HillClimbing(int iteration, int seed) {
    	boolean solved = false;
    	int bestMove;
    	State move = this;
    	Random rng = new Random();
    	if (seed != -1) rng.setSeed(seed);
    	System.out.print("0:");
    	System.out.print(board);
    	System.out.println(" " + getCost(board));
    	if(getCost(board) == 0) {
    		System.out.println("Solved");
    		return;
    	}
    	for(int i = 1; i <= iteration; i++) {
    		bestMove = getBestMove(move);
    		if(move.bestMoves.size() == 0) {
    			break;
    		}
    		System.out.print(i + ":");
    		move = move.bestMoves.get(rng.nextInt(move.bestMoves.size()));
    		System.out.print(move.board);
        	System.out.println(" " + bestMove);
        	if(bestMove == 0) {
        		solved = true;
        		break;
        	}
    	}
    	if(solved) {
    		System.out.println("Solved");
    	}
    }

    private int getCost(char [] board) {
    	int cost = 0;
    	for(int i = 0; i < board.length; i++) {
    		for(int j = i+1; j < board.length; j++) {
    			if(board[i] == board[j]) {
    				cost++;
    			}
    			if(board[i] == board[j] - j + i) {
    				cost++;
    			}
    			if(board[i] == board[j] + j - i) {
    				cost++;
    			}
    		}
    	}
    	return cost;
    }
    
    private int getBestMove(State state) {
    	int bestMove = getCost(state.board);
    	char[] boardTemp;
    	char charTemp;
    	int currMove;
    	state.bestMoves = new ArrayList<State>();
    	if(bestMove == 0) {
    		return -1;
    	}
    	for(int i = 0; i < board.length; i++) {
    		boardTemp = state.board.clone();
    		charTemp = boardTemp[i];
    		for(char a = '0'; a < '0' + board.length; a++) {
    			if(charTemp == a) {
    				continue;
    			}
    			boardTemp = state.board.clone();
    			boardTemp[i] = a;
    			currMove = getCost(boardTemp);
    			if(bestMove > currMove) {
    				bestMove = currMove;
    				state.bestMoves = new ArrayList<State>();
    				state.bestMoves.add(new State(boardTemp));
    			}
    			else if(bestMove == currMove) {
    				state.bestMoves.add(new State(boardTemp));
    			}
    		}
    	}
    	return bestMove;
    }
    
}

public class EightQueen {
    public static void main(String args[]) {
        if (args.length != 2 && args.length != 3) {
            System.out.println("Invalid Number of Input Arguments");
            return;
        }

        int flag = Integer.valueOf(args[0]);
        int option = flag / 100;
        int iteration = flag % 100;
        char[] board = new char[8];
        int seed = -1;
        int board_index = -1;

        if (args.length == 2 && (option == 1 || option == 2 || option == 4)) {
            board_index = 1;
        } else if (args.length == 3 && (option == 3 || option == 5)) {
            seed = Integer.valueOf(args[1]);
            board_index = 2;
        } else {
            System.out.println("Invalid Number of Input Arguments");
            return;
        }

        if (board_index == -1) return;
        for (int i = 0; i < 8; i++) {
            board[i] = args[board_index].charAt(i);
            int pos = board[i] - '0';
            if (pos < 0 || pos > 7) {
                System.out.println("Invalid input: queen position(s)");
                return;
            }
        }

        State init = new State(board);
        init.printState(option, iteration, seed);
    }
}
