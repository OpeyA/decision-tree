
public class Node {

	private String name;
	private Node childTrue;
	private Node childFalse;
	private int value;
	
	public Node(String pName)
	{
		name = pName;
		childTrue = null;
		childFalse = null;
		value = 0;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String pName){
		name = pName;
	}
	
	public Node getChildTrue(){
		return childTrue;
	}
	
	public void setChildTrue(Node pChild){
		childTrue = pChild;
	}
	
	public Node getChildFalse(){
		return childFalse;
	}
	
	public void setChildFalse(Node pChild){
		childFalse = pChild;
	}
	
	public int value(){
		return value;
	}
	
	public void value(int pValue){
		value = pValue;
	}
	
	public String toString(){
		String temp = new String(this.getName());
		temp += "\n" + "   " + "yes:" + "\n" + "      " + getChildTrue() + "\n";
		temp += "   " + "no:" + "\n" + "      " + getChildFalse() + "\n";
		return temp;
	}
	
}
