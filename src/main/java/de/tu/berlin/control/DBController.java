package de.tu.berlin.control;

import de.tu.berlin.model.Content;
import de.tu.berlin.model.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import java.util.List;

/**
 * Created by Armin on 27.11.13.
 */
public class DBController {
    private static final Logger log = LoggerFactory.getLogger(DBController.class);
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataPU");

    public void insert(Data d) throws Exception {
        EntityManager em = emf.createEntityManager();
        Data data = em.find(Data.class, d.getId());
        if (data != null) {
            log.info("{} not inserted as its already there", data.getId());
            return;
        }
        Content cmid = d.getCmid();

        // check if the content is already in the DB
        em.getTransaction().begin();
        Content content = em.find(Content.class, cmid.getId());
        if (content == null) {
            em.persist(cmid);
            log.debug("created content {}", cmid);
        } else {
            d.setCmid(content);
        }

        em.persist(d);
        em.getTransaction().commit();
        log.debug("created data {}", d);

    }

    public List<Data> getAllData(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT d from Data d",Data.class).getResultList();
    }

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
    }
}
