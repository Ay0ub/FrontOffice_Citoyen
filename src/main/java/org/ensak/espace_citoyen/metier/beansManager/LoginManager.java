package org.ensak.espace_citoyen.metier.beansManager;

import org.ensak.espace_citoyen.dao.CitoyenConnexion;
import org.ensak.espace_citoyen.metier.beans.Citoyen;

public class LoginManager {
    private static final CitoyenConnexion citoyenConnexion = new CitoyenConnexion();

    public static boolean validation(String cin)
    {
        return citoyenConnexion.isValideCIN(cin);
    }
    public  static Citoyen dataCitoyen()
    {
        return citoyenConnexion.dataCitoyen();
    }
}
