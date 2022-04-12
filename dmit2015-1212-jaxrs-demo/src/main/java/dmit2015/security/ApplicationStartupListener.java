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
            CallerUser sales01 = new CallerUser();
            sales01.setUsername("sales01");
            callerUserRepository.add(sales01, "Password2015", new String[]{"Sales"});
            CallerUser shipping01 = new CallerUser();
            shipping01.setUsername("shipping01");
            callerUserRepository.add(shipping01, "Password2015", new String[]{"Shipping"});
        } else {
            logger.info("Application has user accounts");
        }
    }
}