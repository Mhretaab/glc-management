package com.glc.managment.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mberhe on 4/13/19.
 */
@Repository
public interface MemberStatusRepository extends JpaRepository<MemberStatus, Long> {
    MemberStatus findByMember(Member member);
    Page<MemberStatus> findAll(Pageable pageable);
}
