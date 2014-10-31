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
public class LinearThresholdFunction implements ThresholdFunction{
    
    @Override
    public int value(int x,double weight) {
        return x>10?0:10-x;
    }
    
}
