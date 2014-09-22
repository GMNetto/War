package br.uff.es2.war.util;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Iterates through an iterable and assuming that
 * the next element after the last one is the first one.  
 * 
 * @author Arthur Pitzer
 *
 * @param <E> Type of the elements
 */
public class CyclicIterator<E> implements Iterator<E> {
    
    private final Iterable<E> elements;
    private Iterator<E> innerIterator;
    
    public CyclicIterator(Iterable<E> elements) {
	this.elements = elements;
	innerIterator = elements.iterator();
    }
    
    public CyclicIterator(E[] elements) {
	this(Arrays.asList(elements));
    }

    @Override
    public boolean hasNext() {
	if(!innerIterator.hasNext()){
	    innerIterator = elements.iterator();
	    return innerIterator.hasNext();
	}
	return true;
    }

    @Override
    public E next() {
	return innerIterator.next();
    }

    @Override
    public void remove() {
	innerIterator.remove();
    }
}
