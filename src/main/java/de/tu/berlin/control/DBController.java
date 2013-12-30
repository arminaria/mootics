package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import de.tu.berlin.model.Material;
import de.tu.berlin.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Armin on 27.11.13.
 */
public class DBController {
    private static final Logger log = LoggerFactory.getLogger(DBController.class);

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataPU");
    public static EntityManager em;

    private EntityManager em() {
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

        em.persist(d);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        ParserImpl parser = new ParserImpl();
        List<Data> datas = parser.parseAllData(new File("C:\\Users\\Armin\\Desktop\\a.csv"));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataPU");
        EntityManager entityManager = emf.createEntityManager();


        DBController dbController = new DBController();
        dbController.start();
        for (Data data : datas) {
            dbController.insert(data);
        }
        dbController.commit();

        Thread.sleep(2000);

    }

    public List<Data> getAllData() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("d from Data d", Data.class).getResultList();
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
