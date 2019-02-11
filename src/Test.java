import Alertes.*;
import Client.*;
import Exceptions.ExceptionCategorieNonPromo;
import Magasin.*;
import Offres.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) {

		// On cree le magasin
		Magasin magasin = Magasin.getInstance() ;
		
		//Test Creation de Categorie
		Categorie livre = new Categorie("Livre", false) ;	// livre n est pas promotionnable
		Categorie hightech = new Categorie("HighTech");	// high-tech est promotionnable
		Categorie nourriture = new Categorie("Nourriture") ; // nourriture est promotionnable
		Categorie billetterie = new Categorie("Billetterie") ; // billeterie est promotionnable
		System.out.println("\nVoici des exemples de categorie:");
		System.out.println(livre);
		System.out.println(nourriture);
		
		//Test Creation d Alerte
		//Creer une alerte qui signale quand le prix de tous les articles d un panier est superieur a 100
		Magasin.getInstance().ajouterAlerte(new AlertePrixPanierSupNb (100));
		//Creer une alerte qui surveille si les paniers ont deux articles de la categorie Livre
		Magasin.getInstance().ajouterAlerte(new AlerteNbArticlesMemeCat(livre,2));
		
		//Test Creation d Article
		Article spectacleHumour = new Article("Spectacle humouristique",20.0,billetterie);
		Article pomme = new Article("Pomme",5.0,nourriture) ;
		Article vin = new Article("Vin",25.0,nourriture) ;
		Article caviar = new Article("Caviar",100.0,nourriture) ;
		Article foieGras = new Article("Foie gras",40.0,nourriture) ;
		Article mp3 = new Article("Mp3",50.0,hightech) ;
		Article telephone = new Article("Telephone", 300.0, hightech);
		Article CD = new Article("CD",5.0,hightech) ;
		Article magazine = new Article("Magazine",3.99,livre) ;
		Article BD = new Article("BD",10.0,livre) ;
		Article BDCollector = new Article("BD Collector",499.99,livre) ;
		Article le_petit_prince = new Article("le petit prince", 10.0, livre);
		Article les_miserables = new Article("les miserables", 15.0, livre);
		Article ecran = new Article("ecran",40.99, hightech);
		System.out.println("\nVoici des exemples d articles:");
		System.out.println(pomme);
		System.out.println(telephone);
		System.out.println(BDCollector);


		//Test Offre
        try {
			//Creation d offre sur une categorie : 20% de reduction sur la categorie high-tech
			magasin.ajouterOffre(new OffreCategorie(hightech, 20));
			//Creation d offre sur un produit : 10% de reduction sur un ecran
			magasin.ajouterOffre(new OffreProduit(ecran, 10));
			//Creation d offre flash sur plusieurs produits : un ticket achete, un ticket offert
			magasin.ajouterOffre(new OffreFlash(new ArrayList<Article>(Arrays.asList(spectacleHumour, spectacleHumour)),
	                new ArrayList<Integer>(Arrays.asList(0,100))));
			//Creation d offre flash sur plusieurs produits : deux pommes achetes, une offert
			magasin.ajouterOffre(new OffreFlash(new ArrayList<Article>(Arrays.asList(pomme, pomme, vin)),
	                new ArrayList<Integer>(Arrays.asList(0,0,100))));
			//Creation d'offre pour statut  Membre , le mp3 aura 30% de reduction
			Membre.getInstance().ajouterOffre(new OffreProduit(mp3, 30));
			//Creation d'offre pour statut  Adherent , la categorie Nourriture aura 10% de reduction
			Adherent.getInstance().ajouterOffre(new OffreCategorie(nourriture, 10));
        }
        catch (ExceptionCategorieNonPromo e) {
            System.err.println("Arret de la creation des offres, du a la creation d'une offre portant sur une categorie" +
                    " (ou un produit appartenant a une categorie) non promouvable. Toutes les offres n'ont pas pu etre crees.");
        }
        catch (Exception e) {
            System.err.println("Une erreur est survenue durant la creation des offres. Toutes n'ont pas pu etre crees.");
        }

		        
		//Test Creation de Personne avec Statut
		//Afficher les clients
		Personne jean = new Personne("Jean","Martin", Visiteur.getInstance());
		Personne paul = new Personne("Paul","Pierre", Membre.getInstance());
		Personne laura = new Personne("Laura","Claire", Adherent.getInstance());
		Personne camille = new Personne("Camille","Noel", Adherent.getInstance());
		System.out.println("\nVoici les clients:");
		System.out.println(jean);
		System.out.println(paul);
		System.out.println(laura);
		System.out.println(camille);
		
		System.out.println("\nOn change les statuts des clients:");
		//Test de changement de Statuts
		paul.retrograder();	// on retrograde Paul en visiteur
		laura.retrograder(); 	// on retrograde Laura en visiteur
        jean.devenirTravailleur();		// Met a jour son statut en tant que membre
		paul.inscrire();		// Met a jour son statut en tant que adherent

		System.out.println("\nVoici les clients avec les nouveaux statuts:");
		System.out.println(jean);
		System.out.println(paul);
		System.out.println(laura);
		System.out.println(camille);
		
		
		// Jean (Membre et connecte)
		// Ajout d article dans le panier de Jean
		jean.ajouterArticle(le_petit_prince);	// 10 euros (prix du produit)
		jean.ajouterArticle(mp3);
		// 50 euros (prix du produit) -20% (offre categorie) -30% (offre membre) = 28 euros
		jean.ajouterArticle(magazine);		// 3.99 euros (prix du produit)
		jean.ajouterArticle(caviar);		// 100 euros(prix du produit)
		jean.ajouterArticle(telephone) ;	// 300 euros(prix du produit) -20% (offre categorie) = 240
		
		// Passage en caisse de jean
		System.out.println("\nJean :");
		jean.seConnecter() ; // doit se connecter pour profiter des offres membres
		jean.payerSonPanier() ;
		//Alerte declenche : alerte2Livre, alertePrixTotal
		// Cout du panier avant reduction : 10+3.99+100+50+300 = 463.99 euros
		// Cout du panier aprÃ¨s reduction : 10+3.99+100+28+270 = 381.99 euros

		// Paul (Adherent et connecte)
		//Ajout d article dans le panier de Paul
		paul.ajouterArticle(mp3);	// 50 (prix du produit) -20% (offre categorie) = 40 euros
		paul.ajouterArticle(ecran);	// 40.99 (prix du produit) -20% (offre categorie) -10% (offre produit) = 29.512 euros
		paul.ajouterArticle(spectacleHumour);	// 20 ( prix produit)
		paul.ajouterArticle(spectacleHumour);	// 20 (prix produit) -100% (offre flash) = 0
		paul.ajouterArticle(foieGras) ;		// 40 (prix produit) -10%(offre Adherent) = 36 euros
		
		System.out.println("\nPaul :");
		paul.seConnecter() ; // doit se connecter pour profiter des offres adherents et des cartes fidelites
		//Pour le test des cartes de fidelites nous devons remplir une des cartes de fidelite de paul
		paul.ajouterPointsFid(215) ; // on donne 215 points a paul, donc deux cartes a 100 pts et une a 15 pts
		// Passage en caisse de Paul
		paul.payerSonPanier() ;
		//Alerte declenche : alertePrixTotal
		// Cout du panier avant reduction : 50+40.99+20+20+40 = 170.99 euros
		// Cout du panier apres reduction : 40+29.512+20+0+36-10*2 (carte reduction plein) = 105.512 euros
		// la reduction de la carte a ete effectue donc il reste 15 points + 105.51*10% = 15 + 10 = 25 pts
		paul.afficherPointFidelite() ; // doit afficher 25 pts

		
		
		
		// Laura (Visiteur)
		// Ajout d article dans le panier de Laura
		laura.ajouterArticle(pomme);	// 5 euros (prix produit)
		laura.ajouterArticle(pomme);	// 5 euros (prix produit)
		laura.ajouterArticle(vin);	// 25 euros (prix produit) -25 (offre flash)
		laura.ajouterArticle(vin);	// 25 euros (prix produit) -25 (offre flash)
		laura.ajouterArticle(pomme); 	// 5 euros (prix produit)
		laura.ajouterArticle(pomme);	// 5 euros (prix produit) 
		
		System.out.println("\nLaura :");
		laura.seConnecter(); // doit afficher qu elle ne peut pas se connecter car elle n est pas adherent ou membre
		// Passage en caisse de Laura
		laura.payerSonPanier() ; 
		// Cout du panier avant reduction : 5+5+25+25+5+5 = 70 euros
	    // Cout du panier apres reduction : 5+5+0+0+5+5 = 20 euros
				
		
		// Camille (Adherent et non connecte)
		// Ajout d article dans le panier de Laura
		camille.ajouterArticle(pomme);	// 5 euros (prix produit)
		camille.ajouterArticle(pomme);	// 5 euros (prix produit)
		camille.ajouterArticle(vin);	// 25 euros (prix produit) -25 (offre flash)
		camille.ajouterArticle(vin);	// 25 euros (prix produit) -25 (offre flash)
		camille.ajouterArticle(pomme); 	// 5 euros (prix produit)
		camille.ajouterArticle(pomme);	// 5 euros (prix produit) 
		
		System.out.println("\nCamille :");
		// Passage en caisse de camille
		camille.payerSonPanier() ; 
		// Cout du panier avant reduction : 5+5+25+25+5+5 = 70 euros
	    // Cout du panier apres reduction : 5+5+0+0+5+5 = 20 euros
		// ATTENTION: ici Camille ne beneficie pas de la reduction de 10% sur la nourriture 
		// que lui permettrait d'avoir son statut car elle ne s est pas connecter
		// ainsi elle ne beneficie pas de l offre, on le remarque car elle paye autant que laura (visiteur)
		// Elle ne gagne pas non plus de point de fidelité
		
	}
}