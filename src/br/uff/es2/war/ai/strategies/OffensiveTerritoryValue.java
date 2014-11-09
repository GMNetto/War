/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies;

import br.uff.es2.war.model.Territory;

/**
 * This class is used for a more offensive strategy. Its {@link #getTerritoryValue(br.uff.es2.war.model.Territory)
 * } returns a value that represents the how good a {@link Territory} is to be
 * the source of an attack.
 *
 * @author Victor Guimarães
 */
public class OffensiveTerritoryValue extends TerritoryValue {

    /**
     * The {@link WinLoseTerritoryValue} to know how good an attack is.
     */
    private final WinLoseTerritoryValue winLoseTerritoryValue;

    /**
     * Constructor with the {@link WinLoseTerritoryValue} to know how good an
     * attack is.
     *
     * @param winLoseTerritoryValue the {@link WinLoseTerritoryValue}
     */
    public OffensiveTerritoryValue(WinLoseTerritoryValue winLoseTerritoryValue) {
        this.winLoseTerritoryValue = winLoseTerritoryValue;
    }

    @Override
    public double getTerritoryValue(Territory territory) {
        double attackOpportunity = 0, aux = 0;
        int count = 0;

        for (Territory border : territory.getBorders()) {
            if (!border.getOwner().equals(territory.getOwner())) {
                aux += winLoseTerritoryValue.getTerritoryValue(border);
                count++;
            }
        }
        attackOpportunity = aux / count;

        return attackOpportunity;
    }

}
