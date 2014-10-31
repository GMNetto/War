/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.util;

import java.util.Objects;

/**
 * Class to hold three types of value together.
 *
 * @author Victor Guimarães
 * @param <I> first type
 * @param <J> second type
 * @param <K> third type
 */
public class Triple<I, J, K> {

    private I first;
    private J second;
    private K third;

    /**
     * Default constructor without parameters.
     */
    public Triple() {
    }

    /**
     * Constructor with all the three values.
     *
     * @param first the first value
     * @param second the second value
     * @param third the third value
     */
    public Triple(I first, J second, K third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Getter for the first value.
     *
     * @return the first value
     */
    public I getFirst() {
        return first;
    }

    /**
     * Setter for the first value.
     *
     * @param first the first value
     */
    public void setFirst(I first) {
        this.first = first;
    }

    /**
     * Getter for the second value.
     *
     * @return the second value
     */
    public J getSecond() {
        return second;
    }

    /**
     * Setter for the second value.
     *
     * @param second the second value
     */
    public void setSecond(J second) {
        this.second = second;
    }

    /**
     * Getter for the third value.
     *
     * @return the third value
     */
    public K getThird() {
        return third;
    }

    /**
     * Getter for the third value.
     *
     * @param third the third value
     */
    public void setThird(K third) {
        this.third = third;
    }

    /**
     * Getter for all three the values as an array.
     *
     * @return the array with the three values
     */
    public Object[] asArray() {
        Object[] array = new Object[3];

        array[0] = first;
        array[1] = second;
        array[2] = third;

        return array;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.first);
        hash = 83 * hash + Objects.hashCode(this.second);
        hash = 83 * hash + Objects.hashCode(this.third);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
        if (!Objects.equals(this.first, other.first))
            return false;
        if (!Objects.equals(this.second, other.second))
            return false;
        if (!Objects.equals(this.third, other.third))
            return false;
        return true;
    }
}
