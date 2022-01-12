package facades;

import dtos.UserDTO;
import entities.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }


    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.userName = :username", User.class);
            query.setParameter("username", username);
            user = query.getSingleResult();
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public UserDTO addUser(UserDTO udto) {
        EntityManager em = emf.createEntityManager();
        UserDTO newUser;
        User user = new User(udto);
        Role userRole = new Role("user");
        user.addRole(userRole);
            newUser = new UserDTO(user);
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
        }

        return newUser;
    }

    public String deleteUser(int id) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, id);
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
        return "Brugeren med id " + user.getUserName() + " Er fjernet";
    }

    public List<UserDTO> allUsers() {
        EntityManager em = getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (int i = 0; i < query.getResultList().size(); i++) {
            UserDTO userDTO = new UserDTO(query.getResultList().get(i));
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public List<UserDTO> allUsersWithUserRoleUser(String role) {
        EntityManager em = getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (int i = 0; i < query.getResultList().size(); i++) {
            if (query.getResultList().get(i).getRoleList().get(0).getRoleName().equals(role)) {
                UserDTO userDTO = new UserDTO(query.getResultList().get(i));
                userDTOList.add(userDTO);
            }
        }
        return userDTOList;
    }

}