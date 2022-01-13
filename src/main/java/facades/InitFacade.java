package facades;

import entities.Role;
import utils.LocalTestDb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;

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

    public void InitDB() throws IOException {
        EntityManager em = emf.createEntityManager();
        try{
        LocalTestDb.main();
        } catch (IOException e) {
            System.out.println("DB already exist");
            em.close();
        }
////        Role userRole = new Role("user");
////        Role adminRole = new Role("admin");
////        Role superRole = new Role("superUser");
//        try {
//
////            em.getTransaction().begin();
////            em.persist(userRole);
////            em.persist(adminRole);
////            em.persist(superRole);
////            em.getTransaction().commit();
////            em.flush();
////            em.close();
//        } catch (Exception e) {
//            em.flush();
//            em.close();
//            System.out.println("Database already exist.");
//
//        }

    }

}
