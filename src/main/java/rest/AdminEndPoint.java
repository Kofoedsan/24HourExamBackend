package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import facades.AdminFacade;
import facades.UserFacade;
import utils.EMF_Creator;

@Path("admin")
public class AdminEndPoint {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final AdminFacade adminFacade = AdminFacade.getUserFacade(EMF);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @POST
    @Path("createconference")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createconference (String conference) {
        ConferenceDTO conferenceDTO = GSON.fromJson(conference, ConferenceDTO.class);
        ConferenceDTO result = adminFacade.createConference(conferenceDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @POST
    @Path("createtalk")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createtalk (String talk) {
        TalkDTO talkDTO = GSON.fromJson(talk, TalkDTO.class);
        TalkDTO result = adminFacade.createtalk(talkDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @POST
    @Path("createspeaker")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createspeaker (String talk) {
        SpeakerDTO speakerDTO = GSON.fromJson(talk, SpeakerDTO.class);
        SpeakerDTO result = adminFacade.createspeaker(speakerDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @PUT
    @Path("updateTalkSpeaker/{talkId}")
//    @RolesAllowed({"admin", "superuser"})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateTalkSpeaker(@PathParam("talkId") int talkId, int speakerId) {
        SpeakerDTO result = adminFacade.updateTalkSpeaker(talkId, speakerId);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @DELETE
//    @RolesAllowed({"admin", "superuser"})
    @Path("deleteTalk/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteUser(@PathParam("id") int id) {
        String result = adminFacade.deleteTalk(id);
        return Response.ok().entity(GSON.toJson(result)).build();
    }


}