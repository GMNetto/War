/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.model.objective;

import java.util.Comparator;

/**
 *
 * @author Victor
 */
public class ObjectiveComparator implements Comparator<Objective> {

    @Override
    public int compare(Objective o1, Objective o2) {
        return o1.toString().compareTo(o2.toString());
    }
    
}
