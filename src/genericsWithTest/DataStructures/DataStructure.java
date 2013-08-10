package genericsWithTest.DataStructures;

import java.util.Collection;

import genericsWithTest.Element;

public abstract class DataStructure implements IDataStructure {
	protected Class<? extends Element> dataType;
	protected int size;

	public boolean add(Element element) {
		if (!dataType.isInstance(element))
			throw new RuntimeException("Datatype to be stored here: " + dataType.getName()
					+ ", datatype tried to be stored: " + element.getClass().getName());
		return true;
	}
}
