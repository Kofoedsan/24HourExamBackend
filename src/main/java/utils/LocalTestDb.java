package utils;

import entities.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class LocalTestDb {

    public static void start () throws IOException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "user", "nick@hansen.dk", 111, "test");
        User admin = new User("admin", "admin","nick@hansen.dk", 222, "test");
        User superuser = new User("su", "superuser", "nick@hansen.dk", 444, "test");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        Role superRole = new Role("superuser");

        user.addRole(userRole);
        admin.addRole(adminRole);
        superuser.addRole(superRole);
        superuser.addRole(adminRole);

        em.persist(userRole);
        em.persist(superRole);
        em.persist(adminRole);

        em.persist(user);
        em.persist(admin);
        em.persist(superuser);


        em.getTransaction().commit();
        System.out.println("Created TEST Users");
    }
}