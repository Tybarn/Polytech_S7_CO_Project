package Alertes;

import Magasin.Magasin;
import Magasin.Panier;

public abstract class Alerte {
    // ----------------------------------- ATTRIBUTS -----------------------------------

    protected boolean dejaNotifiee;

    // ---------------------------------- CONSTRUCTEURS ----------------------------------

    public Alerte() {
        dejaNotifiee = false;
    }

    // ----------------------------------- METHODES -------------------------------------

    /**
     * Analyse un panier et notifie le magasin si besoin
     * @param panier: Panier a analyser
     */
    public abstract void notifier(Panier panier);

    /**
     * Permet d'afficher un message d'alerte
     * @param msg: Message a afficher
     */
    protected void afficherMessage(String msg) {
        System.out.println("\nALERTE : " + msg + "\n");
    }
    
    public abstract Alerte getCopy();
}
