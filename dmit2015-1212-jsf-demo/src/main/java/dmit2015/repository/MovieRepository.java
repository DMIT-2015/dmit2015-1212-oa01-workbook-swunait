package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Movie;
import dmit2015.security.RepositorySecurityInterceptor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
@Interceptors({RepositorySecurityInterceptor.class})
public class MovieRepository extends AbstractJpaRepository<Movie, Long> {

    @Inject
    private SecurityContext _securityContext;

//    @AroundInvoke
//    private Object authorize(InvocationContext ic) throws Exception {
//        String methodName = ic.getMethod().getName();
//        if (methodName.startsWith("create")) {
//            // Check if the user has been authenticated
//            if (_securityContext.getCallerPrincipal() == null) {
//                throw new RuntimeException("Access denied. Anonymonus users do not have permission to access this method.");
//            }
//            boolean hasPermission = _securityContext.isCallerInRole("Sales");
//            if (!hasPermission) {
//                throw new RuntimeException("Access denied. Your role does not allow you to access this method.");
//            }
//        }
//        return ic.proceed();
//    }



    public void create(Movie newMovie) {
//        String username = _securityContext.getCallerPrincipal().getName();
//        newMovie.setUsername(username);
        super.create(newMovie);
    }

    public List<Movie> list() {
        List<Movie> resultList = new ArrayList<>();
//        if (_securityContext.getCallerPrincipal() == null ) {
            resultList = super.list();
//        } else {
//            String username = _securityContext.getCallerPrincipal().getName();
//            resultList = getEntityManager().createQuery(
//                            "SELECT m FROM Movie m WHERE m.username = :usernameValue "
//                            , Movie.class)
//                    .setParameter("usernameValue", username)
//                    .getResultList();
//        }
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

