package facades;

import dtos.ConferenceDTO;
import dtos.SpeakerDTO;
import dtos.TalkDTO;
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

    public UserDTO addUser(UserDTO udto) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.userName = :username", User.class);
        query.setParameter("username", udto.getDto_userName());
        List<User> userList = query.getResultList();

        if(userList.size()!=0){
            throw new AuthenticationException("User already exists!");
        }
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

    public List<ConferenceDTO> getAllConferences() {
        EntityManager em = getEntityManager();
        TypedQuery<Conference> query = em.createQuery("SELECT c FROM Conference c", Conference.class);
        List<ConferenceDTO> conferenceDTOS = new ArrayList<>();
        for (int i = 0; i < query.getResultList().size(); i++) {
            ConferenceDTO conferenceDTO = new ConferenceDTO(query.getResultList().get(i));
            conferenceDTOS.add(conferenceDTO);
        }
        return conferenceDTOS;
    }

    public List<TalkDTO> getAllTalks() {
        EntityManager em = getEntityManager();
        TypedQuery<Talk> query = em.createQuery("SELECT t FROM Talk t", Talk.class);
        List<TalkDTO> talkDTOS = new ArrayList<>();
        for (int i = 0; i < query.getResultList().size(); i++) {
            TalkDTO talkDTO = new TalkDTO(query.getResultList().get(i));
            talkDTOS.add(talkDTO);
        }

        return talkDTOS;
    }

    public List<TalkDTO> getTalkByConferenceId(int ConferenceId) {
        EntityManager em = getEntityManager();
        TypedQuery<Talk> query = em.createQuery("SELECT t FROM Talk t where t.Conference.id=:ConferenceId", Talk.class);
        query.setParameter("ConferenceId", ConferenceId);
        List<TalkDTO> talkDTOS = new ArrayList<>();
        for (int i = 0; i < query.getResultList().size(); i++) {
            TalkDTO talkDTO = new TalkDTO(query.getResultList().get(i));
            talkDTOS.add(talkDTO);
        }
        return talkDTOS;
    }

    public List<TalkDTO> GetTalkBySpeakerId(int speakerId) {
        EntityManager em = getEntityManager();
        TypedQuery<Speaker> query = em.createQuery("SELECT s FROM Speaker s join Talk t where s.id=:speakerId", Speaker.class);
        query.setParameter("speakerId", speakerId);
        List<TalkDTO> talkDTOS = new ArrayList<>();
        for (int i = 0; i < query.getSingleResult().getTalkList().size(); i++) {
            TalkDTO talkDTO = new TalkDTO(query.getSingleResult().getTalkList().get(i));
            talkDTOS.add(talkDTO);
        }
        return talkDTOS;
    }

    public List<SpeakerDTO> GetAllSpeakers() {
        EntityManager em = getEntityManager();
        TypedQuery<Speaker> query = em.createQuery("SELECT s FROM Speaker s", Speaker.class);
        List<SpeakerDTO> speakerDTOS = new ArrayList<>();
        for (int i = 0; i < query.getResultList().size(); i++) {
            SpeakerDTO speakerDTO = new SpeakerDTO(query.getResultList().get(i));
            speakerDTOS.add(speakerDTO);
        }
        return speakerDTOS;
    }
}