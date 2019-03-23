package com.glc.managment.department;

import com.glc.managment.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by mberhe on 12/16/18.
 */
public interface DepartmentService {
    /**
     *
     * @param department
     * @return
     */
    Department addDepartment(Department department);

    /**
     *
     * @param department
     * @return
     */
    Department updateDepartment(Department department);

    /**
     *
     * @param department
     * @return
     */
    Boolean deleteDepartment(Department department);

    /**
     *
     * @param departmentUuid
     * @return
     */
    Boolean deleteDepartmentByUuid(String departmentUuid);

    /**
     *
     * @param departmentUuid
     * @return
     */
    Department findDepartmentByUuid(String departmentUuid);

    /**
     *
     * @return
     */
    List<Department> findAllDepartments();

    /**
     *
     * @param pageable
     * @return
     */
    Page<Department> findAllDepartments(Pageable pageable);

    Department addActivityToDepartment(String departmentUuid, Activity activity);

    List<Activity> findAllDepartmentActivities(Department department);

    List<Activity> findAllDepartmentActivities(String departmentUuid);

    List<Member> findAllDepartmentMembers(Department department);

    List<Member> findAllDepartmentMembers(String departmentUuid);
}
