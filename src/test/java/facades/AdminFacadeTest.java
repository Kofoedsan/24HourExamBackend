package facades;

import dtos.*;
import entities.*;
import errorhandling.RandomError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AdminFacadeTest {

    private static EntityManagerFactory emf;
    private static AdminFacade facade;

    Speaker speaker1;
    Speaker speaker2;
    Speaker speaker3;
    Speaker speaker4;

    Talk talk1;
    Talk talk11;
    Talk talk12;
    Talk talk2;
    Talk talk3;
    Talk talk4;

    Conference conference1;
    Conference conference2;
    Conference conference3;
    Conference conference4;

    PropList prop1;
    PropList prop11;
    PropList prop12;
    PropList prop2;
    PropList prop3;
    PropList prop4;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = AdminFacade.getAdminFacade(emf);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        User u1 = new User("admin", "test");
        User u2 = new User("user", "test");
        Role userRole = new Role("user");
        Role adminsRole = new Role("admin");
        Role suRole = new Role("superuser");
        speaker1 = new Speaker("SpeakerMan1", "IT", "Male");
        speaker2 = new Speaker("SpeakerWoman2", "Finanse", "Woman");
        speaker3 = new Speaker("SpeakerMan2", "Art", "Male");
        speaker4 = new Speaker("SpeakerWoman2", "History", "Woman");


        talk1 = new Talk("New Tech Desktop", "30");
        talk11 = new Talk("New Tech Laptop", "30");
        talk12 = new Talk("New Tech Convertible", "30");
        talk2 = new Talk("New finanse", "30");
        talk3 = new Talk("New art", "30");
        talk4 = new Talk("New history", "30");


        conference1 = new Conference("Bornholm", 100, "15/01/2022", "15:30");
        conference2 = new Conference("Køge", 200, "15/01/2022", "15:30");
        conference3 = new Conference("Langeland", 300, "15/01/2022", "15:30");
        conference4 = new Conference("Copenhagen", 400, "15/01/2022", "15:30");

        prop1 = new PropList("Laptop");
        prop11 = new PropList("Desktop");
        prop12 = new PropList("Convertible");
        prop2 = new PropList("graph");
        prop3 = new PropList("painting");
        prop4 = new PropList("books");


        List<PropList> propList1 = new ArrayList<>();
        propList1.add(prop1);
        propList1.add(prop11);
        propList1.add(prop12);
        talk1.setProps(propList1);

        List<PropList> propList2 = new ArrayList<>();
        propList2.add(prop2);
        talk2.setProps(propList2);

        List<PropList> propList3 = new ArrayList<>();
        propList3.add(prop3);
        talk2.setProps(propList3);

        List<PropList> propList4 = new ArrayList<>();
        propList4.add(prop4);
        talk4.setProps(propList4);

        talk1.setConference(conference1);
        talk11.setConference(conference1);
        talk12.setConference(conference1);
        talk2.setConference(conference2);
        talk3.setConference(conference3);
        talk4.setConference(conference4);


        List<Talk> talkList1 = new ArrayList<>();
        talkList1.add(talk1);
        talkList1.add(talk11);
        talkList1.add(talk12);
        speaker1.setTalkList(talkList1);


        List<Talk> talkList2 = new ArrayList<>();
        talkList2.add(talk2);
        speaker2.setTalkList(talkList2);

        List<Talk> talkList3 = new ArrayList<>();
        talkList3.add(talk3);
        speaker3.setTalkList(talkList3);

        List<Talk> talkList4 = new ArrayList<>();
        talkList4.add(talk4);
        speaker4.setTalkList(talkList4);
        try {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE from users_roles").executeUpdate();
            em.createNativeQuery("DELETE from talk_prop_list").executeUpdate();
            em.createNativeQuery("DELETE from speaker_talkList").executeUpdate();
            em.createNamedQuery("users.deleteAllRows").executeUpdate();
            em.createNamedQuery("role.deleteAllRows").executeUpdate();
            em.createNamedQuery("speaker.deleteAllRows").executeUpdate();
            em.createNamedQuery("talk.deleteAllRows").executeUpdate();
            em.createNamedQuery("conference.deleteAllRows").executeUpdate();
            em.createNamedQuery("prop_list.deleteAllRows").executeUpdate();
            u1.addRole(adminsRole);
            u2.addRole(userRole);
            em.persist(userRole);
            em.persist(adminsRole);
            em.persist(suRole);
            em.persist(u1);
            em.persist(u2);
            em.persist(speaker1);
            em.persist(speaker2);
            em.persist(speaker3);
            em.persist(speaker4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE from users_roles").executeUpdate();
        em.createNativeQuery("DELETE from talk_prop_list").executeUpdate();
        em.createNativeQuery("DELETE from speaker_talkList").executeUpdate();
        em.createNamedQuery("users.deleteAllRows").executeUpdate();
        em.createNamedQuery("role.deleteAllRows").executeUpdate();
        em.createNamedQuery("speaker.deleteAllRows").executeUpdate();
        em.createNamedQuery("talk.deleteAllRows").executeUpdate();
        em.createNamedQuery("conference.deleteAllRows").executeUpdate();
        em.createNamedQuery("prop_list.deleteAllRows").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    void deleteTalk() throws RandomError {

        TalkDTO talkDTO = new TalkDTO(talk1);

        TalkDTO actual = facade.deleteTalk(talk1.getId());


        assertEquals(talkDTO.getDto_id(), actual.getDto_id());
    }


    @Test
    void removeProp() throws RandomError {

        TalkDTO talkDTO = new TalkDTO(talk1);
        talkDTO.getDto_propsDTOList().remove(prop1);

        TalkDTO expected = facade.removeProp(talk1.getId(), prop1.getId());

        assertEquals(talkDTO.getDto_propsDTOList(), expected.getDto_propsDTOList());

    }

    @Test
    void updateConference() throws RandomError {

        ConferenceDTO conferenceDTO = new ConferenceDTO("Danmark", 1500, "13/1/2022", "12:02");

        ConferenceDTO expected = facade.updateConference(conference1.getId(), conferenceDTO);

        assertEquals(conferenceDTO, expected);

    }

    @Test
    void createspeaker() throws RandomError {

        SpeakerDTO speakerDTO = new SpeakerDTO("StortNavn", "StorBranche", "Male");
        SpeakerDTO expected = facade.createspeaker(speakerDTO);

        assertEquals(speakerDTO.getDto_name(), expected.getDto_name());
        assertEquals(speakerDTO.getDto_gender(), expected.getDto_gender());
        assertEquals(speakerDTO.getDto_proffession(), expected.getDto_proffession());

    }

    @Test
    void createConference() throws RandomError {

        ConferenceDTO conferenceDTO = new ConferenceDTO("Lolland", 1600, "StorBranche", "Male");
        ConferenceDTO expected = facade.createConference(conferenceDTO);

        assertEquals(conferenceDTO.getDto_capacity(), expected.getDto_capacity());
        assertEquals(conferenceDTO.getDto_location(), expected.getDto_location());
        assertEquals(conferenceDTO.getDto_date(), expected.getDto_date());
        assertEquals(conferenceDTO.getDto_time(), expected.getDto_time());

    }

    @Test
    void addProp() throws RandomError {

        PropList propList = new PropList("Akvarie");

        talk3.getProps().add(propList);

        PropsDTO propsDTO = new PropsDTO(propList);

        TalkDTO expected = facade.addProp(talk3.getId(), propsDTO);

        TalkDTO actual = new TalkDTO(talk3);


        assertEquals(expected.getDto_propsDTOList().get(0).getDto_item(),actual.getDto_propsDTOList().get(0).getDto_item());

    }

    @Test
    void addConferenceToTalk() throws RandomError {

      talk4.setConference(conference2);

      TalkDTO talkDTO = new TalkDTO(talk4);


        TalkDTO expected = facade.addConferenceToTalk(talk4.getId(),conference2.getId());


        assertEquals(talkDTO,expected);

    }




}