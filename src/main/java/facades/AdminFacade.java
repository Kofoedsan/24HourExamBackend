package facades;

import dtos.*;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

public class AdminFacade {

    private static EntityManagerFactory emf;
    private static AdminFacade instance;

    private AdminFacade() {
    }


    public static AdminFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AdminFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ConferenceDTO createConference(ConferenceDTO conferenceDTO) {
        EntityManager em = emf.createEntityManager();

        Conference conference = new Conference(conferenceDTO);
        em.getTransaction().begin();
        em.persist(conference);
        em.getTransaction().commit();

        return new ConferenceDTO(conference);
    }

    public TalkDTO createtalk(TalkDTO talkDTO) {
        EntityManager em = emf.createEntityManager();

        Talk talk = new Talk(talkDTO);
        em.getTransaction().begin();
        em.persist(talk);
        em.getTransaction().commit();

        return new TalkDTO(talk);

    }

    public SpeakerDTO createspeaker(SpeakerDTO speakerDTO) {
        EntityManager em = emf.createEntityManager();

        Speaker speaker = new Speaker(speakerDTO);
        em.getTransaction().begin();
        em.persist(speaker);
        em.getTransaction().commit();

        return new SpeakerDTO(speaker);


    }

    public SpeakerDTO updateTalkSpeaker(int talkId, int speakerId) {
        EntityManager em = emf.createEntityManager();
        SpeakerDTO speakerDTO = null;
        try {
            Talk talk = em.find(Talk.class, talkId);
            Speaker speaker = em.find(Speaker.class, speakerId);

            speaker.getTalkList().add(talk);
            em.getTransaction().begin();
            em.merge(speaker);
            em.getTransaction().commit();
            em.close();

            speakerDTO = new SpeakerDTO(speaker);
        } catch (Exception e) {
        }
        return speakerDTO;

    }

    public String deleteTalk(int id) {
        EntityManager em = emf.createEntityManager();

        Talk talk = em.find(Talk.class, id);
        Speaker speaker = null;
        TypedQuery<Speaker> query = em.createQuery("SELECT s FROM Speaker s where s.talkList=:talk", Speaker.class);
        query.setParameter("talk", talk);
        for (int i = 0; i < query.getResultList().size(); i++) {
            speaker = query.getResultList().get(i);
            speaker.getTalkList().remove(talk);
        }

        talk.setConference(null);
        try {
            em.getTransaction().begin();
            if (speaker != null) {
                em.merge(speaker);
            }
            em.merge(talk);
            em.remove(talk);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Talk with id: " + id + " has been deleted..";
    }
}