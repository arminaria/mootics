package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import de.tu.berlin.model.Grades;
import de.tu.berlin.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Armin on 27.11.13.
 */
public class DBController {
    private static final Logger log = LoggerFactory.getLogger(DBController.class);

    public static EntityManager em;
    private static DBController instance;
    private static EntityManagerFactory emf;

    public static DBController getInstance(){
        if(instance == null){
            instance = new DBController();
        }
        return instance;
    }

    private DBController(){
        emf = Persistence.createEntityManagerFactory("dataPU");
    };

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

    public void insertGrade(Grades g){
        User user = em.find(User.class, g.getUser().getId());

        if(user == null){
            em.persist(g.getUser());
        }else{
            g.setUser(user);
        }

        em.persist(g);
    }

    public void insert(Data d) throws InterruptedException {
        User user = em.find(User.class, d.getUser().getId());

        if(user == null){
            em.persist(d.getUser());
        }else{
            d.setUser(user);
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

    public void update(Data d) {
        em().merge(d);
    }

    public void updateLecture(Data d) {
        String url = d.getUrl();
        String lecture = d.getLecture();

        TypedQuery<Data> urlsQuery = em().createNamedQuery("Data.urls", Data.class);
        urlsQuery.setParameter("url",url);
        List<Data> sameUrlData = urlsQuery.getResultList();
        for (Data data : sameUrlData) {
            data.setLecture(lecture);
            update(data);
        }
    }

    public void updateCategory(Data d) {
        String url = d.getUrl();
        String category = d.getCategory();

        TypedQuery<Data> urlsQuery = em().createNamedQuery("Data.urls", Data.class);
        urlsQuery.setParameter("url",url);
        List<Data> sameUrlData = urlsQuery.getResultList();
        for (Data data : sameUrlData) {
            data.setCategory(category);
            update(data);
        }
    }

    public User getUser(Long userId) {
        return em().find(User.class, userId);
    }

    public void createUser(User user) {
        em().persist(user);
    }

    public List<Grades> getAllGrades() {
        return em().createQuery("select g from Grades g", Grades.class).getResultList();
    }

    public void updateGradeLecture(Grades g) {
        String name = g.getName();
        String lecture = g.getLecture();

        TypedQuery<Grades> gradesTypedQuery = em().createNamedQuery("Grade.Name", Grades.class);
        gradesTypedQuery.setParameter("name",name);
        List<Grades> resultList = gradesTypedQuery.getResultList();
        for (Grades grade : resultList) {
            grade.setLecture(lecture);
            em().merge(grade);
        }
    }

    public void updateGradeCategory(Grades g) {
        String name = g.getName();
        String category = g.getCategory();

        TypedQuery<Grades> gradesTypedQuery = em().createNamedQuery("Grade.Name", Grades.class);
        gradesTypedQuery.setParameter("name",name);
        List<Grades> resultList = gradesTypedQuery.getResultList();
        for (Grades grade : resultList) {
            grade.setCategory(category);
            em().merge(grade);
        }
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
