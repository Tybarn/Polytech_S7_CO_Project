package Client;

import java.util.ArrayList;

import Alertes.Alerte;
import Exceptions.ExceptionStatut;
import Magasin.Panier;
import Magasin.Magasin;
import Magasin.Article;

public class Personne {

	// -------------------------------------------- ATTRIBUTS --------------------------------------------

	private String nom,prenom;
	private Statut sonStatut;
	private Panier sonPanier;
	private ArrayList<CarteFidelite> sesCartesFid;
	private boolean connecte;

	// -------------------------------------------- CONSTRUCTEURS --------------------------------------------

	public Personne(String prenom, String nom, Statut statut, ArrayList<CarteFidelite> sesCartesFid){
		this.nom = nom;
		this.prenom = prenom;
		this.sonStatut = statut;
        if(!sonStatut.ajouterPersonne(this)) {
            sonStatut = Visiteur.getInstance();
            sonStatut.ajouterPersonne(this);
        }
		this.sonPanier = new Panier(this);
		this.sesCartesFid = (sesCartesFid != null ? sesCartesFid : new ArrayList<>());
		this.connecte = false;
	}

	public Personne(String prenom, String nom, Statut statut) {
		this.nom = nom;
		this.prenom = prenom;
		this.sonStatut = statut;
		if(!sonStatut.ajouterPersonne(this)) {
            sonStatut = Visiteur.getInstance();
            sonStatut.ajouterPersonne(this);
        }
		this.sesCartesFid = new ArrayList<>();
		this.sonPanier = new Panier(this);
		this.connecte = false;
	}

	public Personne(String prenom, String nom) {
		this.nom = nom;
		this.prenom = prenom;
		this.sonStatut = Visiteur.getInstance();
		sonStatut.ajouterPersonne(this);
		this.sesCartesFid = new ArrayList<>();
		this.sonPanier = new Panier(this);
		this.connecte = false;
	}

	// -------------------------------------------- METHODES --------------------------------------------

	// Connexion ---------------------------------------------------

	/**
	 * Permet a la personne de se connecter au site
	 * @return: Succes (true) ou echec (false) de la connexion
	 */
	public boolean seConnecter() {
		if(connecte == false) {
			if (sonStatut.estConnectable()) {
				connecte = true;
				System.out.println(nom + " " + prenom + " vient de se connecter\n");
				return true;
			}
			else
				System.out.println("Cette personne n'a pas de compte\n");
		}
		else
			System.out.println(nom + " " + prenom + " est deja connecte");
		return false;
	}

	/**
	 * Permet a la personne de se deconnecter
	 * @return: Succes (true) ou echec (false) de la deconnexion
	 */
	public boolean seDeconnecter() {
		if(connecte == true) {
			if (sonStatut.estConnectable()) {
				connecte = false;
				System.out.println(nom + " " + prenom + " vient de se deconnecter\n");
				return true;
			}
			else
				System.out.println("cette personne n'a pas de compte\n");
		}
		else
			System.out.println(nom + " " + prenom + " n'est pas connecte. Impossible de vous deconnecter.");
		return false;
	}

	/**
	 * Permet de savoir si la personne est connecte au site
	 * @return: True si connecte, false sinon
	 */
	public boolean estConnectee() {
		return connecte;
	}


	// Gestion du statut ---------------------------------------------------------------------


	/**
	 * Permet de tester aupres de son statut si la personne est promouvable vers un statut plus eleve
	 * @return: True si une evolution est possible, false sinon
	 */
	public boolean estPromouvable() {
		return sonStatut.estPromouvable(this);
	}

	/**
	 * Fait evoluer le statut de la personne positivement
	 * @return: True si succes de la promotion, false si echec
	 */
	public boolean promouvoir() {
		try {
		    sonStatut = sonStatut.promouvoir(this);
		    System.out.println(this.prenom +" " + this.nom + " a ete promu au rang de "+ sonStatut);
        }
        catch(ExceptionStatut e) {
		    System.err.println("La promotion de " + nom + " a echoue");
		    return false;
        }
        return true;
	}

	/**
	 * Fait evoluer le statut de la personne vers le bas
	 * @return: True si succes, false si echec
	 */
	public boolean retrograder() {
        try {
            sonStatut = sonStatut.retrograder(this);
		    System.out.println(this.prenom +" " + this.nom + " a ete retrograde au rang de "+ sonStatut);
        }
        catch(ExceptionStatut e) {
            System.err.println("Le retrogradage de " + nom + " a echoue");
            return false;
        }
        return true;
	}

