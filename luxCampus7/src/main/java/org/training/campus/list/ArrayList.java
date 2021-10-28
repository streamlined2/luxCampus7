package org.training.campus.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

import static java.lang.System.arraycopy;

public class ArrayList<E> implements List<E> {
	static final int INITIAL_CAPACITY = 10;

	private Object[] chunk;
	private int size;

	public ArrayList() {
		this(INITIAL_CAPACITY);
	}

	public ArrayList(int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException("initial capacity should be positive value");
		chunk = new Object[capacity];
		size = 0;
	}

	public ArrayList(E... data) {
		chunk = new Object[data.length];
		size = data.length;

		int k = 0;
		for (E e : data) {
			chunk[k++] = e;
		}
	}

	int capacity() {
		return chunk.length;
	}

	private int getNewCapacity(int requestedCapacity) {
		return requestedCapacity * 3 / 2;
	}

	private void expandInsert(int requestedCapacity, E value, int insertIndex) {
		Object[] newChunk = new Object[getNewCapacity(requestedCapacity)];
		arraycopy(chunk, 0, newChunk, 0, insertIndex);
		newChunk[insertIndex] = value;
		arraycopy(chunk, insertIndex, newChunk, insertIndex + 1, size - insertIndex);
		chunk = newChunk;
		size++;
	}

	private void shiftInsert(E value, int insertIndex) {
		arraycopy(chunk, insertIndex, chunk, insertIndex + 1, size - insertIndex);
		chunk[insertIndex] = value;
	}

	@Override
	public void add(E value) {
		add(value, size);
	}

	@Override
	public void add(E value, int index) {
		Objects.checkIndex(index, size + 1);
		if (size() < capacity()) {
			shiftInsert(value, index);
		} else {
			expandInsert(size() + 1, value, index);
		}
	}

	private E removeShift(int removeIndex) {
		E value = (E) chunk[removeIndex];
		arraycopy(chunk, removeIndex + 1, chunk, removeIndex, size - removeIndex - 1);
		return value;
	}

	@Override
	public E remove(int index) {
		Objects.checkIndex(index, size);
		return removeShift(index);
	}

	@Override
	public E get(int index) {
		Objects.checkIndex(index, size);
		return (E) chunk[index];
	}

	@Override
	public E set(E value, int index) {
		E oldValue = get(index);
		chunk[index] = value;
		return oldValue;
	}

	@Override
	public void clear() {
		size = 0;
		for (int k = 0; k < chunk.length; k++) {
			chunk[k] = null;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size <= 0;
	}

	@Override
	public boolean contains(E value) {
		return indexOf(value) >= 0;
	}

	@Override
	public int indexOf(E value) {
		return indexOf(value, 0);
	}

	@Override
	public int indexOf(E value, int startIndex) {
		for (int k = startIndex; k < size; k++) {
			if (Objects.equals(chunk[k], value))
				return k;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(E value) {
		return lastIndexOf(value, size - 1);
	}

	@Override
	public int lastIndexOf(E value, int startIndex) {
		for (int k = startIndex; k >= 0; k--) {
			if (Objects.equals(chunk[k], value))
				return k;
		}
		return -1;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public E next() {
				if (!hasNext())
					throw new NoSuchElementException("no more elements, iterator exhausted");
				return (E) chunk[index++];
			}

		};
	}

	@Override
	public String toString() {
		final var join = new StringJoiner(",", "[", "]");
		for (E e : this) {
			join.add(e.toString());
		}
		return join.toString();
	}

}
