package ca.nait.dmit.ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.Schedule;
import jakarta.ejb.Schedules;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timer;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import java.util.logging.Logger;

@Singleton                // Instruct the container to create a single instance of this EJB
@Startup                // Create this EJB is created when this app starts
public class AutomaticTimersBean {        // Also known as Calendar-Based Timers

        private Logger _logger = Logger.getLogger(AutomaticTimersBean.class.getName());

        /**
         * Assuming you have define the following entries in your web.xml file
         *     <env-entry>
         *         <env-entry-name>ca.dmit2015.config.SYSADMIN_EMAIL</env-entry-name>
         *         <env-entry-type>java.lang.String</env-entry-type>
         *         <env-entry-value>yourUsername@yourEmailServer</env-entry-value>
         *     </env-entry>
         */
        @Resource(name="ca.dmit2015.config.SYSADMIN_EMAIL")
        private String mailToAddress;

        @Inject
        private EmailSessionBean mail;

        private void sendEmail(Timer timer) {
                if (!mailToAddress.isBlank()) {
                        String mailSubject = timer.getInfo().toString();
                        String mailText = String.format("You have a %s on %s %s %s, %s  ",
                                timer.getInfo().toString(),
                                timer.getSchedule().getDayOfWeek(),
                                timer.getSchedule().getMonth(),
                                timer.getSchedule().getDayOfMonth(),
                                timer.getSchedule().getYear()
                        );
                        try {
                                // mail.sendTextEmail(mailToAddress, mailSubject, mailText);
                                _logger.info("Successfully sent email to " + mailToAddress);
                        } catch (Exception e) {
                                e.printStackTrace();
                                _logger.fine("Error sending email with exception " + e.getMessage());
                        }
                }
        }

         @Schedules({
                 @Schedule(second = "0", minute ="45", hour = "11", dayOfWeek = "Mon,Wed", month = "Jan-Apr", year = "2022", info ="DMIT2015-OA01 Meeting", persistent = false),
                 @Schedule(second = "0", minute ="50", hour = "7", dayOfWeek = "Tue", month = "Jan-Apr", year = "2022", info ="DMIT2015-OA01 Meeting", persistent = false)
         })
        public void dmit2015SectionOA01ClassNotifiation(Timer timer) {
                sendEmail(timer);
        }

        @Schedule(second = "0", minute ="50", hour = "18", dayOfWeek = "Mon,Wed,Fri", month = "Jan-Apr", year = "2022", info ="DMIT2015-OE01 Meeting", persistent = false)
        public void dmit2015SectionOE01ClassNotifiation(Timer timer) {
                sendEmail(timer);
        }

//        @Schedule(second = "*", minute = "*", hour = "*", persistent = false)
//        public void helloWorld() {
//                System.out.println("Hello World from a Timer");
//        }
}