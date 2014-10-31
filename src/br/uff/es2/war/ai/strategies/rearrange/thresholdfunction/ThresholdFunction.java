/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.ai.strategies.rearrange.thresholdfunction;

/**
 *
 * @author Gustavo
 */
public interface ThresholdFunction {
    public int value(int x,double weight);
}
