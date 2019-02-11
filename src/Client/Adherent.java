package Client;

import Magasin.Magasin;

public class Adherent extends Statut {

	// -------------------------------------------- SINGLETON --------------------------------------------

	private static Adherent INSTANCE = null;
	public static Adherent getInstance() {
		if(INSTANCE == null)
			INSTANCE = new Adherent();
		return INSTANCE;
	}

	// -------------------------------------------- CONSTRUCTEURS --------------------------------------------

	private Adherent(){
		super();
	}

	// -------------------------------------------- METHODES --------------------------------------------

	// Changement de statut ------------------------------------------------
	/**
	 * Permet de savoir si une personne peut etre promue a un statut superieur
	 * @param: Client.Personne dont l'on souhaite tester si elle peut etre promue
	 * @return: False, ne peut pas etre promu, aucun statut superieur a celui-ci
	 */
	public boolean estPromouvable(Personne p) {
		return false;
	}

	/**
	 * Fournie le prochain statut superieur a l'actuel si celui-ci existe
	 * @param: Client.Personne qui va changer d'etat
	 * @return: Null, aucun etat superieur
	 */
	protected Statut etatSuivant(Personne p) {
		return null;
	}

	/**
	 * Fournie le prochain statut inferieur a l'actuel si celui-ci existe
	 * @return: Prochain statut inferieur a l'actuel
	 */
	protected Statut etatPrecedent() {
		return Visiteur.getInstance();
	}

	// Autres --------------------------------------------------------------
	
	public boolean estConnectable() {
		return true;
	}

	public boolean autorisePointsFidelite() {return true;}

	// Redefinition --------------------------------------------------------

	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(this == o) return true;
		else if(o instanceof Adherent) {
			Adherent a = (Adherent) o;
			return a.personnesAssociees.equals(personnesAssociees);
		}
		return false;
	}

	/**
	 * Supprime la personne des listes du magasin et du statut
	 * @param p: Personne a supprimer des listes
	 * @return: Succes ou echec de la suppression
	 */
	@Override
	public boolean supprimerPersonne(Personne p) {
		Magasin mag = Magasin.getInstance();
		if(mag.estInscrite(p)) {
			mag.desinscrire(p);
			super.supprimerPersonne(p);
			return true;
		}
		return false;
	}

}
