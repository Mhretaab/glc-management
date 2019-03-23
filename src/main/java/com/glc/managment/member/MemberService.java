package com.glc.managment.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MemberService {
    /**
     *
     * @param password
     * @return
     */
    boolean isValidPasswordFormat(String password);

    /**
     *
     * @param pin
     * @return
     */
    boolean isValidPinFormat(int pin);

    /**
     *
     * @param currentMemberUuid
     * @param phoneNumber
     * @return
     */
    boolean isPhoneNumberInUse(String currentMemberUuid, String phoneNumber);

    /**
     *
     * @param currentMemberUuid
     * @param emailAddress
     * @return
     */
    boolean isEmailAddressInUse(String currentMemberUuid, String emailAddress);

    /**
     *
     * @return
     */
    String getPasswordValidationRegEx();

    /**
     *
     * @param pageable
     * @return
     */
    Page<Member> findAllMembers(Pageable pageable);

    /**
     *
     * @param memberUuid
     * @return
     */
    Member findMemberByUuid(String memberUuid);

    /**
     *
     * @param member
     * @return
     */
    Member createMember(Member member);

    /**
     *
     * @param member
     * @return
     */
    Member updateMember(Member member);

    /**
     *
     * @param member
     * @param roleList
     * @return
     */
    Member updateMemberRoles(Member member, List<String> roleList);

    /**
     *
     * @param uuid
     */
    void deleteMemberByUuid(String uuid);

    /**
     *
     * @param searchTerms
     * @return
     */
    List<Member> findMembers(Map<String, Object> searchTerms);

    /**
     *
     * @param searchTerms
     * @param pageable
     * @return
     */
    Page<Member> findMembers(Map<String, Object> searchTerms, Pageable pageable);

    /**
     *
     * @param email
     * @return
     */
    Member findMemberByEmailAddress(String email);

    /**
     *
     * @param phoneNumber
     * @return
     */
    Member findUserByPhoneNumber(String phoneNumber);

    /**
     *
     * @param uuid
     */
    void deleteRoleByUuid(String uuid);
}