package model;
// Generated 04/09/2014 19:22:09 by Hibernate Tools 3.6.0



/**
 * JogadorcartaId generated by hbm2java
 */
public class JogadorcartaId  implements java.io.Serializable {


     private int codCarta;
     private int codPartida;

    public JogadorcartaId() {
    }

    public JogadorcartaId(int codCarta, int codPartida) {
       this.codCarta = codCarta;
       this.codPartida = codPartida;
    }
   
    public int getCodCarta() {
        return this.codCarta;
    }
    
    public void setCodCarta(int codCarta) {
        this.codCarta = codCarta;
    }
    public int getCodPartida() {
        return this.codPartida;
    }
    
    public void setCodPartida(int codPartida) {
        this.codPartida = codPartida;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof JogadorcartaId) ) return false;
		 JogadorcartaId castOther = ( JogadorcartaId ) other; 
         
		 return (this.getCodCarta()==castOther.getCodCarta())
 && (this.getCodPartida()==castOther.getCodPartida());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getCodCarta();
         result = 37 * result + this.getCodPartida();
         return result;
   }   


}


