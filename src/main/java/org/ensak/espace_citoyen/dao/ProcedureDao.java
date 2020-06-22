package org.ensak.espace_citoyen.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.ensak.espace_citoyen.metier.beans.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ProcedureDao {
    private static final DB conn = ConnexionMBD.mdbConnexion("test");
    private DBCollection dbCollection = conn.getCollection("procedures");
    private DBCollection dbCollection1 = conn.getCollection("proceduresLancées");

    /**
     * methode permettant de recuperer toutes les procedures qui
     * ont été enregistrées dans la basecde données
     * @return
     */
    public ArrayList<Procedure> getAllProcedures()
    {
        ArrayList<Procedure> procedures = new ArrayList<>();

        DBCursor dbCursor = dbCollection.find();
        return getProcedures(procedures, dbCursor);
    }

    /**
     * recuperer toutes les procedures lancées par un citoyen
     * @param cin ce parametre represente la cin du citoyen
     * @return
     */

    public ArrayList<ProcedureLance> getAllProcedureLance(String cin)
    {
        ArrayList<ProcedureLance> procedureLances = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("cin",cin);
        DBCursor dbCursor = dbCollection1.find(basicDBObject);
        return  getProceduresLance(procedureLances,dbCursor);

    }

    /**
     * methode privée qui permet de  recuperer toutes les donnes d'une procedure lancée
     * presente dans la base de donnees mongodb
     * @param procedures
     * @param dbCursor
     * @return
     */

    private ArrayList<ProcedureLance> getProceduresLance(ArrayList<ProcedureLance> procedures, DBCursor dbCursor) {
        while (dbCursor.hasNext())
        {
            ProcedureLance procedure = new ProcedureLance();
            DBObject dbObject = dbCursor.next();
            int numero = (int)dbObject.get("id_procedure");
            String nom = (String) dbObject.get("nom");
            String cin = (String) dbObject.get("cin");
            String etat = (String) dbObject.get("etat");
            String date = (String) dbObject.get("date");
            procedure.setCIN(cin);
            procedure.setDateDebut(date);
            procedure.setEtat(etat);
            procedure.setId_procedure(numero);
            procedure.setNom(nom);
            procedures.add(procedure);
        }
        return procedures;
    }


    /**
     * recuperer une procedure au travers de son Id
     * @param id id de la procedure
     * @return
     */
    public Procedure getProcedureById(int id)
    {
        ArrayList<Document> documents = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("id",id);
        DBObject dbObject = dbCollection.findOne(basicDBObject);
        Procedure procedure = new Procedure();
        procedure.setNom((String) dbObject.get("nom"));
        ArrayList<DBObject> allDocuments = (ArrayList<DBObject>) dbObject.get("documents");
        for(DBObject ob : allDocuments)
        {
            Document document = new Document();
            document.setNumero(String.valueOf(ob.get("numero")));
            document.setNom((String) ob.get("nom"));
            documents.add(document);
        }

        procedure.setDocuments(documents);
        return procedure;

    }

    /**
     * recuperation de toutes les procedures d"une divsision
     * @param idDivision id de la divsision
     * @return
     */
    public ArrayList<Procedure> getAllProcedureDivision(int idDivision)
    {
        ArrayList<Procedure> procedures = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("division.id",idDivision);
        DBCursor dbCursor = dbCollection.find(basicDBObject);
        return getProcedures(procedures, dbCursor);

    }

    /**
     * methode qui permettra de pouvoir recuperer toutes les informations relatives aus
     * procedures de la divsions
     * @param procedures
     * @param dbCursor
     * @return
     */
    private ArrayList<Procedure> getProcedures(ArrayList<Procedure> procedures, DBCursor dbCursor) {
        while (dbCursor.hasNext())
        {
            Procedure procedure = new Procedure();
            DBObject dbObject = dbCursor.next();
            int numero = (int)dbObject.get("id");
            String nom = (String) dbObject.get("nom");
            DBObject division = (DBObject) dbObject.get("division");
            String nomDivision = (String) division.get("nomDivision");
            int id = (int) division.get("id");
            Division division1 = new Division(id,nomDivision);
            procedure.setNumero(numero);
            procedure.setNom(nom);
            procedure.setDivision(division1);
            procedures.add(procedure);
        }
        return procedures;
    }

    /**
     * methode permettant de pouvoir enregistrer une procedure dans la base de onnees
     * @param procedureSave objet procedure que l'on desire enregistrer
     * @return
     * @throws JsonProcessingException
     */
    public boolean saveProcedureSave(ProcedureSave procedureSave) throws JsonProcessingException {
        LocalDateTime createdAt = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String date = createdAt.format(dateTimeFormatter);
        procedureSave.setDate(date);
        procedureSave.setEtat("En attente de traitement");
        ObjectMapper objectMapper1 = new ObjectMapper();
        String json1 = objectMapper1.writeValueAsString(procedureSave);
        dataMigration(json1);
        return true;
    }


    /**
     * methode provée qui perrmet de pouvoir enregistrer des informations dans la
     * base de données
     * @param json l'l'objet JSOn a enregistrer
     */
    private void dataMigration(String json)
    {
        DBObject dbObject = (DBObject) JSON.parse(json);
        DBCollection dbCollection = conn.getCollection("proceduresLancées");
        dbCollection.insert(dbObject);
    }
    private void dataMigrationUpdate(String json1,String json2)
    {
        DBObject dbObject1 = (DBObject) JSON.parse(json1);
        DBObject dbObject2 = (DBObject) JSON.parse(json2);
        dbCollection1.update(dbObject1,dbObject2);
    }

    public ProcedureSave getSaveProcedureById(int id)
    {
        ArrayList<Document> documents = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("id_procedure",id);
        DBObject dbObject = dbCollection1.findOne(basicDBObject);
        ProcedureSave procedure = new ProcedureSave();
        procedure.setNom((String) dbObject.get("nom"));
        procedure.setCIN((String) dbObject.get("cin"));
        procedure.setId_procedure((Integer) dbObject.get("id_procedure"));
        procedure.setDate((String) dbObject.get("Date"));
        ArrayList<DBObject> allDocuments = (ArrayList<DBObject>) dbObject.get("documents");
        for(DBObject ob : allDocuments)
        {
            Document document = new Document();
            document.setNumero(String.valueOf(ob.get("numero")));
            document.setNom((String) ob.get("nom"));
            document.setUrl((String) ob.get("url"));
            documents.add(document);
        }

        procedure.setDocuments(documents);
        return procedure;

    }


    /**
     * on verifie si une procedure existe deja dans la base de données
     * @param id id d la procedure a tester
     * @return
     */
    public boolean isExisteProcedure (int id)
    {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("id_procedure",id);
        DBObject dbObject = dbCollection1.findOne(basicDBObject);
        return dbObject != null;
    }

    /**
     * supprimer une procedure dans la base de données
     * @param id
     * @return
     */
    public boolean removeProcedure(int id)
    {
        BasicDBObject document = new BasicDBObject();
        document.put("id_procedure", id);
        DBObject dbObject = dbCollection1.findOne(document);
        dbCollection1.remove(dbObject);
        return isExisteProcedure(id);

    }

    /**
     * cette methode teste si le jeton d'une procedure a deja été
     * genéré
     * @param jeton le jeton a recherché
     * @return
     */
    public boolean isJeton(String jeton, String cin)
    {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("jeton",jeton);
        basicDBObject.put("cin",cin);
        DBObject dbObject = dbCollection1.findOne(basicDBObject);
        return dbObject != null;
    }

    public int nombreProcedureLancees(String cin)
    {
        return getAllProcedureLance(cin).size();
    }

    public int nombreProceduresTerminées(String cin)
    {
        int npro =0;
        String etat = "Terminé";
        ArrayList<ProcedureLance> procedureLances;
        procedureLances=getAllProcedureLance(cin);
        for (ProcedureLance procedureLance: procedureLances)
        {
            if (procedureLance.getEtat().equals(etat))
                npro = npro + 1;
        }
        return npro;
    }

    /**
     * methode permettant de recuperer toutes les etapes d'une procedure
     * @param idProcedure
     * @return
     */
    public ArrayList<String> getAllEtapesProcedure(int idProcedure)
    {
        ArrayList<String> etapes = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("id",idProcedure);
        DBObject dbObject = dbCollection.findOne(basicDBObject);
        ArrayList<DBObject> allEtapes = (ArrayList<DBObject>) dbObject.get("etapes");
        for(DBObject et : allEtapes)
        {
            etapes.add((String) et.get("nom"));
        }
       return etapes;
    }

    public ArrayList<Etape> getEtapestraites(int idProcedure)
    {
        ArrayList<Etape> etapes = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("id_procedure",idProcedure);
        DBObject dbObject = dbCollection1.findOne(basicDBObject);
        ArrayList<DBObject> allEtapesTraites = (ArrayList<DBObject>) dbObject.get("etapes");
        for(DBObject et : allEtapesTraites)
        {
            Etape etape = new Etape();
            etape.setNom((String) et.get("nom"));
            etape.setRapport((String) et.get("rapport"));
            etapes.add(etape);
        }
       return etapes;
    }

    public  boolean isEtapeActuelle(String etape)
    {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("etapeActuelle",etape);
        DBObject dbObject = dbCollection1.findOne(basicDBObject);
        return dbObject != null;
    }
}
