package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.EmployeesEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class EmployeesEntityRepository extends AbstractJpaRepository<EmployeesEntity, Integer> {

    public EmployeesEntityRepository() {
        super(EmployeesEntity.class);
    }

}

