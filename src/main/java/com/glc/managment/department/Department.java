package com.glc.managment.department;

import com.glc.managment.common.AbstractEntity;
import com.glc.managment.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mberhe on 12/4/18.
 */
@Entity
@Table(indexes = {
        @Index(name = "department_idx_uuid", columnList = "uuid", unique = true)
})
public class Department extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -6531065361459386371L;

    @NotNull(message = "error.validation.department.name.required")
    @Size(max = 50, message = "error.validation.department.name.invalid.length")
    private String name;
    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    @NotNull
    @OneToOne
    private Member leader;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Member getLeader() {
        return leader;
    }

    public void setLeader(Member leader) {
        this.leader = leader;
    }
}
