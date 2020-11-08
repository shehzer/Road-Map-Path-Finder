
public class Node {
	int name;
	boolean mark;
	public Node(int name) {
		//constructor that creates a node and gives it an int name from 0- n-1
		this.name = name;
		
	}
	//set mark
	public void setMark(boolean mark) {
		this.mark = mark;
	}//return mark
	public boolean getMark() {
		return mark;
	
	} //get int
	public int getName() {
		return name;
	}

}
