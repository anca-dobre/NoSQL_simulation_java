import java.util.ArrayList;

public class Entity implements Comparable<Object> {
	
	public String type;
	public int replicationFactor;
	public int noAttributes;
	long timestamp = 0;   
	ArrayList<Attribute> atributes = new ArrayList <Attribute>() ;
	
	public Entity (String type, int replicationFactor, int noAttributes) {
		this.type = type;
		this.replicationFactor = replicationFactor;
		this.noAttributes = noAttributes;
	}

	public  Entity (Entity another, boolean hasValue) {
		this.type = another.type;
		this.replicationFactor = another.replicationFactor;
		this.noAttributes = another.noAttributes;
		this.atributes = new ArrayList<Attribute>();
		this.timestamp = another.timestamp;
		for(Attribute atribut : another.atributes) {
			this.atributes.add(new Attribute (atribut, hasValue));
		}
	}

	@Override
	public int compareTo(Object o) {
	        long compareTimestamp=((Entity)o).timestamp;
	        return (int) (compareTimestamp-this.timestamp);

	}
}
