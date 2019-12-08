import java.util.ArrayList;

public class LSQLDB {

	public String name;
	public int noNodes;
	public int maxCapacity;
	ArrayList<Node> nodes = new ArrayList<Node>();
	ArrayList<Entity> entitys = new ArrayList<Entity>();
	
	public LSQLDB (String name, int noNodes, int maxCapacity){
		this.name = name;
		this.noNodes = noNodes;
		this.maxCapacity = maxCapacity;
	}
	
}
