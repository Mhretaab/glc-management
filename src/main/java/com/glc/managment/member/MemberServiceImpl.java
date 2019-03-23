package com.glc.managment.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by mberhe on 12/24/18.
 */
@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository){

        this.memberRepository = memberRepository;
    }
    @Override
    public boolean isValidPasswordFormat(String password) {
        return false;
    }

    @Override
    public boolean isValidPinFormat(int pin) {
        return false;
    }

    @Override
    public boolean isPhoneNumberInUse(String currentMemberUuid, String phoneNumber) {
        return false;
    }

    @Override
    public boolean isEmailAddressInUse(String currentMemberUuid, String emailAddress) {
        return false;
    }

    @Override
    public String getPasswordValidationRegEx() {
        return null;
    }

    @Override
    public Page<Member> findAllMembers(Pageable pageable) {
        return null;
    }

    @Override
    public Member findMemberByUuid(String memberUuid) {
        return null;
    }

    @Override
    public Member createMember(Member member) {
        return null;
    }

    @Override
    public Member updateMember(Member member) {
        return null;
    }

    @Override
    public Member updateMemberRoles(Member member, List<String> roleList) {
        return null;
    }

    @Override
    public void deleteMemberByUuid(String uuid) {

    }

    @Override
    public List<Member> findMembers(Map<String, Object> searchTerms) {
        return null;
    }

    @Override
    public Page<Member> findMembers(Map<String, Object> searchTerms, Pageable pageable) {
        return null;
    }

    @Override
    public Member findMemberByEmailAddress(String email) {
        return null;
    }

    @Override
    public Member findUserByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public void deleteRoleByUuid(String uuid) {

    }
}
