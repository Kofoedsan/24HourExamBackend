//package rest;
//
//import entities.User;
//import entities.Role;
//import io.restassured.RestAssured;
//import static io.restassured.RestAssured.given;
//import io.restassured.http.ContentType;
//import io.restassured.parsing.Parser;
//import java.net.URI;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.ws.rs.core.UriBuilder;
//import org.glassfish.grizzly.http.server.HttpServer;
//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
//import org.glassfish.jersey.server.ResourceConfig;
//import static org.hamcrest.Matchers.equalTo;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import utils.EMF_Creator;
//
////Disabled
//public class LoginEndpointTest
//{
//
//    private static final int SERVER_PORT = 7777;
//    private static final String SERVER_URL = "http://localhost/api";
//
//    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
//    private static HttpServer httpServer;
//    private static EntityManagerFactory emf;
//
//    static HttpServer startServer()
//    {
//        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
//        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
//    }
//
//    @BeforeAll
//    public static void setUpClass()
//    {
//        //This method must be called before you request the EntityManagerFactory
//        EMF_Creator.startREST_TestWithDB();
//        emf = EMF_Creator.createEntityManagerFactoryForTest();
//
//        httpServer = startServer();
//        //Setup RestAssured
//        RestAssured.baseURI = SERVER_URL;
//        RestAssured.port = SERVER_PORT;
//        RestAssured.defaultParser = Parser.JSON;
//    }
//
//    @AfterAll
//    public static void closeTestServer()
//    {
//        //Don't forget this, if you called its counterpart in @BeforeAll
//        EMF_Creator.endREST_TestWithDB();
//
//        httpServer.shutdownNow();
//    }
//    @BeforeEach
//    public void setUp()
//    {
//        EntityManager em = emf.createEntityManager();
//        try
//        {
//            em.getTransaction().begin();
//            em.createNativeQuery("DELETE from users_roles").executeUpdate();
//            em.createNamedQuery("users.deleteAllRows").executeUpdate();
//            em.createNamedQuery("role.deleteAllRows").executeUpdate();
//
//            Role userRole = new Role("user");
//            Role adminRole = new Role("admin");
//            User user = new User("user", "test");
//            user.addRole(userRole);
//            User admin = new User("admin", "test");
//            admin.addRole(adminRole);
//            User both = new User("user_admin", "test");
//
//
//            both.addRole(userRole);
//            both.addRole(adminRole);
//            em.persist(userRole);
//            em.persist(adminRole);
//            em.persist(user);
//            em.persist(admin);
//            em.persist(both);
//            em.getTransaction().commit();
//        } finally
//        {
//            em.close();
//        }
//    }
//
//    //Security token
//    private static String securityToken;
//
//    //Utility method to login and set the returned securityToken
//    private static void login(String role, String password)
//    {
//        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
//        securityToken = given()
//                .contentType("application/json")
//                .body(json)
//                .when().post("/login")
//                .then()
//                .extract().path("token");
//    }
//
//    private void logOut()
//    {
//        securityToken = null;
//    }
//
//    @Test
//    public void serverIsRunning()
//    {
//        given().when().get("/login/serverTest").then().statusCode(200);
//    }
//
//    @Test
//    public void testAutorizedUserCannotAccessAdminPage()
//    {
//        login("user", "test");
//        given()
//                .contentType("application/json")
//                .header("x-access-token", securityToken)
//                .when()
//                .get("/login/testAdminAccess").then() //Call Admin endpoint as user
//                .statusCode(401);
//    }
//
//    @Test
//    public void testAutorizedAdminCannotAccessUserPage()
//    {
//        login("admin", "test");
//        given()
//                .contentType("application/json")
//                .header("x-access-token", securityToken)
//                .when()
//                .get("/login/testAdminAccess").then() //Call User endpoint as Admin
//                .statusCode(200);
//    }
//
//    @Test
//    public void testRestForMultiRole1()
//    {
//        login("user_admin", "test");
//        given()
//                .contentType("application/json")
//                .accept(ContentType.JSON)
//                .header("x-access-token", securityToken)
//                .when()
//                .get("/login/serverTest").then()
//                .statusCode(200);
//    }
//
//    @Test
//    public void userNotAuthenticated()
//    {
//        logOut();
//        given()
//                .contentType("application/json")
//                .when()
//                .get("/user/getAllUsers").then()
//                .statusCode(403)
//                .body("code", equalTo(403))
//                .body("message", equalTo("Not authenticated - do login"));
//    }
//
//}
