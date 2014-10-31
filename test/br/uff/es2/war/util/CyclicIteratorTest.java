package br.uff.es2.war.util;

import static org.junit.Assert.assertEquals;

public class CyclicIteratorTest {

    public void FIRST_AFTER_LAST() {
	Integer[] numbers = new Integer[] { 1, 2, 3 };
	CyclicIterator<Integer> iterator = new CyclicIterator<>(numbers);
	Integer last = 0;
	for (int i = 0; i < numbers.length; i++)
	    last = iterator.next();
	assertEquals(last, numbers[0]);
    }

    public void COUNT_CYCLES() {
	final int cycles = 2;
	Integer[] numbers = new Integer[] { 1, 2, 3 };
	CyclicIterator<Integer> iterator = new CyclicIterator<>(numbers);
	for (int i = 0; i < numbers.length * cycles; i++)
	    iterator.next();
	assertEquals(iterator.getCycles(), cycles);
    }
}
