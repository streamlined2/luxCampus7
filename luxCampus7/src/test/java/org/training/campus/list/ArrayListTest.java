package org.training.campus.list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.NoSuchElementException;

class ArrayListTest {

	@Test
	@DisplayName("default constructor")
	void testArrayList() {
		var list = new ArrayList<String>();
		assertEquals(0, list.size());
		assertEquals(ArrayList.INITIAL_CAPACITY, list.capacity());
	}

	@Test
	@DisplayName("initial capacity constructor success")
	void testArrayListIntSuccess() {
		final int initialCapacity = 50;
		var list = new ArrayList<String>(initialCapacity);
		assertEquals(0, list.size());
		assertEquals(initialCapacity, list.capacity());
	}

	@Test
	@DisplayName("initial capacity constructor failure")
	void testArrayListIntFail() {
		final int initialCapacity = -20;
		assertThrows(IllegalArgumentException.class, () -> new ArrayList<String>(initialCapacity));
	}

	@Test
	@DisplayName("varargs constructor")
	void testArrayListEArray() {
		final var data = new String[] { "A", "B", "C", "D", "E", "F" };
		final var list = new ArrayList<>(data);
		assertEquals(data.length, list.size());
		assertEquals(list.getNewCapacity(data.length), list.capacity());
		assertTrue(Arrays.equals(data, list.toArray()));
	}

	@Test
	@DisplayName("add element to last position in list")
	void testAddE() {
		var list = new ArrayList<>(1);
		list.add("A");
		assertEquals(1, list.size());
		assertEquals(1, list.capacity());
		list.add("B");
		assertEquals(2, list.size());
		assertEquals(3, list.capacity());
		list.add("C");
		assertEquals(3, list.size());
		assertEquals(3, list.capacity());
		assertTrue(Arrays.equals(new String[] { "A", "B", "C" }, list.toArray()));
		list.add(null);
		assertEquals(4, list.size());
		assertTrue(Arrays.equals(new String[] { "A", "B", "C", null }, list.toArray()));
	}

	@Test
	@DisplayName("add element at any given position in list")
	void testAddEInt() {
		var list = new ArrayList<>("A", "B", "C");
		list.add("Z", 0);
		assertEquals(4, list.size());
		assertTrue(Arrays.equals(new String[] { "Z", "A", "B", "C" }, list.toArray()));
		list.add("Y", 2);
		assertEquals(5, list.size());
		assertTrue(Arrays.equals(new String[] { "Z", "A", "Y", "B", "C" }, list.toArray()));
		list.add("X", 4);
		assertEquals(6, list.size());
		assertTrue(Arrays.equals(new String[] { "Z", "A", "Y", "B", "X", "C" }, list.toArray()));
	}

