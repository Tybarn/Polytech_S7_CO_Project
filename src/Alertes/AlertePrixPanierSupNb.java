package Alertes;

import Magasin.Panier;

public class AlertePrixPanierSupNb extends Alerte {
    // -------------------------------------------- ATTRIBUTS -----------------------------------------

	double prixPanierLimite;

    // -------------------------------------------- CONSTRUCTEURS -----------------------------------------

	public AlertePrixPanierSupNb(double prixPanierMax) {
		super();
		prixPanierLimite = prixPanierMax;
	}

    // -------------------------------------------- METHODES -----------------------------------------

	/**
	 * Analyse un panier et notifie le magasin s'il le prix total du panier depasse la limite fixee pour cette alerte
	 * @param panier: Panier a analyser
	 */
	public void notifier(Panier panier) {
		double prixTotal = 0;
		if(!dejaNotifiee) {
			for (int i = 0; i < panier.getArticles().size(); i++) {
				prixTotal = prixTotal + panier.getArticles().get(i).getPrix();
			}
			if (prixTotal >= prixPanierLimite) {
				afficherMessage("Le panier de " + panier.getSaPersonne().getPrenom() + " " + panier.getSaPersonne().getNom()
						+ " a depasse la limite de valeur " + prixPanierLimite + "e avec une valeur totale de " + String.format("%.2f",prixTotal) + "e");
				dejaNotifiee = true;
			}
		}
	}

	public AlertePrixPanierSupNb getCopy() {
		return new AlertePrixPanierSupNb(prixPanierLimite);
	}
}
