package Magasin;

import Alertes.Alerte;
import Client.CarteFidelite;
import Client.Personne;
import Offres.Offre;

import java.util.ArrayList;

public class Panier {

	// ---------------------------------------- ATTRIBUTS ----------------------------------------

	private ArrayList<Alerte> observateurs;
	private ArrayList<Article> articles;
	private Personne saPersonne;

	// -------------------------------------- CONSTRUCTEURS --------------------------------------

	public Panier(Personne p) {
		 this.articles = new ArrayList<>();
		 this.saPersonne = p;
		 this.observateurs = new ArrayList<>();
		 ArrayList<Alerte> alertesEnCours = Magasin.getInstance().getAlertes();
		 for(Alerte a : alertesEnCours)
			 observateurs.add(a.getCopy());
	}

	// ---------------------------------------- METHODES ----------------------------------------

	// Gestion des observateurs (alertes) -------------------------------------

	public boolean ajouterObservateur(Alerte a) {
		if(!observateurs.contains(a)) {
			return observateurs.add(a);
		}
		return false;
	}

	public boolean supprimerObservateur(Alerte a) {
		return observateurs.remove(a);
	}

	public void notifierObs() {
		for(Alerte a : this.observateurs)
			a.notifier(this);
	}

	// Gestion des articles -------------------------------------------

	public boolean ajouterArticle(Article article) {
		if(article == null)
			return false;
		notifierObs();
		return articles.add(article);
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	// Paiement -------------------------------------------------------

    /**
     * Finalise le panier, les achats du client, en calculant le cout global du panier, le prix a payer en caisse
     * @return: Le prix global, final, du panier
     */
	public double payer(){
	    boolean cartesFidUtilisees = false;

		// Calcul d'un premier prix sans reduction
		double prixNonReduit = calculCoutTotalArticles(this.articles);
		
		// enlever les promos du prix des articles
		ArrayList<Article> copyArticles = new ArrayList<Article>();
		copierPanier(this.articles, copyArticles);
		appliquerOffres(copyArticles);
		double prixFinal = calculCoutTotalArticles(copyArticles);

		// Gerer les rabais et ajout des points de fidelite
		if(saPersonne.getSonStatut().autorisePointsFidelite() && saPersonne.estConnectee()) {
		    double prixApresCartesFid = saPersonne.appliquerRabaisCartesFid(prixFinal);
			if(prixApresCartesFid != prixFinal)
			    prixFinal = prixApresCartesFid;
			this.saPersonne.ajouterPointFidPrix(prixFinal);
		}

		// Affichage les details du paiement une fois termine
		System.out.println("Passage en caisse de " + saPersonne.getPrenom() + " " + saPersonne.getNom()
				+ ":\nListes des articles :");
		for(Article a : copyArticles)
			System.out.println(a);
		System.out.println("Prix final du panier: "+ String.format("%.2f", prixFinal));
		System.out.println("Prix avant reduction (offres" +
				(cartesFidUtilisees ? " et cartes de fidelite): " : "): ") + String.format("%.2f", prixNonReduit));
		System.out.println("Economie de : " + String.format("%.2f",(prixNonReduit - prixFinal)));
		if(saPersonne.getSonStatut().autorisePointsFidelite() && saPersonne.estConnectee())
			System.out.println("Nombre de points de fidelite gagnes : " + CarteFidelite.nombrePointsFidGagnes(prixFinal));
		System.out.print("\n");

		// Notifier concernant des alertes demandant un panier complet, pret a etre paye
		notifierObs();

		return prixFinal;
	}

	/**
     * Calcule le prix global de la liste d'articles passes en parametre
     * @param la: liste d'articles dont nous devons calculer le prix
     * @return: le prix global calcule
     */
	private double calculCoutTotalArticles(ArrayList<Article> la) {
		double somme=0;
		for(int i=0; i<la.size();i++) {
			somme += la.get(i).getPrix();
		}
		return somme;
	}

	/**
     * Applique les offres en cours dans le magasin a une liste d'article.
     * @param listeArticle: Le liste d'articles surlaquelle nous devons appliquer les offres
     */
	private void appliquerOffres(ArrayList<Article> listeArticle){
		// Application des offres ne necessitant pas le statut
		ArrayList<Offre> offres = Magasin.getInstance().getOffresEnCours();
		for(int i=0;i<offres.size();i++) {
			offres.get(i).appliquerOffre(listeArticle);
		}

		// Application des offres en specifique au statut de la personne
		if(!saPersonne.getSonStatut().estConnectable() || (saPersonne.getSonStatut().estConnectable() && saPersonne.estConnectee())) {
			offres = saPersonne.getSonStatut().getOffres();
			for (int i = 0; i < offres.size(); i++) {
				offres.get(i).appliquerOffre(listeArticle);
			}
		}
	}

	/**
     * Copie une liste d'articles dans une autre
     * @param a: Liste d'articles source
     * @param a2: Liste d'articles destination
     */
	private void copierPanier(ArrayList<Article> a, ArrayList<Article> a2) {
		for(int i=0;i<a.size();i++) {
			a2.add(new Article(a.get(i)));
		}
	}

	// Autres -----------------------------------------------------------
	
	@Override
	public String toString() {
		String s="Panier de "+ saPersonne.getPrenom() +" "+ saPersonne.getNom() +":\n";
		for(int i=0;i<articles.size();i++) {
			s += articles.get(i)+"\n";
		}
		return s;
	}

	public Personne getSaPersonne() {return saPersonne;}

}
