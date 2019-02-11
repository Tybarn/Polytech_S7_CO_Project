package Client;

public class CarteFidelite {

    // -------------------------------------- ATTRIBUTS --------------------------------------
    private int pointsFidelite;
    private Personne saPersonne;
    public static final int SEUIL = 100;
    public static final double RABAIS_PRIX = 10;

    // -------------------------------------- CONSTRUCTEURS --------------------------------------

    public CarteFidelite(Personne p) {
        this.saPersonne = p;
        this.pointsFidelite = 0;
    }

    // -------------------------------------- METHODES --------------------------------------

    // Static -----------------------------------------------------------

    public static int nombrePointsFidGagnes(double prixPaye) {return (int)((10.f/100.f) * prixPaye);}

    // Autres -------------------------------------------------------------

    /**
     * Ajoute des points a la carte actuelle
     * @param points: Points que l'on souhaite ajouter a la carte
     * @return: Nombre de points qu'il reste a placer sur une autre carte
     */
    public int ajouterPointsFid(int points) {
        if(points <= 0)
            return 0;
        if(this.pointsFidelite == SEUIL) {
            System.out.println("Le seuil de points de fidelite sur cette carte est deja atteint.");
            return 0;
        }

        int pointsRestants = points - (SEUIL - pointsFidelite);
        if(pointsRestants < 0)
            pointsRestants = 0;
        pointsFidelite += points;
        if(pointsFidelite > SEUIL) {
            System.out.println("Le seuil de points de fidelite sur cette carte vient d'etre atteint.");
            pointsFidelite = SEUIL;
        }

        return pointsRestants;
    }

    /**
     * La carte applique un rabais sur un prix
     * @param prix: Prix sur lequel appliquer le rabais
     * @return: Prix apres rabais
     */
    public double appliquerRabais(double prix) {
        if(prix < 0  || !estPleine() || prix < RABAIS_PRIX)
            return prix;
        System.out.println("Carte de fidelite utilisee");
        return (prix - RABAIS_PRIX);
    }

    public int getPointsFidelite() {return pointsFidelite;}

    public boolean estPleine() {
        return (pointsFidelite == SEUIL);
    }

    public String toString() {
        return "Nombre de points fidelite sur cette carte : " + pointsFidelite;
    }
}
