package dmit2015.view;

import dmit2015.client.Employee;
import dmit2015.client.EmployeeService;

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@Named("currentEmployeeEditController")
@ViewScoped
public class EmployeeEditController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private EmployeeService _employeeService;

    @Inject
    private LoginSession _loginSession;

    @Inject
    @ManagedProperty("#{param.editId}")
    @Getter
    @Setter
    private Long editId;

    @Getter
    private Employee existingEmployee;

    @PostConstruct
    public void init() {
        if (!Faces.isPostback()) {
            if (editId != null) {
                String authorization = _loginSession.getAuthorization();
                existingEmployee = _employeeService.findOneById(editId);
                if (existingEmployee == null) {
                    Faces.redirect(Faces.getRequestURI().substring(0, Faces.getRequestURI().lastIndexOf("/")) + "/index.xhtml");
                }
            } else {
                Faces.redirect(Faces.getRequestURI().substring(0, Faces.getRequestURI().lastIndexOf("/")) + "/index.xhtml");
            }
        }
    }

    public String onSave() {
        String nextPage = "";
        try {
            String authorization = _loginSession.getAuthorization();
            _employeeService.update(editId, existingEmployee, authorization);
            Messages.addFlashGlobalInfo("Update was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Update was not successful.");
        }
        return nextPage;
    }
}