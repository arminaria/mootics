package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import de.tu.berlin.model.Material;
import de.tu.berlin.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Armin on 27.11.13.
 */
public class DBController {
    private static final Logger log = LoggerFactory.getLogger(DBController.class);

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataPU");

    public void insert(Data d) {
        EntityManager em = emf.createEntityManager();
        Data data = em.find(Data.class, d.getId());

        if (data != null) {
            log.info("{} not inserted as its already there", data.getId());
            return;
        }

        User user = em.find(User.class, d.getUser().getId());

        if(user == null){
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            log.debug("USER created: {} ", user);
        }

        Material material = em.find(Material.class, d.getMaterial().getId());

        if(material == null){
            em.getTransaction().begin();
            em.persist(material);
            em.getTransaction().commit();
            log.debug("MATERIAL created: {} ", material);
        }

        em.getTransaction().begin();
        em.persist(data);
        em.getTransaction().commit();

        log.debug("DB inserted: {}",data);
    }

    public List<Data> getAllData(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT d from Data d",Data.class).getResultList();
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
