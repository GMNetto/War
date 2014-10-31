/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies.attack.allocation;

import br.uff.es2.war.model.Territory;
import br.uff.es2.war.ai.BasicBot;
import java.util.Set;

/**
 * This interface describes a strategy that will be used for the allocation of
 * soldiers on the {@link Game}. This interface is used by BOTs.
 * 
 * @see BasicBot
 * @author Victor Guimarães
 */
public interface AllocationStrategy {

    /**
     * This method allocates a number of soldiers among a {@link Set} of
     * {@link Territory}s.
     * 
     * @param soldierQuantity
     *            the number of soldiers
     * @param territories
     *            the {@link Set} of {@link Territory}
     */
    public void distributeSoldiers(int soldierQuantity,
	    Set<Territory> territories);

}
