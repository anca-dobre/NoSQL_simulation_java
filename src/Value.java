
public class Value {
	Integer intValue = null;
	Float floatValue = null;
	String stringValue = null;

	public Value(String object, String type) {
		switch (type) {
		case "Integer":
			this.intValue = Integer.parseInt(object);
			break;
		case "Float":
			this.floatValue = Float.parseFloat(object);
			break;
		case "String":
			this.stringValue = object;
			break;
		}
	}

	public Value(Value value, String type) {
		switch (type) {
		case "Integer":
			if(value.intValue != null)
			this.intValue = value.intValue;  
			break;
		case "Float":
			if(value.floatValue != null)
			this.floatValue = value.floatValue;
			break;
		case "String":
			if(value.stringValue != null)
			this.stringValue = value.stringValue;
			break;
		} 
	}
}
