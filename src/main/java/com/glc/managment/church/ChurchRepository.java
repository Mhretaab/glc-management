package com.glc.managment.church;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mberhe on 12/15/18.
 */
@Repository
public interface ChurchRepository extends JpaRepository<Church, Long> {
}