	@Test
	@DisplayName("adding element at negative position or beyond list size fails")
	void testAddEIntFail() {
		var list = new ArrayList<>("A", "B", "C");
		assertThrows(IndexOutOfBoundsException.class, () -> list.add("K", -1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add("K", list.size() + 1));
	}

	@Test
	@DisplayName("remove element at any position within list")
	void testRemoveSuccess() {
		var list = new ArrayList<>("A", "B", "C");
		assertEquals(3, list.size());
		assertTrue(Arrays.equals(new String[] { "A", "B", "C" }, list.toArray()));
		list.remove(1);
		assertEquals(2, list.size());
		assertTrue(Arrays.equals(new String[] { "A", "C" }, list.toArray()));
		list.remove(1);
		assertEquals(1, list.size());
		assertTrue(Arrays.equals(new String[] { "A" }, list.toArray()));
		list.remove(0);
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
	}

	@Test
	@DisplayName("removing element at negative position or beyond list size fails")
	void testRemoveFail() {
		var list = new ArrayList<>("A", "B", "C");
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size()));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-10));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size() + 10));
	}

	@Test
	@DisplayName("get element at any position within list")
	void testGetSuccess() {
		var list = new ArrayList<>("A", "B", "C");
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		assertEquals("C", list.get(2));
	}

	@Test
	@DisplayName("getting element at negative position or beyond list size fails")
	void testGetFail() {
		var list = new ArrayList<>("A", "B", "C");
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(list.size()));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-10));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(list.size() + 10));
	}

	@Test
	@DisplayName("set element at any position within list")
	void testSetSuccess() {
		var list = new ArrayList<>("A", "B", "C");
		var old = list.get(0);
		assertEquals(old, list.set("0", 0));
		assertEquals("0", list.get(0));
		old = list.get(1);
		assertEquals(old, list.set("1", 1));
		assertEquals("1", list.get(1));
		old = list.get(2);
		assertEquals(old, list.set("2", 2));
		assertEquals("2", list.get(2));
	}

	@Test
	@DisplayName("setting element at negative position or beyond list size fails")
	void testSetFail() {
		var list = new ArrayList<>("A", "B", "C");
		assertThrows(IndexOutOfBoundsException.class, () -> list.set("!", -1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set("!", list.size()));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set("!", -10));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set("!", list.size() + 10));
	}

	@Test
	@DisplayName("clearing list")
	void testClear() {
		var list = new ArrayList<>("A", "B", "C");
		assertFalse(list.isEmpty());
		assertEquals(3, list.size());
		list.clear();
		assertTrue(list.isEmpty());
		assertEquals(0, list.size());
		list.forEach(Assertions::assertNull);
	}

	@Test
	@DisplayName("check if list contains given element")
	void testContains() {
		var list = new ArrayList<>("A", "B", "C");
		assertTrue(list.contains("A"));
		assertTrue(list.contains("C"));
		assertFalse(list.contains("Z"));
		assertFalse(list.contains("9"));
		assertFalse(list.contains(null));
	}

	@Test
	@DisplayName("get index of given element")
	void testIndexOfE() {
		var list = new ArrayList<>("A", "B", "C");
		assertEquals(0, list.indexOf("A"));
		assertEquals(2, list.indexOf("C"));
		assertEquals(-1, list.indexOf("0"));
		assertEquals(-1, list.indexOf(null));
	}

	@Test
	@DisplayName("get occurence of given element searching from passed index")
	void testIndexOfEInt() {
		var list = new ArrayList<>("A", "B", "A", "C", "A");
		assertEquals(0, list.indexOf("A", 0));
		assertEquals(2, list.indexOf("A", 1));
		assertEquals(4, list.indexOf("A", 3));
	}

	@Test
	@DisplayName("get last index of given element")
	void testLastIndexOfE() {
		var list = new ArrayList<>("A", "B", "A", "C", "A");
		assertEquals(4, list.lastIndexOf("A"));
		assertEquals(3, list.lastIndexOf("C"));
		assertEquals(1, list.lastIndexOf("B"));
		assertEquals(-1, list.lastIndexOf("1"));
	}

	@Test
	@DisplayName("get last occurence of given element searching from passed index")
	void testLastIndexOfEInt() {
		var list = new ArrayList<>("A", "B", "A", "C", "A");
		assertEquals(4, list.lastIndexOf("A", 4));
		assertEquals(2, list.lastIndexOf("A", 3));
		assertEquals(0, list.lastIndexOf("A", 1));
	}

	@Test
	@DisplayName("iterator for empty list fails to fetch next element")
	void testIteratorFail() {
		var list = new ArrayList<>();
		var i = list.iterator();
		assertThrows(NoSuchElementException.class, () -> i.next());
	}

	@Test
	@DisplayName("list iterator succeeds")
	void testIteratorSuccess() {
		var list = new ArrayList<>("A", "B", "A", "C", "A");
		list.forEach(x -> {
			assertInstanceOf(String.class, x);
			assertNotNull(x);
		});
	}

	@Test
	@DisplayName("conversion to string")
	void testToString() {
		var list = new ArrayList<>("A", "B", "C");
		assertEquals("[A,B,C]", list.toString());
	}

}
