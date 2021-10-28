package org.training.campus.list;

public interface List<E> extends Iterable<E> {

	void add(E value);

	void add(E value, int index);

	E remove(int index);

	E get(int index);

	E set(E value, int index);

	void clear();

	int size();

	boolean isEmpty();

	boolean contains(E value);

	int indexOf(E value);

	int indexOf(E value, int startIndex);

	int lastIndexOf(E value);

	int lastIndexOf(E value, int startIndex);

	public Object[] toArray();

}
