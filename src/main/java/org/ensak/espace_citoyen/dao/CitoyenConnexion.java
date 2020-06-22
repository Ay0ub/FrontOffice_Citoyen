package org.ensak.espace_citoyen.dao;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.ensak.espace_citoyen.metier.beans.Citoyen;


public class CitoyenConnexion {

    private static final DB conn = ConnexionMBD.mdbConnexion("test");
    private  DBObject dbObject;

    /**
     * cette methode permet de tester si une cin entree par un citoyen est valide
     * cest a dire si elle est ^presente dans la base de données
     * @param cin la cin entrée par le citoyen
     * @return
     */
    public boolean isValideCIN(String cin)
    {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("CIN",cin);
        DBCollection mongoCollection = conn.getCollection("citoyens");
        dbObject = mongoCollection.findOne(basicDBObject);
        if(dbObject == null)
            return false;

        return true;
    }

    /**
     * recuperation de touytes les informations d'un citoyen qui souhaite
     * se connecter
     * @return
     */
    public Citoyen dataCitoyen()
    {
        String cin = (String) dbObject.get("CIN");
        String nom = (String) dbObject.get("Nom");
        String prenom = (String) dbObject.get("Prenom");
        Citoyen citoyen = new Citoyen(cin,nom,prenom);
        return citoyen;
    }


}
