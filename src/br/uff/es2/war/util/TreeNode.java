/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.util;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Victor Guimarães
 */
public class TreeNode<T> {
    
    T value;
    TreeNode<T> father;
    Set<TreeNode<T>> suns;

    public TreeNode(TreeNode<T> father) {
        this.father = father;
        this.suns = new LinkedHashSet<>();
    }

    public T getValue() {
        return value;
    }

    public TreeNode<T> getFather() {
        return father;
    }

    public final Set<TreeNode<T>> getSuns() {
        return new LinkedHashSet<>(this.suns);
    }
    
    public void addSun() {
        
    }
    
}
