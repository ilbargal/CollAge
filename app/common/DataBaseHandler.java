package common;

import models.Users;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Gal on 27-May-16.
 */
public class DataBaseHandler {

    static private DataBaseHandler instance;

    public static DataBaseHandler getInstance() {
        if (instance == null)  {
            instance = new DataBaseHandler();
        }
        return instance;
    }

    public <T>List<T> query(String qry) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("collageUnit");
        EntityManager em = emf.createEntityManager();

        List<T> list= em.createNamedQuery(qry).getResultList();

        em.close();
        return list;
    }

    public <T> T singleQueryById(String qry, String propName,  Object id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("collageUnit");
        EntityManager em = emf.createEntityManager();

        T obj= (T)em.createNamedQuery(qry).setParameter(propName, id).getSingleResult();

        em.close();
        return obj;
    }

    public <T> void Persist(T obj) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("collageUnit");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        em.close();
    }
}