	/**
	 * Inscrit la personne aupres du magasin afin de devenir adherent
	 * @return: True si succes de l'inscription, false sinon
	 */
	public boolean inscrire() {
	 	Magasin mag = Magasin.getInstance();
		if(!mag.estTravailleur(this) && !mag.estInscrite(this) && mag.inscrire(this) )
			return promouvoir();
		return false;
	}

	/**
	 * La personne devient membre du personnel
	 * @return: True si succes, false sinon
	 */
	public boolean devenirTravailleur() {
		Magasin mag = Magasin.getInstance();
		if(!mag.estInscrite(this) && !mag.estTravailleur(this) && mag.ajouterTravailleur(this))
			return promouvoir();
		return false;
	}


	// Points fidelite ----------------------------------------------------------


	public void afficherPointFidelite() {
	    if(sesCartesFid.size() == 0)
	        System.out.println("Aucune carte de fidelite pour " + prenom + " " + nom);
		else if(!sonStatut.autorisePointsFidelite())
		    System.out.println("Le statut de " + prenom + " " + nom + " n'autorise pas l'utilisation des points de fidelite");
	    else {
            for (CarteFidelite cf : sesCartesFid)
                System.out.println(cf);
        }
	}

	/**
     * Permet d'ajouter des points de fidelites sur les cartes de fidelites de la personne a partir d'un prix
     * @param prixPaye: Prix paye par la personne
     * @return: Succes ou echec de l'ajout des points
     */
	public boolean ajouterPointFidPrix(double prixPaye) {
	    if(prixPaye <= 0)
	        return false;

		int nbPoints = CarteFidelite.nombrePointsFidGagnes(prixPaye);
		return ajouterPointsFid(nbPoints);
	}

	/**
     * Permet d'ajouter un nombre precis de points de fidelite a la personne
     * @param points: nombre de points a ajouter
     * @return: Succes ou echec de l'ajout des points
     */
    public boolean ajouterPointsFid(int points) {
        int i = 0;
        int pointsAAjouter = points;

        if(points <= 0)
            return false;

        // Creation de la toute premiere carte de fidelite
        if(sesCartesFid.size() == 0)
            sesCartesFid.add(new CarteFidelite(this));

        // Ajout des points sur les cartes existantes
        while(i < sesCartesFid.size() && pointsAAjouter != 0) {
            if(!sesCartesFid.get(i).estPleine()) {
                pointsAAjouter = sesCartesFid.get(i).ajouterPointsFid(pointsAAjouter);
                if(pointsAAjouter != 0) {
                    CarteFidelite newCarte = new CarteFidelite(this);
                    sesCartesFid.add(newCarte);
                }
            }
            i++;
        }

        return true;
    }

	/**
     * Permet d'appliquer sur un prix (dans notre projet le prix total du panier) un rabais par carte de fidelite pleine
     * @param prix: prix sur lequel appliquer le rabais
     * @return: nouveau prix apres application des rabais
     */
	public double appliquerRabaisCartesFid(double prix) {
		if(prix <= 0 || !sonStatut.autorisePointsFidelite() || !connecte)
			return prix;
		double res = prix;
		// application des rabais et destructions des cartes dont nous avons utilise le rabais
		
		for(int i =0 ; i<sesCartesFid.size();i++) {
			if(sesCartesFid.get(i).estPleine()) {
				res = sesCartesFid.get(i).appliquerRabais(res);
				sesCartesFid.remove(sesCartesFid.get(i));
				i--; // Car la suppression de l'element de la liste decale tous les elements suivants de 1 en avant
			}
		}
		return res;
	}


	// Magasin.Panier ----------------------------------------------------------


	public boolean ajouterArticle(Article article){
		return this.sonPanier.ajouterArticle(article);
	}

	/**
     * Permet de finaliser sa commande, de payer le contenu de son panier
     * @return: Prix paye par la personne
     */
	public double payerSonPanier() {
		double prixPaye = sonPanier.payer();
		sonPanier = new Panier(this);
		return prixPaye;
	}
	
	public String afficherSonPanier() {
		return ""+sonPanier;
	}


	// Redefinition ------------------------------------------------------------


	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(this == o) return true;
		else if(o instanceof Personne) {
			Personne p = (Personne) o;
			return p.nom.equals(nom) && p.prenom.equals(prenom)
					&& p.sonStatut.equals(sonStatut)
					&& p.sonPanier.equals(sonPanier)
					&& p.sesCartesFid.equals(sesCartesFid)
					&& p.connecte == connecte;
		}
		return false;
	}

	@Override
    public String toString() {
	    return prenom + " " + nom + ": " + sonStatut;
    }

	// Autres ----------------------------------------------------------


	public Statut getSonStatut() {
		return sonStatut;
	}

	public String getNom() {return this.nom;}

	public String getPrenom() {return this.prenom;}
}
