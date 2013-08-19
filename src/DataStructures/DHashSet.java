package DataStructures;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import Graph.IElement;
import Graph.Edges.Edge;
import Graph.Nodes.Node;
import Utils.Rand;

public class DHashSet extends DataStructure implements IEdgeListDatastructure {
	private HashSet<IElement> list;

	public DHashSet(Class<? extends IElement> dT) {
		this.init(dT, defaultSize);
	}

	@Override
	public void init(Class<? extends IElement> dT, int initialSize) {
		this.list = new HashSet<>(initialSize);
		this.dataType = dT;
	}

	@Override
	public boolean add(IElement element) {
		super.add(element);
		return element != null && this.list.add(element);
	}

	@Override
	public boolean contains(IElement element) {
		return list.contains(element);
	}

	@Override
	public int size() {
		return list.size();
	}

	public Edge get(Edge e) {
		for (Object o : list) {
			if (o.equals(e))
				return (Edge) o;
		}
		return null;
	}

	@Override
	public IElement getRandom() {
		int index = Rand.rand.nextInt(this.list.size());
		int counter = 0;
		Iterator<IElement> iter = this.list.iterator();
		while (iter.hasNext()) {
			if (counter == index) {
				return iter.next();
			}
			iter.next();
			counter++;
		}
		return null;
	}


	@Override
	public Collection<IElement> getElements() {
		return this.list;
	}

	@Override
	public boolean removeEdge(Edge element) {
		return this.list.remove(element);
	}

	@Override
	public Iterator<IElement> iterator() {
		return this.list.iterator();
	}

	@Override
	public boolean remove(IElement element) {
		if ( element instanceof Edge ) return this.removeEdge((Edge) element);
		else throw new RuntimeException("Cannot remove a non-edge from an edge list");
	}

	@Override
	public boolean canStore(Class<? extends IElement> o) {
		return ( o.isAssignableFrom(Edge.class));
	}
	
}
