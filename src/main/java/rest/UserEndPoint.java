package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDTO;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import facades.UserFacade;
import utils.EMF_Creator;

@Path("user")
public class UserEndPoint {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final UserFacade userFacade = UserFacade.getUserFacade(EMF);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createUser(String p) throws Exception {
        UserDTO userDTO = GSON.fromJson(p, UserDTO.class);
        UserDTO result = userFacade.addUser(userDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @GET
    @Path("getAllUsers")
    @RolesAllowed({"admin", "superuser"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers() {
        List<UserDTO> u = userFacade.allUsers();
        return Response.ok().entity(GSON.toJson(u)).build();
    }

    @GET
    @Path("getAllUsersWithRole/{role}")
    @RolesAllowed({"admin", "superuser"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsersWithRole(@PathParam("role") String role){
        List<UserDTO> u = userFacade.allUsersWithUserRoleUser(role);
        return Response.ok().entity(GSON.toJson(u)).build();
    }

    @DELETE
    @RolesAllowed({"user", "admin", "superuser"})
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteUser(@PathParam("username") int id) {
        String result = userFacade.deleteUser(id);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

}