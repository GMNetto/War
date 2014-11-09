/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.ai.strategies.cardchange;

import java.util.List;

import br.uff.es2.war.model.Card;

/**
 *
 * @author Gustavo
 */
public interface ChangeCardStrategy {
    public List<Card> changeCard(List<Card> cards);
}
