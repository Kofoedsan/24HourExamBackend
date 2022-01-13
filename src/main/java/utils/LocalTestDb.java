package utils;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalTestDb {

    public static void main() throws IOException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "user", "nick@hansen.dk", 111, "test");
        User admin = new User("admin", "admin", "nick@hansen.dk", 222, "test");
        User superuser = new User("su", "superuser", "nick@hansen.dk", 444, "test");

        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        Role superRole = new Role("superuser");

        Speaker speaker1 = new Speaker("SpeakerMan1", "IT", "Male");
        Speaker speaker2 = new Speaker("SpeakerWoman2", "Finanse", "Woman");
        Speaker speaker3 = new Speaker("SpeakerMan2", "Art", "Male");
        Speaker speaker4 = new Speaker("SpeakerWoman2", "History", "Woman");


        Talk talk1 = new Talk("New Tech Desktop", "30");
        Talk talk11 = new Talk("New Tech Laptop", "30");
        Talk talk12 = new Talk("New Tech Convertible", "30");
        Talk talk2 = new Talk("New finanse", "30");
        Talk talk3 = new Talk("New art", "30");
        Talk talk4 = new Talk("New history", "30");


        Conference conference1 = new Conference("Copenhagen", 100, "15/01/2022", "15:30");
        Conference conference2 = new Conference("Copenhagen", 200, "15/01/2022", "15:30");
        Conference conference3 = new Conference("Copenhagen", 300, "15/01/2022", "15:30");
        Conference conference4 = new Conference("Copenhagen", 400, "15/01/2022", "15:30");

        PropList prop1 = new PropList("Laptop");
        PropList prop11 = new PropList("Desktop");
        PropList prop12 = new PropList("Convertible");
        PropList prop2 = new PropList("graph");
        PropList prop3 = new PropList("painting");
        PropList prop4 = new PropList("books");


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

        em.getTransaction().begin();

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

        em.persist(speaker1);
        em.persist(speaker2);
        em.persist(speaker3);
        em.persist(speaker4);

        em.getTransaction().commit();
        System.out.println("Created TEST Users");
    }
}