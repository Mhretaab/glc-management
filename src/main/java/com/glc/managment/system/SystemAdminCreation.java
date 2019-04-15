package com.glc.managment.system;

import com.glc.managment.member.*;
import com.glc.managment.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by mberhe on 4/15/19.
 */

@Component
public class SystemAdminCreation  implements CommandLineRunner {
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthenticationService authenticationService;

    @Value("${glc.managment.admin.email}")
    private String adminEmail;
    @Value("${glc.managment.admin.password}")
    private String adminPassword;
    @Override
    public void run(String... strings) throws Exception {
        String memberUuid = "a032e7d6-5f97-11e9-b268-d3ff825d731d";

        if(null == memberService.findMemberByUuid(memberUuid)){
            Member adminMember = new Member();
            adminMember.setGivenName("Admin");
            adminMember.setEmail(adminEmail);
            adminMember.setUuid(memberUuid);
            adminMember.setApprovalStatusEnum(ApprovalStatusEnum.MEMBER);

            MemberCredential mc = new MemberCredential();
            mc.setCredentialsType(CredentialsTypeEnum.PASSWORD);
            mc.setCredentials(authenticationService.encodePassword(adminPassword));
            mc.setMember(adminMember);

            Role role = new Role();
            role.setMember(adminMember);
            role.setMember(adminMember);
            role.setName(RoleEnum.ROLE_ADMIN);

            adminMember.setRoles(new ArrayList<Role>(){{add(role);}});
            adminMember.setCredentials(new ArrayList<MemberCredential>(){{add(mc);}});

            memberService.createMember(adminMember);
        }

    }
}
