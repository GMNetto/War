/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view.widget;

/**
 * 
 * @author anacarolinegomesvargas
 */
public interface TerritoryUIStrategy {

    public void buttonAction(TerritoryUI ter);
    
    public TerritoryUIStrategy nextPhase();
}
