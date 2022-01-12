package facades;

import entities.Role;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class InitFacade {

    private static EntityManagerFactory emf;
    private static InitFacade instance;


    public static InitFacade getInitFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new InitFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void InitDB() {
        EntityManager em = emf.createEntityManager();

        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        Role superRole = new Role("superUser");
        try {

            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(superRole);
            em.getTransaction().commit();
            em.flush();
            em.close();
        } catch (Exception e) {
            em.flush();
            em.close();
            System.out.println("Database already exist.");

        }

    }

}
