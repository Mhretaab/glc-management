package com.glc.managment.department;

import com.glc.managment.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by mberhe on 12/24/18.
 */
@Entity
@Table(indexes = {
        @Index(name = "activity_idx_uuid", columnList = "uuid", unique = true)
})
public class Activity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 6880688384272953966L;

    @NotNull(message = "error.validation.activity.name.required")
    @Size(max = 50, message = "error.validation.activity.name.invalid.length")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date activityStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date activityEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("departmentId")
    private Department department;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getActivityStartDate() {
        return activityStartDate;
    }

    public void setActivityStartDate(Date activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    public Date getActivityEndDate() {
        return activityEndDate;
    }

    public void setActivityEndDate(Date activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
