/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.controller;

/**
 *
 * @author Victor Guimarães
 */
public enum ExceptionCauses {
    NONEXISTENT_ENTITY("There is no entity with this key on the data base.");

    private final String text;

    /**
     * @param text
     */
    private ExceptionCauses(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
