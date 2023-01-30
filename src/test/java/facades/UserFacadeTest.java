//package facades;
//
//import dtos.UserDTO;
//import entities.Role;
//import entities.User;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import utils.EMF_Creator;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class UserFacadeTest {
//
//    private static EntityManagerFactory emf;
//    private static UserFacade facade;
//
//    User u1;
//    User u2;
//    User u3;
//    Role userRole = new Role("user");
//    Role adminRole = new Role("admin");
//    Role superUserRole = new Role("superuser");
//
//    @BeforeAll
//    public static void setUpClass() {
//        emf = EMF_Creator.createEntityManagerFactoryForTest();
//        facade = UserFacade.getUserFacade(emf);
//    }
//
//    @BeforeEach
//    void setUp() {
//        EntityManager em = emf.createEntityManager();
//        u1 = new User("admin", "test");
//        u2 = new User("user", "test");
//        u3 = new User("superuser", "test");
//
//        try {
//            em.getTransaction().begin();
//            em.createNativeQuery("DELETE from users_roles").executeUpdate();
//            em.createNamedQuery("users.deleteAllRows").executeUpdate();
//            em.createNamedQuery("role.deleteAllRows").executeUpdate();
//            u1.addRole(adminRole);
//            u2.addRole(userRole);
//            u3.addRole(superUserRole);
//            em.persist(userRole);
//            em.persist(adminRole);
//            em.persist(superUserRole);
//            em.persist(u1);
//            em.persist(u2);
//            em.persist(u3);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }
//
//    @AfterEach
//    void tearDown() {
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        em.createNativeQuery("DELETE from users_roles").executeUpdate();
//        em.createNamedQuery("users.deleteAllRows").executeUpdate();
//        em.createNamedQuery("role.deleteAllRows").executeUpdate();
//        em.getTransaction().commit();
//    }
//
//
//    @Test
//    void addUser(){
//        User newUser = new User("admin", "test");
//        UserDTO userDTO = new UserDTO(newUser);
//
//        UserDTO actual = facade.addUser(userDTO);
//
//        assertEquals(userDTO, actual);
//    }
//
//
//}