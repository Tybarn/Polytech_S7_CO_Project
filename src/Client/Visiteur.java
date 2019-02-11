package Client;

import Magasin.Magasin;

public class Visiteur extends Statut {

    // -------------------------------------------- SINGLETON --------------------------------------------

    private static Visiteur INSTANCE = null;
    public static Visiteur getInstance() {
        if(INSTANCE == null)
            INSTANCE = new Visiteur();
        return INSTANCE;
    }

    // -------------------------------------------- CONSTRUCTEUR --------------------------------------------

    private Visiteur() {
        super();
    }

    // -------------------------------------------- METHODES --------------------------------------------

    /**
     * Permet de savoir si une personne peut etre promue a un statut superieur
     * @param: Client.Personne dont l'on souhaite tester si elle peut etre promue
     * @return: True si promouvable, false sinon
     */
    public boolean estPromouvable(Personne p) {
        return ((Magasin.getInstance().estInscrite(p) && !Magasin.getInstance().estTravailleur(p))
            || (!Magasin.getInstance().estInscrite(p) && Magasin.getInstance().estTravailleur(p)));
    }

    /**
     * Fournie le prochain statut superieur a l'actuel si celui-ci existe
     * @param: Client.Personne qui va changer d'etat
     * @return: Prochain statut superieur a l'actuel, null si ne peu pas changer d'etat
     */
    protected Statut etatSuivant(Personne p) {
        if(Magasin.getInstance().estTravailleur(p))
            return Membre.getInstance();
        else if(Magasin.getInstance().estInscrite(p))
            return Adherent.getInstance();
        return null;
    }

    /**
     * Doit etre definie par toute classe heritant de Client.Statut.
     * Fournie le prochain statut inferieur a l'actuel si celui-ci existe.
     * @return: Prochain statut inferieur a l'actuel
     */
    protected Statut etatPrecedent() {
        return null;
    }

    public boolean estConnectable() {
        return false;
    }

    public boolean autorisePointsFidelite() {return false;}

    // Redefinition ------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        else if (o instanceof Visiteur) {
            Visiteur v = (Visiteur) o;
            return v.personnesAssociees.equals(personnesAssociees);
        }
        return false;
    }

}
