package Offres;

import Client.Personne;
import Exceptions.ExceptionCategorieNonPromo;
import Magasin.Article;
import Offres.Offre;

import java.util.ArrayList;


public class OffreProduit implements Offre {

	// --------------------------------------- ATTRIBUTS ----------------------------------------

	private Article article;
	private int reduction;

	// --------------------------------------- CONSTRUCTEURS ----------------------------------------

	public OffreProduit(Article art, int reduc) throws ExceptionCategorieNonPromo {
		if(art == null || reduc <= 0 || reduc > 100)
			throw new IllegalArgumentException();
		if(!art.getCategorie().estPromouvable())
			throw new ExceptionCategorieNonPromo();
		article=art;
		reduction=reduc;
	}

	// --------------------------------------- METHODES ----------------------------------------

	/**
	 * Applique une reduction en % sur tous les articles de la liste qui correspondent au produit sur lequel porte l'offre
	 * @param listeArticle: Liste contenant les articles sur lesquels nous allons potentiellement appliquer l'offre
	 */
	@Override
	public void appliquerOffre(ArrayList<Article> listeArticle) {
		for(int i=0;i<listeArticle.size();i++) {
			if(article.equals(listeArticle.get(i))){
				listeArticle.get(i).setPrix(listeArticle.get(i).getPrix()-listeArticle.get(i).getPrix()*reduction/100);
			}
		}
	}

}
