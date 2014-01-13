package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import de.tu.berlin.model.Grades;
import de.tu.berlin.model.User;
import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Armin on 27.11.13.
 */
public class DBController {
    private static final Logger log = LoggerFactory.getLogger(DBController.class);

    public static EntityManager em;
    private static DBController instance;
    private static EntityManagerFactory emf;
    private Object selfTests;

    public static DBController getInstance() {
        if (instance == null) {
            instance = new DBController();
        }
        return instance;
    }

    private DBController() {
        emf = Persistence.createEntityManagerFactory("dataPU");
    }

    ;

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

    public void insertGrade(Grades g) {
        User user = em.find(User.class, g.getUser().getId());

        if (user == null) {
            em.persist(g.getUser());
        } else {
            g.setUser(user);
        }

        em.persist(g);
    }

    public void insert(Data d) throws InterruptedException {
        User user = em.find(User.class, d.getUser().getId());

        if (user == null) {
            em.persist(d.getUser());
        } else {
            d.setUser(user);
        }

        TypedQuery<Data> namedQuery = em.createNamedQuery("Data.Match", Data.class);
        namedQuery.setParameter("action", d.getAction());
        namedQuery.setParameter("material", d.getMaterial());
        namedQuery.setParameter("time", d.getTime());
        namedQuery.setParameter("user", d.getUser());


        boolean empty = namedQuery.getResultList().isEmpty();
        if (empty) {
            em.persist(d);
            log.info("insert: {}", d);
        } else {
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
        urlsQuery.setParameter("url", url);
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
        urlsQuery.setParameter("url", url);
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
        gradesTypedQuery.setParameter("name", name);
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
        gradesTypedQuery.setParameter("name", name);
        List<Grades> resultList = gradesTypedQuery.getResultList();
        for (Grades grade : resultList) {
            grade.setCategory(category);
            em().merge(grade);
        }
    }

    public void updateViews() {
        List<Data> resultList = em().createQuery("select d from Data d where " +
                "(d.url like '%id=23986%' " +
                "or d.url like '%id=22056%'" +
                "or d.url like '%id=22054%'" +
                "or d.url like '%id=22055%'" +
                "or d.url like '%id=22009%'" +
                "or d.url like '%id=22408%'" +
                "or d.url like '%id=22419%'" +
                "or d.url like '%id=22265%'" +
                "or d.url like '%id=26310%'" +
                "or d.url like '%id=22754%'" +
                ")", Data.class).getResultList();
        for (Data data : resultList) {
            data.setLecture("1");
            this.update(data);
        }


        resultList = em().createQuery("select d from Data d where " +
                "(d.url like '%id=23775%' " +
                "or d.url like '%id=24134%'" +
                "or d.url like '%id=22654%'" +
                "or d.url like '%id=25697%'" +
                "or d.url like '%id=23572%'" +
                "or d.url like '%id=23573%'" +
                "or d.url like '%id=23574%'" +
                "or d.url like '%id=16045%'" +
                "or d.url like '%id=16046%'" +
                "or d.url like '%id=24241%'" +
                ")", Data.class).getResultList();
        for (Data data : resultList) {
            data.setLecture("2");
            this.update(data);
        }

        resultList = em().createQuery("select d from Data d where " +
                "(d.url like '%id=25064%' " +
                "or d.url like '%id=26185%'" +
                "or d.url like '%id=25978%'" +
                "or d.url like '%id=25980%'" +
                "or d.url like '%id=16036%'" +
                "or d.url like '%id=26281%'" +
                "or d.url like '%id=26430%'" +
                "or d.url like '%id=26059%'" +
                "or d.url like '%id=26308%'" +
                "or d.url like '%id=16043%'" +
                "or d.url like '%id=27133%'" +
                "or d.url like '%id=27688%'" +
                "or d.url like '%id=26149%'" +
                ")", Data.class).getResultList();
        for (Data data : resultList) {
            data.setLecture("3");
            this.update(data);
        }

        resultList = em().createQuery("select d from Data d where " +
                "(d.url like '%id=27412%' " +
                "or d.url like '%id=27577%'" +
                "or d.url like '%id=27672%'" +
                "or d.url like '%id=16028%'" +
                "or d.url like '%id=27579%'" +
                "or d.url like '%id=30197%'" +
                "or d.url like '%id=27588%'" +
                "or d.url like '%id=27601%'" +
                "or d.url like '%id=16038%'" +
                "or d.url like '%id=28512%'" +
                "or d.url like '%id=28935%'" +
                "or d.url like '%id=27693%'" +
                ")", Data.class).getResultList();
        for (Data data : resultList) {
            data.setLecture("4");
            this.update(data);
        }


        resultList = em().createQuery("select d from Data d where " +
                "(d.url like '%id=28353%' " +
                "or d.url like '%id=16020%'" +
                "or d.url like '%id=27675%'" +
                "or d.url like '%id=28827%'" +
                "or d.url like '%id=28832%'" +
                "or d.url like '%id=28834%'" +
                "or d.url like '%id=28861%'" +
                "or d.url like '%id=28863%'" +
                "or d.url like '%id=28964%'" +
                "or d.url like '%id=30194%'" +
                "or d.url like '%id=30113%'" +
                ")", Data.class).getResultList();
        for (Data data : resultList) {
            data.setLecture("5");
            this.update(data);
        }

        resultList = em().createQuery("select d from Data d where " +
                "(d.url like '%id=29841%' " +
                "or d.url like '%id=16002%'" +
                "or d.url like '%id=30696%'" +
                "or d.url like '%id=29884%'" +
                "or d.url like '%id=29885%'" +
                "or d.url like '%id=30115%'" +
                "or d.url like '%id=30116%'" +
                "or d.url like '%id=30195%'" +
                "or d.url like '%id=30117%'" +
                "or d.url like '%id=30460%'" +
                "or d.url like '%id=30118%'" +
                "or d.url like '%id=30120%'" +
                ")", Data.class).getResultList();
        for (Data data : resultList) {
            data.setLecture("6");
            this.update(data);
        }


        resultList = em().createQuery("select d from Data d where " +
                "(d.url like '%id=30945%' " +
                "or d.url like '%id=30949%'" +
                "or d.url like '%id=31520%'" +
                "or d.url like '%id=30950%'" +
                "or d.url like '%id=30951%'" +
                "or d.url like '%id=31153%'" +
                "or d.url like '%id=31155%'" +
                "or d.url like '%id=31320%'" +
                "or d.url like '%id=31199%'" +
                "or d.url like '%id=33657%'" +
                "or d.url like '%id=33656%'" +
                "or d.url like '%id=31523%'" +
                "or d.url like '%id=33007%'" +
                ")", Data.class).getResultList();
        for (Data data : resultList) {
            data.setLecture("7");
            this.update(data);
        }


        resultList = em().createQuery("select d from Data d where " +
                "(d.url like '%id=32304%' " +
                "or d.url like '%id=32307%'" +
                "or d.url like '%id=32848%'" +
                "or d.url like '%id=32308%'" +
                "or d.url like '%id=32311%'" +
                "or d.url like '%id=31827%'" +
                "or d.url like '%id=31828%'" +
                "or d.url like '%id=33659%'" +
                ")", Data.class).getResultList();
        for (Data data : resultList) {
            data.setLecture("8");
            this.update(data);
        }


        resultList = em().createQuery("select d from Data d where " +
                "(d.url like '%id=15993%' " +
                "or d.url like '%id=33385%'" +
                "or d.url like '%id=34072%'" +
                "or d.url like '%id=33382%'" +
                "or d.url like '%id=33434%'" +
                "or d.url like '%id=33435%'" +
                "or d.url like '%id=34574%'" +
                ")", Data.class).getResultList();
        for (Data data : resultList) {
            data.setLecture("9");
            this.update(data);
        }

        /**
         * 6. - 12. Januar
         */
        resultList = em().createQuery("select d from Data d where " +
                "(d.url like '%id=15990%' " +
                "or d.url like '%id=34482%'" +
                "or d.url like '%id=34926%'" +
                "or d.url like '%id=34544%'" +
                ")", Data.class).getResultList();
        for (Data data : resultList) {
            data.setLecture("10");
            this.update(data);
        }


    }

    public List<Point2D> getChartPoints(String test) {
        List<Point2D> results = new ArrayList<>();

        test = test.contains(":")? test.split(":")[1].trim() : test;
        List<User> allUser = em.createQuery("select u from User u", User.class).getResultList();
        for (User user : allUser) {
            TypedQuery<Data> dataTypedQuery = em.createQuery("Select d from Data d where d.user =:user and d.material=:testName " +
                    "ORDER BY time DESC ", Data.class).setMaxResults(1);
            dataTypedQuery.setParameter("user", user);
            dataTypedQuery.setParameter("testName", test);

            List<Data> resultList = dataTypedQuery.getResultList();

            if (resultList.isEmpty()) {
                log.debug("user {} hat den Test 1 nicht gemacht", user);
            } else {
                Data lastTest = resultList.get(0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

                TypedQuery<Data> query = em.createQuery("Select d from Data d where d.user=:user and d.lecture='1' and d.time < :latestTestTime", Data.class);
                query.setParameter("user", user);
                query.setParameter("latestTestTime", lastTest.getTime());
                List<Data> allClicks = query.getResultList();

                TypedQuery<Grades> grades = em.createQuery("Select g from Grades g where g.name like :testName and user=:user", Grades.class).setMaxResults(1);
                grades.setParameter("testName", "%" + test + "%");
                grades.setParameter("user", user);

                Long g = grades.getResultList().isEmpty() ? -1 : grades.getResultList().get(0).getGrade();
                if(g > 0){
                    results.add(new Point2D(allClicks.size(),g));
                    log.debug("user: {}, last: {}, clicks before: " + allClicks.size(), user.getId(), simpleDateFormat.format(lastTest.getTime().getTime()));
                    log.debug("\t grade:{} ", g);
                }

            }
        }
        return results;
    }

    public List<String> getSelfTests() {
        List<String> resultList = em.createQuery("select distinct g.name from Grades g where g.name like '%Self-Test Lecture%' group by g.name,g.id", String.class).getResultList();

        return resultList;
    }

    public List<User> getAllUser() {
        return em.createQuery("select u from User u").getResultList();
    }

    public List<String> getCategories() {
        return em.createQuery("select distinct d.category from Data d where d.category != 'TODO' ",String.class).getResultList();
    }

    public int getClicksOn(User user, String category) {
        TypedQuery<Data> query = em.createQuery("select d from Data d where d.user=:user and d.category=:category", Data.class);
        query.setParameter("user", user);
        query.setParameter("category", category);
        return query.getResultList().size();

    }

    public List<Grades> getGrades(User user) {
        TypedQuery<Grades> query = em.createQuery("Select g from Grades g where g.user = :user", Grades.class);
        query.setParameter("user", user);
        return query.getResultList();

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
