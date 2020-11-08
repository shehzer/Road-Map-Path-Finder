
public class Edge {
int type;
Node start;
Node end;



	Edge(Node u, Node v, int type){
		//where u is the start
		//v is the end and type and public/private/reward
		this.start=u;
		this.end=v;
		this.type=type;
		
	}
	//return fEndpoint
	public Node firstEndpoint() {
		return start;
	}
	//return sEndpoint
	public Node secondEndpoint() {
		return end;
	}
	//return type
	int getType() {
		return type;
	}
}


