/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies.attack;

import br.uff.es2.war.model.Combat;

/**
 * This interface describes a strategy that will be used on the attack phase of
 * the {@link Game}. This interface is used by BOTs.
 *
 * @author Victor Guimarães
 */
public interface AttackStrategy {

    /**
     * This method generates a {@link Combat} based on a strategy.
     *
     * @return the {@link Combat} to be realized or null, in case of skip the
     * combat phase
     */
    public Combat declareCombat();

}
