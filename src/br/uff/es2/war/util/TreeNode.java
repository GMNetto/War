/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.util;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class that represent a node of a tree.
 *
 * @author Victor Guimarães
 * @param <T> A type of value to keep on the nodes
 */
public class TreeNode<T> {

    /**
     * A value to hold on a node.
     */
    private T value;

    /**
     * The node's father.
     */
    private TreeNode<T> father;

    /**
     * A {@link Set} of sons.
     */
    private Set<TreeNode<T>> sons;

    /**
     * A constructor without parameter. Use it only for the tree's root. Once
     * you create the node, you will not be able to set a father.
     */
    public TreeNode() {
        this.sons = new LinkedHashSet<>();
    }

    /**
     * A constructor with the node's father.
     *
     * @param father the node's father
     */
    public TreeNode(TreeNode<T> father) {
        this();
        this.father = father;
    }
    
    /**
     * A constructor with the node's father and value.
     *
     * @param father the node's father
     * @param value the value
     */
    public TreeNode(TreeNode<T> father, T value) {
        this(father);
        this.value = value;
    }
    /**
     * Setter for the node's value.
     *
     * @param value the node's value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Getter for the node's value.
     *
     * @return the node's value
     */
    public T getValue() {
        return value;
    }

    /**
     * Getter for the node's father.
     *
     * @return the node's father
     */
    public TreeNode<T> getFather() {
        return father;
    }

    /**
     * Getter for the node's sons.
     *
     * @return the node's sons
     */
    public final Set<TreeNode<T>> getSons() {
        return this.sons;
    }

    /**
     * Method to add a son on the node's {@link Set}.
     *
     * @param son the son
     */
    public void addSon(TreeNode<T> son) {
        son.father = this;
        this.sons.add(son);
    }
    
    /**
     * Method to add a {@link Collection} of sons.
     *
     * @param sons a {@link Collection} of sons
     */
    public void addAllSons(Collection<TreeNode<T>> sons) {
        for (TreeNode<T> son : sons) {
            addSon(son);
        }
    }

}
