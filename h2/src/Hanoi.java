import java.util.*;

class Rod {
	List<Integer> rod;
	int entireNum;
	String disks;
	Rod(String disks) {
		this.disks = disks;
		entireNum = Integer.parseInt(disks);
		if(entireNum != 0) {
			rod = new ArrayList<Integer>();
			while(entireNum != 0) {
				rod.add(entireNum % 10);
				entireNum /= 10;
			}
		}
	}
	String moveLeft(Rod left) {
		String returnValue = "";
		if(this.rod == null) {
			return null;
		}
		if(left.rod == null) {
			returnValue = this.rod.get(this.rod.size() - 1).toString() + " ";
			for(int i = this.rod.size() - 2; i >= 0 ; i--) {
				returnValue += this.rod.get(i);
			}
			if(this.rod.size() == 1) {
				returnValue += "0";
			}
			return returnValue;
		}
		if(this.rod.get(this.rod.size() - 1).compareTo(left.rod.get(left.rod.size() - 1)) < 0) {
			returnValue = this.rod.get(this.rod.size() - 1).toString() + left.disks + " ";
			for(int i = this.rod.size() - 2; i >= 0 ; i--) {
				returnValue += this.rod.get(i);
			}
			if(this.rod.size() == 1) {
				returnValue += "0";
			}
			return returnValue;
		}
		else {
			return null;
		}
	}
	String moveRight(Rod right) {
		String returnValue = "";
		if(this.rod == null) {
			return null;
		}
		if(right.rod == null) {
			for(int i = this.rod.size() - 2; i >= 0 ; i--) {
				returnValue += this.rod.get(i);
			}
			if(this.rod.size() == 1) {
				returnValue = "0";
			}
			returnValue += " " + this.rod.get(this.rod.size() - 1).toString();
			return returnValue;
		}
		if(this.rod.get(this.rod.size() - 1).compareTo(right.rod.get(right.rod.size() - 1)) < 0) {
			for(int i = this.rod.size() - 2; i >= 0 ; i--) {
				returnValue += this.rod.get(i);
			}
			if(this.rod.size() == 1) {
				returnValue = "0";
			}
			returnValue += " " + this.rod.get(this.rod.size() - 1).toString() + right.disks;
			return returnValue;
		}
		else {
			return null;
		}
	}
}
public class Hanoi {
    
    public static List<String> getSuccessor(String[] hanoi) {
    	String defaultCase = hanoi[0];
    	for (int i = 1; i < hanoi.length; i++) {
            defaultCase += " " + hanoi[i];
        }
        List<String> result = new ArrayList<>();
        Rod[] rods = new Rod[hanoi.length];
        for (int i = 0; i < hanoi.length; i++) {
            rods[i] = new Rod(hanoi[i]);
        }
        if(rods[0].moveRight(rods[1]) != null) {
        	result.add(defaultCase.replace(hanoi[0] + " " + hanoi[1], rods[0].moveRight(rods[1])));
        }
        for (int i = 1; i < hanoi.length - 1; i++) {
        	if(rods[i].moveRight(rods[i + 1]) != null) {
            	result.add(defaultCase.replace(hanoi[i] + " " + hanoi[i+1], rods[i].moveRight(rods[i+1])));
            }
        	if(rods[i].moveLeft(rods[i-1]) != null) {
            	result.add(defaultCase.replace(hanoi[i-1] + " " + hanoi[i], rods[i].moveLeft(rods[i-1])));
            }
        }
        if(rods[hanoi.length - 1].moveLeft(rods[hanoi.length - 2]) != null) {
        	result.add(defaultCase.replace(hanoi[hanoi.length - 2] + " " 
        			+ hanoi[hanoi.length - 1], rods[hanoi.length - 1].moveLeft(rods[hanoi.length - 2])));
        }
        return result;
    }

    public static void main(String[] args) {
        if (args.length < 3) {
	        return;
	    }
        
        List<String> sucessors = getSuccessor(args);
        for (int i = 0; i < sucessors.size(); i++) {
            System.out.println(sucessors.get(i));
        }    
    }
 
}
