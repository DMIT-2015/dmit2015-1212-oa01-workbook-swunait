package ca.nait.dmit.ejb;
import ca.nait.dmit.entity.EnforcementZoneCentre;
import ca.nait.dmit.repository.EnforcementZoneCentreRepository;
import jakarta.annotation.Resource;
import jakarta.batch.operations.JobOperator;
import jakarta.batch.runtime.BatchRuntime;
import jakarta.batch.runtime.BatchStatus;
import jakarta.batch.runtime.JobExecution;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.logging.Logger;

@Singleton                // Instruct the container to create a single instance of this EJB
@Startup                // Create this EJB is created when this app starts
public class DemoTimersBean {        // Also known as Calendar-Based Timers

        private Logger _logger = Logger.getLogger(DemoTimersBean.class.getName());

        /**
         * Assuming you have define the following entries in your META-INF/microprofile-config.properties file
         ca.dmit2015.config.SYSADMIN_EMAIL=yourUsername@yourEmailServer

         * This code assumes that this project is configured to use Eclipse Microprofile.
         * You can add the following to pom.xml to enable Eclipse Microprofile

         <dependency>
                 <groupId>org.eclipse.microprofile</groupId>
                 <artifactId>microprofile</artifactId>
                 <version>5.0</version>
                 <type>pom</type>
                 <scope>provided</scope>
         </dependency>

         */
        @Inject
        @ConfigProperty(name="ca.dmit2015.config.SYSADMIN_EMAIL")
        private String mailToAddress;

        @Inject
        private EmailSessionBean mail;

        private void sendEmail(Timer timer) {
                if (!mailToAddress.isBlank()) {
                        String mailSubject = timer.getInfo().toString();
                        String mailText = String.format("You have a %s on %s %s, %s  ",
                                timer.getInfo().toString(),
                                timer.getSchedule().getDayOfWeek(),
                                timer.getSchedule().getMonth(),
                                timer.getSchedule().getYear()
                        );
                        try {
                                mail.sendTextEmail(mailToAddress, mailSubject, mailText);
                                _logger.info("Successfully sent email to " + mailToAddress);
                        } catch (Exception e) {
                                e.printStackTrace();
                                _logger.fine("Error sending email with exception " + e.getMessage());
                        }
                }
        }

         @Schedules({
                 @Schedule(second = "0", minute ="0", hour = "11", dayOfWeek = "Mon,Wed", month = "Jan-Apr", year = "2022", info ="DMIT2015-OA01 Meeting", persistent = false),
                 @Schedule(second = "0", minute ="50", hour = "7", dayOfWeek = "Tue", month = "Jan-Apr", year = "2022", info ="DMIT2015-OA01 Meeting", persistent = false)
         })
        public void dmit2015SectionOA01ClassNotifiation(Timer timer) {
                sendEmail(timer);

                // Start the Batch Job
                 JobOperator jobOperator = BatchRuntime.getJobOperator();
                 long jobId = jobOperator.start(jobXmlFile, null);
                 // Create a interval timer that starts in 1 seconds and repeats every 10 seconds and passed in the jobId to the timer
                 timerService.createTimer(1000, 10000, jobId);

        }

        // @Schedule(second = "0", minute ="50", hour = "18", dayOfWeek = "Mon,Wed,Fri", month = "Jan-Apr", year = "2022", info ="DMIT2015-OE01 Meeting", persistent = false)
        public void dmit2015SectionOE01ClassNotifiation(Timer timer) {
                sendEmail(timer);
        }

        @Inject
        @ConfigProperty(name = "enforcement.batchxml")
        String jobXmlFile;

        @Resource
        TimerService timerService;

        @Inject
        EnforcementZoneCentreRepository enforcementZoneCentreRepository;

        @Timeout
        public void checkBatchJobStatus(Timer timer) {
                // Extract the jobId from the timer
                long jobId = (long) timer.getInfo();
                JobOperator jobOperator = BatchRuntime.getJobOperator();
                JobExecution jobExecution = jobOperator.getJobExecution(jobId);
                if (jobExecution.getBatchStatus() == BatchStatus.COMPLETED) {
                        timer.cancel();
                        // send email to notified batch job has completed
                        List<EnforcementZoneCentre> entities = enforcementZoneCentreRepository.list();

                } else if (jobExecution.getBatchStatus() == BatchStatus.FAILED) {
                        // send email to notified batch job has failed
                        timer.cancel();
                }
        }

}