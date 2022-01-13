package rest;

import com.nimbusds.jose.shaded.json.JSONObject;
import entities.*;
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
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


class UserEndPointTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

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

    //Security token
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then()
                .extract().path("token");
    }

    private void logOut() {
        securityToken = null;
    }

    @BeforeAll
    public static void setUpClass() {
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
        User u1 = new User("admin", "test");
        User u2 = new User("user", "test");
        Role userRole = new Role("user");
        Role adminsRole = new Role("admin");
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


        conference1 = new Conference("Copenhagen", 100, "15/01/2022", "15:30");
        conference2 = new Conference("Copenhagen", 200, "15/01/2022", "15:30");
        conference3 = new Conference("Copenhagen", 300, "15/01/2022", "15:30");
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

    @Test
    void getAllConferences() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .when()
                .get("/user/GetAllSpeakers").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dto_id", hasItems(speaker1.getId(), speaker2.getId(), speaker3.getId(), speaker4.getId()))
                .body("dto_name", hasItems(speaker1.getName(), speaker2.getName(), speaker3.getName(), speaker4.getName()));
    }

    @Test
    void GetTalkBySpeakerId() {
        given()
                .contentType("application/json")
                .when()
                .get("/user/GetTalkBySpeakerId/"+speaker1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dto_id[0]", equalTo(speaker1.getTalkList().get(0).getId()))
                .body("dto_topic[0]", equalTo(speaker1.getTalkList().get(0).getTopic()))
                .body("dto_duration[0]", equalTo(speaker1.getTalkList().get(0).getduration()))
                .body("dto_capacity[0]", equalTo(speaker1.getTalkList().get(0).getConference().getcapacity()))
                .body("dto_date[0]", equalTo(speaker1.getTalkList().get(0).getConference().getDate()))
                .body("dto_time[0]", equalTo(speaker1.getTalkList().get(0).getConference().getTime()));
    }



}