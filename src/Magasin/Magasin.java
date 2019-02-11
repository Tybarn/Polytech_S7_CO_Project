package Magasin;

import Alertes.Alerte;
import Client.Personne;
import Offres.Offre;

import java.util.ArrayList;

public class Magasin {
    // ------------------------------ INSTANCES --------------------------------------

    private static Magasin INSTANCE = null;

    public static Magasin getInstance() {
        if(INSTANCE == null)
            INSTANCE = new Magasin();
        return INSTANCE;
    }

    // ------------------------------ ATTRIBUTS --------------------------------------

    private ArrayList<Personne> personnesInscrites; // Clients inscrits dans le magasin, comme les adherents
    private ArrayList<Personne> personnesTravail; // Clients travaillant pour le magasin, comme les membres
    private ArrayList<Offre> offresEnCours;
    private ArrayList<Article> articlesDispo;
    private ArrayList<Alerte> alertesEnCours;

    // ------------------------------ CONSTRUCTEURS --------------------------------------

    public Magasin() {
        this.personnesInscrites = new ArrayList<>();
        this.personnesTravail = new ArrayList<>();
        this.offresEnCours = new ArrayList<>();
        this.articlesDispo = new ArrayList<>();
        this.alertesEnCours = new ArrayList<>();
    }

    // ------------------------------ METHODES --------------------------------------

    // Client.Personne -----------------------------------

    public boolean estInscrite(Personne p) {
        return personnesInscrites.contains(p);
    }

    /**
     * Inscrire le client dans le magasin
     * @param p: Personne a inscrire
     * @return: Succes ou echec de l'inscription
     */
    public boolean inscrire(Personne p) {
        if(!estInscrite(p) && !estTravailleur(p))
            return personnesInscrites.add(p);
        return false;
    }

    public boolean desinscrire(Personne p) {
        if(estInscrite(p))
            return personnesInscrites.remove(p);
        return false;
    }

    public boolean estTravailleur(Personne p) {
        return personnesTravail.contains(p);
    }

    /**
     * Ajoute une personne en tant qu'employe du magasin
     * @param p: Personne devenant employe
     * @return: Succes ou echec de l'ajout
     */
    public boolean ajouterTravailleur(Personne p) {
        if(!estTravailleur(p) && !estInscrite(p))
            return personnesTravail.add(p);
        return false;
    }

    public boolean supprimerTravailleur(Personne p) {
        if(estTravailleur(p))
            return personnesTravail.add(p);
        return false;
    }

    // Offres ----------------------------------------------

    public boolean ajouterOffre(Offre o) {return this.offresEnCours.add(o);}

    public boolean supprimerOffre(Offre o) {return this.offresEnCours.remove(o);}

    public ArrayList<Offre> getOffresEnCours() {
        ArrayList<Offre> copy = new ArrayList<>();
        for(Offre o : this.offresEnCours)
            copy.add(o);
        return copy;
    }

    // Articles ---------------------------------------------

    public boolean ajouterArticle(Article a) {return articlesDispo.add(a);}

    public boolean supprimerArticle(Article a) {return articlesDispo.remove(a);}

    public ArrayList<Article> getArticles() {
        ArrayList<Article> copy = new ArrayList<>();
        for(Article a : articlesDispo)
            copy.add(a);
        return copy;
    }

    // Alertes ----------------------------------------------

    public boolean ajouterAlerte(Alerte a) {return alertesEnCours.add(a);}

    public boolean supprimerAlerte(Alerte a) {return alertesEnCours.remove(a);}

    public ArrayList<Alerte> getAlertes() {
        ArrayList<Alerte> copy = new ArrayList<>();
        for(Alerte a : alertesEnCours)
            copy.add(a);
        return copy;
    }

}
