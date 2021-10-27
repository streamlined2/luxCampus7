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

	int capacity() {
		return chunk.length;
	}

	private int getNewCapacity(int requestedCapacity) {
		return requestedCapacity * 3 / 2;
	}

	private void allocateInsert(int requestedCapacity, E value, int insertIndex) {
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

	private void checkIndex(int index, int upperLimit) {
		if (index < 0 || index > upperLimit)
			throw new IndexOutOfBoundsException(String.format("index should be within [0..%d]", upperLimit));
	}

	@Override
	public void add(E value, int index) {
		checkIndex(index, size);
		if (size() < capacity()) {
			shiftInsert(value, index);
		} else {
			allocateInsert(size() + 1, value, index);
		}
	}

	private E removeShift(int removeIndex) {
		E value = (E) chunk[removeIndex];
		arraycopy(chunk, removeIndex + 1, chunk, removeIndex, size - removeIndex - 1);
		return value;
	}

	@Override
	public E remove(int index) {
		checkIndex(index, size - 1);
		return removeShift(index);
	}

	@Override
	public E get(int index) {
		checkIndex(index, size - 1);
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
		for (E e : this) {
			if (Objects.equals(e, value))
				return true;
		}
		return false;
	}

	@Override
	public int indexOf(E value) {
		for (int k = 0; k < size; k++) {
			if (Objects.equals(chunk[k], value))
				return k;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(E value) {
		for (int k = size - 1; k >= 0; k--) {
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
