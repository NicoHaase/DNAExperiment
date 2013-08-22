package Graph;

public interface IElement extends Comparable<Element> {
	public int getIndex();
	public String getStringRepresentation();
	public boolean deepEquals(IElement other);
}
