package Alertes;

import Magasin.Categorie;
import Magasin.Panier;

public class AlerteNbArticlesMemeCat extends Alerte {
	// -------------------------------------------- ATTRIBUTS -----------------------------------------

	int nbLimite;
	Categorie categorie;

	// -------------------------------------------- CONSTRUCTEURS -----------------------------------------

	public AlerteNbArticlesMemeCat(Categorie c, int limite) {
		super();
		categorie= c;
		nbLimite = limite;
	}

	// -------------------------------------------- METHODES -----------------------------------------

	/**
	 * Analyse un panier et notifie le magasin s'il y a autant ou plus de produits de la meme categorie dans le panier
	 * @param panier: Panier a analyser
	 */
	public void notifier(Panier panier) {
		if(!dejaNotifiee) {
			int cpt = 0;
			for (int i = 0; i < panier.getArticles().size(); i++) {
				if (panier.getArticles().get(i).getCategorie().equals(categorie))
					cpt++;
			}
			if (cpt >= nbLimite) {
				afficherMessage("Achat de plus de " + nbLimite + " article(s) de la classe " + categorie + " (" + cpt + ")");
				dejaNotifiee = true;
			}
		}
	}

	public AlerteNbArticlesMemeCat getCopy() {
		return new AlerteNbArticlesMemeCat(categorie,nbLimite);
	}
}
