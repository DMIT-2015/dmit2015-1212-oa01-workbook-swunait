package dmit2015.security;

import dmit2015.security.CallerUser;
import dmit2015.security.CallerUserRepository;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.validation.constraints.NotBlank;

@Named("currentCallerUserCreateController")
@RequestScoped
public class CallerUserCreateController {

    @Inject
    private SecurityContext _securityContext;

    @Inject
    private CallerUserRepository _calleruserRepository;

    @Getter
    private CallerUser newCallerUser = new CallerUser();

    @Getter
    @Setter
    @NotBlank(message = "A password is required")
    private String plainTextPassword;

    @Getter
    @Setter
    @Size(min = 1, message = "A user must be assigned to at minimum one role group")
    private String[] selectedGroups;

    public String onCreate() {
        String nextPage = "";
        try {
            _calleruserRepository.add(newCallerUser, plainTextPassword, selectedGroups);

            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (RuntimeException e) {
            Messages.addGlobalInfo(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful. {0}", e.getMessage());
        }
        return nextPage;
    }

}