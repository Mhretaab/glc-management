package com.glc.managment.member;

import com.glc.managment.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mberhe on 12/15/18.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUuid(String uuid);
    Member findByEmail(String email);
    Member findByMobilePhoneNumber(String mobilePhoneNumber);
    Member findByGivenName(String givenName);
    List<Member> findAllByDepartments(List<Department> departments);
}
