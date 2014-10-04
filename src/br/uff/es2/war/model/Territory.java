package br.uff.es2.war.model;

import br.uff.es2.war.entity.Ocupacao;
import br.uff.es2.war.entity.Territorio;
import java.util.HashSet;
import java.util.Set;

/**
 * A territory is owned by a player and occupied by 
 * soldiers. Territories are nodes from a graph where
 * a edge between two territories mean that one is on
 * the borders of the other.
 * 
 * @author Arthur Pitzer
 */
public class Territory {
    
    private final String name;
    private Set<Territory> borders;
    private Player owner;
    private int soldiers;
    private Territorio territory;
    private Ocupacao occupation;
    
    @Deprecated
    public Territory(String name) {
	this.name = name;
	this.borders = new HashSet<>();
    }
    
    public Territory(Territorio territory){
        this.territory=territory;
        this.name=territory.getNome();
    }
    
    public Territorio getBDTerritorio(){
        return territory;
    }
    
    public boolean isInBorder(Territory territory){
        return this.territory.getTerritorioCollection().contains(territory.getBDTerritorio());
    }
    
    
    
    public String getName() {
        return name;
    }
    
    public Set<Territory> getBorders() {
        return borders;
    }
    
    public void addBorder(Territory border) {
        borders.add(border);
    }
    
    public Player getOwner(){
	return owner;
    }
    
    public void setOwner(Player owner){
        occupation=new Ocupacao(territory.getCodTerritorio(), owner.getJogador().getCodJogador(), );
	this.owner = owner;
    }
    
    public int getSoldiers(){
	return soldiers;
    }
    
    public void setSoldiers(int soldiers){
	this.soldiers = soldiers;
    }
    
    public void addSoldiers(int soldiers){
	this.soldiers += soldiers;
    }
    
    public void removeSoldiers(int soldiers){
	this.soldiers -= soldiers;
    }

    @Override
    public String toString() {
        return "Territory Name:\t" + name;
    }
}