package org.ensak.espace_citoyen.metier.beansManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ensak.espace_citoyen.dao.ProcedureDao;
import org.ensak.espace_citoyen.metier.beans.Etape;
import org.ensak.espace_citoyen.metier.beans.Procedure;
import org.ensak.espace_citoyen.metier.beans.ProcedureLance;
import org.ensak.espace_citoyen.metier.beans.ProcedureSave;

import java.util.ArrayList;

public class ProcedureManager {

    static  ProcedureDao procedureDao = new ProcedureDao();

    public static ArrayList<Procedure> getAllProcedures()
    {
        return procedureDao.getAllProcedures();
    }
    public static Procedure getProcedureById(int id)
    {
        return procedureDao.getProcedureById(id);
    }
    public static ArrayList<Procedure> getProceduresDivision(String idDivision)
    {
        return procedureDao.getAllProcedureDivision(Integer.valueOf(idDivision));
    }

    public static boolean saveData(ProcedureSave procedureSave) throws JsonProcessingException {
        return procedureDao.saveProcedureSave(procedureSave);
    }

    public static boolean isExistProcedure(int id)
    {
        return procedureDao.isExisteProcedure(id);
    }
    public static ArrayList<ProcedureLance> getAllProceduresLance(String cin)
    {
        return procedureDao.getAllProcedureLance(cin);
    }

    public static boolean removeProcedure(int id)
    {
        return procedureDao.removeProcedure(id);
    }

    public static boolean testJeton(String  jeton,String cin)
    {
        return procedureDao.isJeton(jeton,cin);
    }
    public static int nombreproceduresLances(String cin)
    {
        return procedureDao.nombreProcedureLancees(cin);
    }

    public static int nombreDeProceduresTermine(String cin)
    {
        return procedureDao.nombreProceduresTerminées(cin);
    }
    public static ArrayList<String> getAllEtapesProcedure(int idProcedure)
    {
        return procedureDao.getAllEtapesProcedure(idProcedure);
    }

    public static ArrayList<Etape> getEtapestraites(int idProcedure)
    {
        return procedureDao.getEtapestraites(idProcedure);
    }
    public static   boolean isEtapeActuelle(String etape)
    {
        return procedureDao.isEtapeActuelle(etape);
    }
}
