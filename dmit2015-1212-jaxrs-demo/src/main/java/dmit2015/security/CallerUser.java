package dmit2015.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CallerUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Size(min = 3, max = 32, message = "Enter a username that contains {min} to {max} characters")
    @Column(length = 32, unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CallerGroup", joinColumns = {@JoinColumn(name = "username")})
    @Column(name = "groupname", nullable = false)
    private Set<String> groups = new HashSet<>();

    public String getCsvGroups() {
        return groups.stream().collect(Collectors.joining(","));
    }

    @Version
    private Integer version;

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @Column(nullable = false)
    private LocalDateTime lastModifiedDateTime;

    @PrePersist
    private void beforePersist() {
        createdDateTime = LocalDateTime.now();
        lastModifiedDateTime = createdDateTime;
    }

    @PreUpdate
    private void beforeUpdate() {
        lastModifiedDateTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CallerUser that = (CallerUser) o;
        return username != null && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}