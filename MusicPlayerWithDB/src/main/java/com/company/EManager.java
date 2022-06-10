package com.company;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EManager {
    private static EntityManagerFactory factory = null;
    private static EManager eManager = null;

    private EManager(){
        factory = Persistence.createEntityManagerFactory("MusicPlayerWithDB_persistence_unit");
    }

    public static EManager getInstance(){
        if(eManager == null){
            eManager = new EManager();
        }
        return eManager;
    }

    public static EntityManager getEM(){
        return factory.createEntityManager();
    }
}
