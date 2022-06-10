package com.company;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SongRepository {
    private EManager eManager = EManager.getInstance();
    public String findByNameQuery = "Song.findByName";
    public String findAllQuery = "Song.findAll";
    public String componenta = "Song";
    public String findByPath = "Song.findByPath";
    public String findByIDQuery = "Song.findById";
    public String getMostListened="Song.getMostListened";
    public float findByNameQueryTime = 0;
    public long findByNameQueryCount = 0;

    public float getByPathQueryTime = 0;
    public long getByPathQueryCount = 0;

    public float createTime = 0;
    public long createCount = 0;

    public float findAllQueryTime = 0;
    public long findAllQueryCount = 0;

    public float getByIdTime = 0;
    public float getByIdCount = 0;

    public Song getByName(String name) {
        EntityManager em = eManager.getEM();
        long start = (new Date()).getTime();
        Song c = (Song) em.createNamedQuery(findByNameQuery).setParameter("songName", name).getSingleResult();
        findByNameQueryTime += (new Date()).getTime() - start;
        findByNameQueryCount += 1;
        em.close();
        return c;
    }

    public Song getById(int id) {
        EntityManager em = eManager.getEM();
        long start = (new Date()).getTime();
        Song c = (Song) em.createNamedQuery(findByIDQuery).setParameter("id", id).getSingleResult();
        getByIdTime = (new Date()).getTime() - start;
        getByIdCount++;
        em.close();
        return c;
    }
    public Song getByPath(String path) {
        EntityManager em = eManager.getEM();
        long start = (new Date()).getTime();
        Song c = (Song) em.createNamedQuery(findByPath).setParameter("songPath", path).setMaxResults(1).getSingleResult();
        findByNameQueryTime += (new Date()).getTime() - start;
        findByNameQueryCount += 1;
        em.close();
        return c;
    }

    public void create(Song c) {
        EntityManager em = eManager.getEM();
        em.getTransaction().begin();
        long start = (new Date()).getTime();
        em.persist(c);
        em.getTransaction().commit();
        em.close();
        createTime += (new Date()).getTime() - start;
        createCount++;
    }

    public List<Song> findAll() {
        EntityManager em = eManager.getEM();
        long start = (new Date()).getTime();
        List<Song> c = em.createNamedQuery(findAllQuery).getResultList();
        em.close();
        findAllQueryTime += (new Date()).getTime() - start;
        findAllQueryCount++;
        return c;
    }

    public List<Song> getMostListened(){
        EntityManager em = eManager.getEM();
        List<Song> listaCantece=em.createNamedQuery(findAllQuery).getResultList();
        listaCantece.sort(Song::compareTo);
        em.close();
        return listaCantece;
    }

    public void replayUpdate(Song c){
        EntityManager em=eManager.getEM();
        em.getTransaction().begin();
        Song a=em.find(Song.class,c.getId());
        c.setReplayNumber(a.replayNumber);
        c.replay();
        a.setReplayNumber(c.getReplayNumber());
        em.getTransaction().commit();
        em.close();
    }

    public void printStats() {
        System.out.println("Pentru tabelul: " + componenta);
        if (findByNameQueryCount > 0) {
            System.out.println("Timpul mediu pt find by name: " + (findByNameQueryTime / findByNameQueryCount) + "ms");
        }
        if (getByIdCount > 0) {
            System.out.println("Timpul mediu pt find by ID: " + (getByIdTime / getByIdCount) + "ms");
        }
        if (createCount > 0) {
            System.out.println("Timpul mediu pt crearea unui element: " + (createTime / createCount) + "ms");
        }
        if (findAllQueryCount > 0) {
            System.out.println("Timpul mediu pt findAll: " + (findAllQueryTime / findAllQueryCount) + "ms");
        }
    }
}
