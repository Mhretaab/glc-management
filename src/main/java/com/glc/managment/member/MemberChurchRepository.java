package com.glc.managment.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mberhe on 12/15/18.
 */
@Repository
public interface MemberChurchRepository extends JpaRepository<MemberChurch, Long> {
}
