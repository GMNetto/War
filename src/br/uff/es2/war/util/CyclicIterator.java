package br.uff.es2.war.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
    private int cicles;
    
    public CyclicIterator(Iterable<E> elements) {
	this.elements = elements;
	innerIterator = elements.iterator();
	cicles = 0;
    }
    
    public CyclicIterator(E[] elements) {
	this(Arrays.asList(elements));
    }
    
    public int getCycles() {
	return cicles;
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
	try{
	    return innerIterator.next();
	}catch(NoSuchElementException e){
	    if(hasNext()){
		cicles++;
		return next();
	    }
	    throw e;
	}
    }

    @Override
    public void remove() {
	innerIterator.remove();
    }
}
