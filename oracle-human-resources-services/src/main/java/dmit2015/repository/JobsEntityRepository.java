package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.JobsEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class JobsEntityRepository extends AbstractJpaRepository<JobsEntity, String> {

    public JobsEntityRepository() {
        super(JobsEntity.class);
    }

}

