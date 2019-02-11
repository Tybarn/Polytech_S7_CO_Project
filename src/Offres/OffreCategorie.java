package Offres;

import Client.Personne;
import Exceptions.ExceptionCategorieNonPromo;
import Magasin.Article;
import Magasin.Categorie;
import Offres.Offre;

import java.util.ArrayList;


public class OffreCategorie implements Offre {

	// ---------------------------------------- ATTRIBUTS ------------------------------------------

	private Categorie categorie;
	private int reduction;

	// ---------------------------------------- CONSTRUCTEURS --------------------------------------

	public OffreCategorie(Categorie cat, int reduc) throws ExceptionCategorieNonPromo{
		if(cat == null || reduc <= 0 || reduc > 100)
			throw new IllegalArgumentException();
		if(!cat.estPromouvable())
			throw new ExceptionCategorieNonPromo();
		categorie = cat;
		reduction = reduc;
	}

	// ----------------------------------------- METHODES ------------------------------------------

    /**
     * Applique une reduction sur tous les articles appartenant a la categorie souhaitee.
     * @param listeArticle: La liste contenant les articles sur lequels nous allons potentiellement appliquer l'offre
     */
	@Override
	public void appliquerOffre(ArrayList<Article> listeArticle) {
		for(int i=0;i<listeArticle.size();i++) {
			if(categorie.equals(listeArticle.get(i).getCategorie())){
				listeArticle.get(i).setPrix(listeArticle.get(i).getPrix()-listeArticle.get(i).getPrix()*reduction/100);
			}
		}
	}

}
