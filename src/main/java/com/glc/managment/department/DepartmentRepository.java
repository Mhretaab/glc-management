package com.glc.managment.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mberhe on 12/15/18.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByUuid(String uuid);
    void deleteByUuid(String uuid);
}
