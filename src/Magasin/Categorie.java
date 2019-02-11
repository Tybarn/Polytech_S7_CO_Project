package Magasin;

import java.util.ArrayList;

public class Categorie {

	// --------------------------------------- ATTRIBUTS ---------------------------------------

	String nom;
	boolean promouvable;
	ArrayList<Article> articles;

	// --------------------------------------- CONSTRUCTEURS ---------------------------------------

	public Categorie(String nom) {
		this.nom = nom;
		this.promouvable = true;
		this.articles = new ArrayList<>();
	}

	public Categorie(String nom, boolean promouvable) {
		this.nom = nom;
		this.promouvable = promouvable;
		this.articles = new ArrayList<>();
	}

	// --------------------------------------- METHODES ---------------------------------------

	// Concrete --------------------------------------------

	public boolean estPromouvable() {
		return promouvable;
	}

	public boolean ajouterArticle(Article a) {
	    if(a == null)
	        return false;
	    return articles.add(a);
    }

    public boolean supprimerArticle(Article a) {return articles.remove(a);}

    public ArrayList<Article> getArticles() {
	    ArrayList<Article> copy = new ArrayList<>();
	    for(Article a : articles)
	        copy.add(a);
	    return copy;
    }

	// Redefinition ---------------------------------------

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(obj instanceof Categorie) {
			Categorie cat = (Categorie) obj;
			return nom.equals(cat.nom) && promouvable == cat.promouvable && articles.equals(cat.articles);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Categorie " + nom;
	}


}
