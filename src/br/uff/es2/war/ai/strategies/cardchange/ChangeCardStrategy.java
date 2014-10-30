/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.ai.strategies.cardchange;

import br.uff.es2.war.model.Card;
import java.util.List;

/**
 *
 * @author Gustavo
 */
public interface ChangeCardStrategy {
    public List<Card> changeCard(List<Card> cards);
}
