/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies.rearrange;

import br.uff.es2.war.ai.BasicBot;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Territory;

/**
 * This interface describes a strategy that will be used for the rearrange the
 * soldiers on the {@link Game}. This interface is used by BOTs.
 * 
 * @see BasicBot
 * @author Victor Guimarães
 */
public interface RearrangeStrategy {

    /**
     * This method rearranges a number of soldiers among a the owners *
     * {@link Territory}s.
     */
    public void moveSoldiers();

}
