package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class MovieRepository extends AbstractJpaRepository<Movie, Long> {

    @Inject
    private SecurityContext _securityContext;

    public void create(Movie newMovie) {
        String username = _securityContext.getCallerPrincipal().getName();
        newMovie.setUsername(username);
        super.create(newMovie);
    }

    public List<Movie> list() {
        List<Movie> resultList = new ArrayList<>();
        if (_securityContext.getCallerPrincipal() == null ) {
            resultList = super.list();
        } else {
            String username = _securityContext.getCallerPrincipal().getName();
            resultList = getEntityManager().createQuery(
                            "SELECT m FROM Movie m WHERE m.username = :usernameValue "
                            , Movie.class)
                    .setParameter("usernameValue", username)
                    .getResultList();
        }
        return resultList;
    }

    public MovieRepository() {
        super(Movie.class);
    }

    public List<Movie> listByReleaseDateDescending() {
        return getEntityManager()
                .createQuery("""
                SELECT m
                FROM Movie m
                ORDER BY m.releaseDate DESC
                """, Movie.class)
                .getResultList();
    }

}

