package br.uff.es2.war.model;

import java.util.Iterator;

public class CyclicIterator<E> implements Iterator<E> {
    
    private final Iterable<E> elements;
    private Iterator<E> innerIterator;
    
    public CyclicIterator(Iterable<E> elements) {
	this.elements = elements;
	innerIterator = elements.iterator();
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
