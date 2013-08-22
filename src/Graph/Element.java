package Graph;

public abstract class Element implements IElement {

	public abstract int getIndex();
	
	public boolean deepEquals(IElement other) {
		return this.getStringRepresentation().equals(other.getStringRepresentation());
	}

}
