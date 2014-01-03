package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import de.tu.berlin.model.Material;
import de.tu.berlin.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Armin on 27.11.13.
 */
public class DBController {
    private static final Logger log = LoggerFactory.getLogger(DBController.class);

    public static EntityManager em;

    private EntityManager em() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataPU");
        if (em == null) {
            em = emf.createEntityManager();
        }
        return em;
    }

    public void start() {
        em().getTransaction().begin();
    }

    public void commit() {
        em().getTransaction().commit();
    }

    public void insert(Data d) throws InterruptedException {
        User user = em.find(User.class, d.getUser().getId());

        if(user == null){
            em.persist(d.getUser());
        }else{
            d.setUser(user);
        }

        Material material = em.find(Material.class, d.getMaterial().getName());
        if(material == null){
            em.persist(d.getMaterial());
        }else {
            d.setMaterial(material);
        }

        TypedQuery<Data> namedQuery = em.createNamedQuery("Data.Match", Data.class);
        namedQuery.setParameter("action", d.getAction());
        namedQuery.setParameter("material", d.getMaterial());
        namedQuery.setParameter("time", d.getTime());
        namedQuery.setParameter("user", d.getUser());


        boolean empty = namedQuery.getResultList().isEmpty();
        if(empty){
            em.persist(d);
            log.info("insert: {}", d);
        }else {
            log.info("data already in the db");
        }
    }


    public List<Data> getAllData() {
        return em().createQuery("select d from Data d", Data.class).getResultList();
    }

  /*



    public List<Content> getAllContent(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT c from Content c",Content.class).getResultList();
    }


    public void updateNameOfContent(Content content, String newValue) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Content c = em.find(Content.class, content.getId());
        c.setName(newValue);
        em.merge(c);
        em.getTransaction().commit();
    }*/
}
