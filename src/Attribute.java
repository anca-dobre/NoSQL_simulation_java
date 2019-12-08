

public class Attribute {
	
	public String type;
	public String name;
	public Value value;

	public Attribute(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
public Attribute(Attribute attribute, boolean hasValue) {
		type = attribute.type;
		name = attribute.name;
		if(hasValue == true)
			value = new Value(attribute.value, attribute.type);
	}

	public void setupValue (String object) {
		this.value = new Value(object, this.type);
	}

	public String getValue (String type) {
		String afisare = null;
		switch (type) {
		case "Integer":
			afisare = Integer.toString(this.value.intValue);
			break;
		case "Float":
			if(this.value.floatValue != Math.round(this.value.floatValue))
				afisare = Float.toString(this.value.floatValue);
			else 
				afisare = Integer.toString(Math.round(this.value.floatValue));
			break;
		case "String":
			afisare = this.value.stringValue;
			break;
		}
		return afisare;

	}
}
