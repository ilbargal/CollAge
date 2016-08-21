package common;

import models.Users;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
        emf.close();
        return list;
    }

    public <T> T singleQueryById(String qry, String propName,  Object id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("collageUnit");
        EntityManager em = emf.createEntityManager();

        T obj= (T)em.createNamedQuery(qry).setParameter(propName, id).getSingleResult();
        em.close();
        emf.close();
        return obj;
    }

    public <T> void Persist(T obj) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("collageUnit");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        em.refresh(obj);
        em.close();
    }

    public <T> void UpdateQuery(String updateQuery, Object... params) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("collageUnit");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Query query = em.createNativeQuery(updateQuery);

        int index = 0;
        for (Object param : params) {
            query.setParameter(index, param);
            index++;
        }
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public <T> void Merge(T obj) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("collageUnit");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public <T> T queryByParams(String qry, Object ...params) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("collageUnit");
        EntityManager em = emf.createEntityManager();

        Query q = em.createNamedQuery(qry);

        for (int i=0; i < params.length; i++) {
            q.setParameter(i+1, params[i]);
        }
        T results = (T) q.getResultList();

        em.close();
        emf.close();

        return results;
    }
}

