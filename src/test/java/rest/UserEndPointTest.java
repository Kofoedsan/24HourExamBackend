package rest;

import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;


class UserEndPointTest
{
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

       static HttpServer startServer()
    {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    //Security token
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password)
    {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then()
                .extract().path("token");
    }

    private void logOut()
    {
        securityToken = null;
    }

    @BeforeAll
    public static void setUpClass()
    {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        User u1 = new User("admin","test");
        User u2 = new User("user","test");
        Role userRole = new Role("user");
        Role adminsRole = new Role("admin");
        try{
            em.getTransaction().begin();
            em.createNativeQuery("DELETE from users_roles").executeUpdate();
            em.createNamedQuery("users.deleteAllRows").executeUpdate();
            em.createNamedQuery("role.deleteAllRows").executeUpdate();
            u1.addRole(adminsRole);
            u2.addRole(userRole);
            em.persist(userRole);
            em.persist(adminsRole);
            em.persist(u1);
            em.persist(u2);
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
        em.createNamedQuery("users.deleteAllRows").executeUpdate();
        em.createNamedQuery("role.deleteAllRows").executeUpdate();
        em.getTransaction().commit();
    }


    @Test
    void getAllUsers() {
        login("admin", "test");
        given()
                .contentType("application/json").when()
                .header("x-access-token", securityToken)
                .get("/user/getAllUsers").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("getAllUsers", hasSize(2));
    }

}