package Client;

import java.util.ArrayList;
import Offres.Offre;
import Exceptions.ExceptionStatut;

public abstract class Statut {

	// ---------------------------------------- ATRIBUTS ----------------------------------------

	protected ArrayList<Personne> personnesAssociees;
	protected ArrayList<Offre> offres;

	// ---------------------------------------- CONSTRUCTEURS ----------------------------------------

	public Statut() {
		this.personnesAssociees = new ArrayList<>();
		this.offres = new ArrayList<>();
	}

	public Statut(ArrayList<Offre> aof) {
		this.personnesAssociees = new ArrayList<>();
		this.offres = aof;
	}

	// ---------------------------------------- METHODES ----------------------------------------

	// A definir ---------------------------------------------

	/**
	 * Permet de savoir si une personne peut etre promue a un statut superieur
	 * @param p: Client.Personne dont l'on souhaite tester si elle peut etre promue
	 * @return: True si promouvable, false sinon
	 */
	public abstract boolean estPromouvable(Personne p);

	/**
	 * Fournie le prochain statut superieur a l'actuel si celui-ci existe
	 * @param p: Client.Personne qui va changer d'etat
	 * @return: Prochain statut superieur a l'actuel
	 */
	protected abstract Statut etatSuivant(Personne p);

	/**
	 * Fournie le prochain statut inferieur a l'actuel si celui-ci existe
	 * @return: Prochain statut inferieur a l'actuel
	 */
	protected abstract Statut etatPrecedent();

	public abstract boolean estConnectable();

	public abstract boolean autorisePointsFidelite();

	@Override
	public abstract boolean equals(Object o);

	// Concrete ---------------------------------------------

	/**
	 * Fait evoluer positivement le statut d'une personne
	 * @param p: Client.Personne que l'on souhaite promouvoir
	 * @return: succes ou echec de la promotion
	 */
	final public Statut promouvoir(Personne p) throws ExceptionStatut {
		if(!estPromouvable(p))
			throw new ExceptionStatut();
		Statut nextStatus = this.etatSuivant(p);
		if(nextStatus == null)
			throw new ExceptionStatut();
		this.supprimerPersonne(p);
		nextStatus.ajouterPersonne(p);
		return nextStatus;
	}

	/**
	 * Fait evoluer negativement le statut d'une personne
	 * @param p: Client.Personne que l'on souhaite retrograder
	 * @return: succes ou echec du changement de statut
	 */
	final public Statut retrograder(Personne p) throws ExceptionStatut {
		Statut nextStatus = this.etatPrecedent();
		if (nextStatus == null)
			throw new ExceptionStatut();
		this.supprimerPersonne(p);
		nextStatus.ajouterPersonne(p);
		return nextStatus;
	}

	/**
	 * Ajoute une personne a la liste des personnes ayant ce statut
	 * @param p: Client.Personne a ajouter
	 * @return: succes ou echec de l'ajout dans la liste
	 */
	public boolean ajouterPersonne(Personne p) {
		if(personnesAssociees.contains(p))
			return false;
		return personnesAssociees.add(p);
	}

	/**
	 * Supprime une personne a la liste des personnes ayant ce statut
	 * @param p: Client.Personne a supprimer
	 * @return: succes ou echec de la suppression dans la liste
	 */
	public boolean supprimerPersonne(Personne p) {
		if(personnesAssociees.contains(p))
			return personnesAssociees.remove(p);
		return false;
	}

	// Gestion des offres propres au statut ---------------------------

	public boolean ajouterOffre(Offre o) {return this.offres.add(o);}

	public boolean supprimerOffre(Offre o) {return this.offres.remove(o);}

	public ArrayList<Offre> getOffres() {
		ArrayList<Offre> copy = new ArrayList<>();
		for(Offre o : offres)
			copy.add(o);
		return copy;
	}

	@Override
    public String toString() {
	    return getClass().getName();
    }
}
