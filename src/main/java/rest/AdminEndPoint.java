package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import errorhandling.RandomError;
import facades.AdminFacade;
import utils.EMF_Creator;

@Path("admin")
public class AdminEndPoint {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final AdminFacade adminFacade = AdminFacade.getAdminFacade(EMF);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @POST
    @Path("createconference")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createconference (String conference) throws RandomError {
        ConferenceDTO conferenceDTO = GSON.fromJson(conference, ConferenceDTO.class);
        ConferenceDTO result = adminFacade.createConference(conferenceDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @POST
    @Path("createtalk")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createtalk (String talk) throws RandomError {
        TalkDTO talkDTO = GSON.fromJson(talk, TalkDTO.class);
        TalkDTO result = adminFacade.createtalk(talkDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @POST
    @Path("createspeaker")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createspeaker (String talk) throws RandomError {
        SpeakerDTO speakerDTO = GSON.fromJson(talk, SpeakerDTO.class);
        SpeakerDTO result = adminFacade.createspeaker(speakerDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @PUT
    @Path("updateTalkSpeaker/{talkId}")
    @RolesAllowed({"admin", "superuser"})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateTalkSpeaker(@PathParam("talkId") int talkId, int speakerId) throws RandomError {
        SpeakerDTO result = adminFacade.updateTalkSpeaker(talkId, speakerId);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @DELETE
//    @RolesAllowed({"admin", "superuser"})
    @Path("deleteTalk/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteUser(@PathParam("id") int id) throws RandomError {
        TalkDTO result = adminFacade.deleteTalk(id);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @GET
    @Path("getpropsfortalk/{talkId}")
    @RolesAllowed({"user","admin", "superuser"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getpropsfortalk(@PathParam("talkId") int talkId) throws RandomError {
        List<TalkDTO> u = adminFacade.getpropsfortalk(talkId);
        return Response.ok().entity(GSON.toJson(u)).build();
    }

    @PUT
    @Path("removeProp/{talkid}")
    @RolesAllowed({"admin", "superuser"})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response removeProp(@PathParam("talkid") int talkid, int propid) throws RandomError {
        TalkDTO result = adminFacade.removeProp(talkid,propid);
        return Response.ok().entity(GSON.toJson(result)).build();
    }


    @PUT
    @Path("addProp/{talkid}")
    @RolesAllowed({"admin", "superuser"})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addProp(@PathParam("talkid") int talkid, String prop) throws RandomError {
        PropsDTO propsDTO = GSON.fromJson(prop, PropsDTO.class);
        TalkDTO result = adminFacade.addProp(talkid,propsDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @PUT
    @Path("addConferenceToTalk/{talkid}")
//    @RolesAllowed({"admin", "superuser"})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addConferenceToTalk(@PathParam("talkid") int talkid, int confId) throws RandomError {
        TalkDTO result = adminFacade.addConferenceToTalk(talkid,confId);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @PUT
    @Path("updateConference/{confid}")
    @RolesAllowed({"admin", "superuser"})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateConference(@PathParam("confid") int confid, String conf) throws RandomError {
        ConferenceDTO conferenceDTO = GSON.fromJson(conf, ConferenceDTO.class);
        ConferenceDTO result = adminFacade.updateConference(confid,conferenceDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @PUT
    @Path("updateSpeaker/{speakerId}")
    @RolesAllowed({"admin", "superuser"})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateSpeaker(@PathParam("speakerId") int speakerId, String speaker) throws RandomError {
        SpeakerDTO speakerDTO = GSON.fromJson(speaker, SpeakerDTO.class);
        SpeakerDTO result = adminFacade.updateSpeaker(speakerId,speakerDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

}