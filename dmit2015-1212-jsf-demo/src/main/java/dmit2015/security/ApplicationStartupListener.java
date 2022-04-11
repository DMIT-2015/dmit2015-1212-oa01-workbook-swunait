package dmit2015.security;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.logging.Logger;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ApplicationStartupListener.class.getName());

    @Inject
    CallerUserRepository callerUserRepository;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (callerUserRepository.list().size() == 0) {
            logger.info("Creating default accounts for application");
            CallerUser student01 = new CallerUser();
            student01.setUsername("stud01@dmit2015.ca");
            callerUserRepository.add(student01, "Password2015", new String[]{"STUDENT", "IT"});
            CallerUser instructor01 = new CallerUser();
            instructor01.setUsername("inst01@dmit2015.ca");
            callerUserRepository.add(instructor01, "Password2015", new String[]{"INSTRUCTOR", "Executive"});
        } else {
            logger.info("Application has user accounts");
        }
    }
}