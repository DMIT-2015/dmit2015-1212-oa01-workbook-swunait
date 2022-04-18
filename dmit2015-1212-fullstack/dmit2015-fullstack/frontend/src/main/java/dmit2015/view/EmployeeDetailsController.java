package dmit2015.view;

import dmit2015.client.Employee;
import dmit2015.client.EmployeeService;

import lombok.Getter;
import lombok.Setter;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Faces;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@Named("currentEmployeeDetailsController")
@ViewScoped
public class EmployeeDetailsController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    @RestClient
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
        String authorization = _loginSession.getAuthorization();
        existingEmployee = _employeeService.findOneById(editId);
        if (existingEmployee == null) {
            Faces.redirect(Faces.getRequestURI().substring(0, Faces.getRequestURI().lastIndexOf("/")) + "/index.xhtml");
        }
    }
}