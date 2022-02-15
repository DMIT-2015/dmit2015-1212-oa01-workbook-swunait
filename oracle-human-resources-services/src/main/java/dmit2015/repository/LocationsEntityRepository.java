package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.LocationsEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class LocationsEntityRepository extends AbstractJpaRepository<LocationsEntity, Short> {

    public LocationsEntityRepository() {
        super(LocationsEntity.class);
    }

}

