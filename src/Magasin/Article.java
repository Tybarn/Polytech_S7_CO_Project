package Magasin;

public class Article {
	// --------------------------------------- ATTRIBUTS ---------------------------------------

	private String nom;
	private double prix;
	private Categorie categorie;

	// --------------------------------------- CONSTRUCTEURS ---------------------------------------

	public Article(String nom, double prix, Categorie categorie) {
	    if(categorie == null || nom == null || prix < 0)
	        throw new IllegalArgumentException();
		this.nom = nom;
		this.prix = prix;
		this.categorie = categorie;
		categorie.ajouterArticle(this);
	}
	
	public Article(Article a) {
		this.nom=a.nom;
		this.prix=a.prix;
		this.categorie=a.categorie;
	}

	// Pour les offres flash
	public Article() {
		this.nom="VIDE";
		this.prix=0.0;
		this.categorie= null;
	}

	// --------------------------------------- METHODES ---------------------------------------

    // Concrete ---------------------------------------------------------

	public Categorie getCategorie() {
		return categorie;
	}
	
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix=prix;
	}

	public String getNom() {return nom;}

	// Redefinition -------------------------------------------------------

    @Override
    public String toString() {
        return "" + nom +": \t" + String.format("%.2f", prix) + " euro(s)";
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (categorie == null) {
			if (other.categorie != null)
				return false;
		} else if (!categorie.equals(other.categorie))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
	
}
