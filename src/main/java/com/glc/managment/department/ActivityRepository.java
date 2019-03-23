package com.glc.managment.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mberhe on 1/1/19.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Activity findByUuid(String activityUuid);
    List<Activity> findByDepartment(Department department);
}
