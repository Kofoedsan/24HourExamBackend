package rest;

import facades.InitFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Init implements ServletContextListener {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private final InitFacade initFacade = InitFacade.getInitFacade(EMF);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            System.out.println("Setting up DB");
            initFacade.InitDB();

        } catch (Exception e) {
            System.out.println("Db already exists.. Skipping");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
//Nothing to do during shutdown..
        System.out.println("Shutdown system .. ");
}
}