package Offres;

import Client.Personne;
import Exceptions.ExceptionCategorieNonPromo;
import Magasin.Article;
import Offres.Offre;

import java.util.ArrayList;

public class OffreFlash implements Offre {

	private ArrayList<Article> articles;
	private ArrayList<Integer> reductions; // nombre a appliquer a l'article, -1 si l'article ne recoit pas de promotion
	
	public OffreFlash(ArrayList<Article> arts, ArrayList<Integer> reducs) throws Exception {
		// verifier que la taille de reduction est la meme que articles
		if(arts.size() != reducs.size()) {
			System.out.println("Erreur : le nombre d'article ("+ arts.size()
					+") et le nombre de reduction ("+ reducs.size() + ") ne correspondent pas");
			throw new Exception("Erreur de taille d'article ou de reduction");
		}
		for(int i=0;i<arts.size();i++) {
			// lancer une exception quand une promotion est sur un article non promouvable
			if(!arts.get(i).getCategorie().estPromouvable() && reducs.get(i) > 100 && reducs.get(i) < 0) {
				System.out.println("Erreur : La reduction ("+ reducs.get(i) +") ne peux etre applique sur l'article "+ arts.get(i).getNom());
				throw new ExceptionCategorieNonPromo();
			}	
		}
		articles=arts;
		reductions=reducs;
	}

	
	@Override
	public void appliquerOffre(ArrayList<Article> listeArticle) {
			appliquerReduc(listeArticle, reductions, articles);	
	}
	
	// appliquer un nombre de fois l'offre flash sur le panier 
	public static void appliquerReduc(ArrayList<Article> listeArticles, ArrayList<Integer> reduc , ArrayList<Article> articlespromo) {
		ArrayList<Article> listeAVerifier = new ArrayList<Article>(); // on cree une copie des articles afin d enlever les articles traites au fur et a mesure
		for(int k=0;k<listeArticles.size();k++) {
			listeAVerifier.add(new Article(listeArticles.get(k)));
		}
		int i=0,j=0,l=0;
		Article a= null;
		int k= nombreDeReduc(listeArticles,articlespromo);	// on va chercher combien de fois ma promo peut etre applique
		while(i<listeAVerifier.size() && j<articlespromo.size() && l<=k) {
			if(!listeAVerifier.contains(articlespromo.get(j))) {
				break;
			}
			a= listeAVerifier.get(i);
			if(articlespromo.get(j).equals(a)) {
				listeArticles.get(i).setPrix(listeArticles.get(i).getPrix()-listeArticles.get(i).getPrix()*reduc.get(j)/100);
				listeAVerifier.set(i, new Article());
				j++;
				i=-1;
				if(j == articlespromo.size()) {
					l++;
					j=0;
				}
				
					
			}
			i++;
		}
	}
		
	// permet de savoir selon une liste d article en promotion, le nombre de fois quelle apparait dans mon panier
	public static int nombreDeReduc(ArrayList<Article> listeArticles , ArrayList<Article> articlespromo) {
		ArrayList<Article> listeAVerifier = new ArrayList<Article>(); // permet de savoir si un article du panier a ete traiter
		for(int k=0;k<listeArticles.size();k++) {
			listeAVerifier.add(new Article(listeArticles.get(k)));
		}
		int i=0,j=0,k=0;
		Article a= null;
		while(i<listeAVerifier.size() && j<articlespromo.size()) {
			if(!listeAVerifier.contains(articlespromo.get(j)))
				break;
			a= listeAVerifier.get(i);
			if(articlespromo.get(j).equals(a)) {
				listeAVerifier.set(i, new Article());
				j++;
				i=0;
				if(j== articlespromo.size()) {
					k++;
					j=0;
				}
			}
			a= listeAVerifier.get(i);
			i++;
		}
		return k;
	}
}
