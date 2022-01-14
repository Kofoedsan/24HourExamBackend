package facades;

import dtos.*;
import entities.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import errorhandling.RandomError;
import java.util.ArrayList;
import java.util.List;

public class AdminFacade {

    private static EntityManagerFactory emf;
    private static AdminFacade instance;

    private AdminFacade() {
    }


    public static AdminFacade getAdminFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AdminFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ConferenceDTO createConference(ConferenceDTO conferenceDTO) throws RandomError {
        EntityManager em = emf.createEntityManager();

        Conference conference = new Conference(conferenceDTO);
        try {

        em.getTransaction().begin();
        em.persist(conference);
        em.getTransaction().commit();
        } catch (Exception e) {
            throw new RandomError(500, "Unable to create conference. Please contact support");
        }

        return new ConferenceDTO(conference);
    }

    public TalkDTO createtalk(TalkDTO talkDTO) throws RandomError {
        EntityManager em = emf.createEntityManager();

        Talk talk = new Talk(talkDTO);
        try {

        em.getTransaction().begin();
        em.persist(talk);
        em.getTransaction().commit();
        } catch (Exception e) {
            throw new RandomError(500, "Unable to create talk. Please contact support");
        }

        return new TalkDTO(talk);

    }

    public SpeakerDTO createspeaker(SpeakerDTO speakerDTO) throws RandomError {
        EntityManager em = emf.createEntityManager();

        Speaker speaker = new Speaker(speakerDTO);
        try {

        em.getTransaction().begin();
        em.persist(speaker);
        em.getTransaction().commit();
        } catch (Exception e) {
            throw new RandomError(500, "Unable to create person. Please contact support");
        }

        return new SpeakerDTO(speaker);


    }

    public SpeakerDTO updateTalkSpeaker(int talkId, int speakerId) throws RandomError {
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
            throw new RandomError(500, "Unable to update. Please contact support");
        }
        return speakerDTO;

    }

    public TalkDTO deleteTalk(int id) throws RandomError {
        EntityManager em = emf.createEntityManager();

        Talk talk = em.find(Talk.class, id);
        Speaker speaker = null;
        TalkDTO talkDTO = null;
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
            talkDTO = new TalkDTO(talk);
        } catch (Exception e) {
            throw new RandomError(500, "Unable to delete talk.. Please contact support");
        }
        return talkDTO;
    }

    public List<TalkDTO> getpropsfortalk(int talkId) throws RandomError {
        EntityManager em = emf.createEntityManager();
        Talk talk = em.find(Talk.class, talkId);

        List<TalkDTO> proplist = new ArrayList<>();

        try {

            for (int i = 0; i < talk.getProps().size(); i++) {
                TalkDTO talkDTO = new TalkDTO(talk.getProps().get(i));
                proplist.add(talkDTO);
            }
        } catch (Exception e) {
            throw new RandomError(500, "Unable to fetch props.. Please contact support");
        }

        return proplist;
    }

    public TalkDTO removeProp(int talkid, int propid) throws RandomError {
        EntityManager em = emf.createEntityManager();

        Talk talk = em.find(Talk.class, talkid);
        PropList propList = em.find(PropList.class, propid);

        talk.getProps().remove(propList);

        try {

        em.getTransaction().begin();
        em.merge(talk);
        em.getTransaction().commit();
        } catch (Exception e) {
            throw new RandomError(500, "Unable to remove prop.. Please contact support");
        }

        TalkDTO talkDTO = new TalkDTO(talk);

        return talkDTO;
    }

    public TalkDTO addProp(int talkid, PropsDTO prop) throws RandomError {
        EntityManager em = emf.createEntityManager();

        Talk talk = em.find(Talk.class, talkid);

        PropList propList = new PropList(prop);

        talk.getProps().add(propList);

       try{
        em.getTransaction().begin();
        em.merge(talk);
        em.getTransaction().commit();
       } catch (Exception e) {
           throw new RandomError(500, "Unable to add props.. Please contact support");
       }

        TalkDTO talkDTO = new TalkDTO(talk);

        return talkDTO;
    }

    public TalkDTO addConferenceToTalk(int talkid, int confId) throws RandomError {
        EntityManager em = emf.createEntityManager();

        Talk talk = em.find(Talk.class, talkid);
        Conference conference = em.find(Conference.class, confId);

        talk.setConference(conference);

        try{

        em.getTransaction().begin();
        em.merge(talk);
        em.getTransaction().commit();
        }catch (Exception e){
            throw new RandomError(500, "Unable to change conference info.. Please contact support");
        }

        TalkDTO talkDTO = new TalkDTO(talk);

        return talkDTO;

    }

    public ConferenceDTO updateConference(int confid, ConferenceDTO conferenceDTO) throws RandomError {

        EntityManager em = emf.createEntityManager();

        Conference conference = em.find(Conference.class, confid);

        conference.setTime(conferenceDTO.getDto_time());
        conference.setDate(conferenceDTO.getDto_date());
        conference.setcapacity(conferenceDTO.getDto_capacity());
        conference.setlocation(conferenceDTO.getDto_location());

        try {
            em.getTransaction().begin();
            em.merge(conference);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RandomError(500, "Unable to change conference info.. Please contact support");
        }

        return conferenceDTO;
    }

    public SpeakerDTO updateSpeaker(int speakerId, SpeakerDTO speakerDTO) throws RandomError {
        EntityManager em = emf.createEntityManager();

        Speaker speaker = em.find(Speaker.class, speakerId);

        speaker.setGender(speakerDTO.getDto_gender());
        speaker.setProffession(speakerDTO.getDto_proffession());
        speaker.setName(speakerDTO.getDto_name());


        try {
            em.getTransaction().begin();
            em.merge(speaker);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RandomError(500, "Unable to change speaker info.. Please contact support");
        }

        return speakerDTO;


    }
}