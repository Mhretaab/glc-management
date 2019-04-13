package com.glc.managment.common;

import com.glc.managment.member.Member;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by mberhe on 12/4/18.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable{
    public static final String UUID_FIELD = "uuid";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigserial", name = "id", updatable = false, nullable = false)
    protected Long id;

    @Column(unique = true)
    @Size(min = 36, max = 36, message = "error.validation.entity.uuid.invalid.length")
    protected String uuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "registeredById", nullable = true, columnDefinition = "bigint")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @CreatedBy
    protected Member registeredBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "updatedById", nullable = true, columnDefinition = "bigint")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @LastModifiedBy
    protected Member updatedBy;

    @Version
    protected Long version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date dateUpdated;

    public AbstractEntity() {
    }

    public AbstractEntity(String uuid, Member registeredBy, Member updatedBy, Long version, Date dateCreated, Date dateUpdated) {
        this.uuid = uuid;
        this.registeredBy = registeredBy;
        this.updatedBy = updatedBy;
        this.version = version;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    @PrePersist
    public void preInsert(){

        if(null == uuid)
            uuid = UUID.randomUUID().toString();

        if(null == dateCreated)
            dateCreated = new Date();

        if(null == dateUpdated)
            dateUpdated = dateCreated;

    }

    @PreUpdate
    public void preUpdate() {
        if(null == dateUpdated)
            dateUpdated = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Member getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(Member registeredBy) {
        this.registeredBy = registeredBy;
    }

    public Member getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Member updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEntity that = (AbstractEntity) o;

        if (!id.equals(that.id)) return false;
        if (!uuid.equals(that.uuid)) return false;
        if (!registeredBy.equals(that.registeredBy)) return false;
        if (!updatedBy.equals(that.updatedBy)) return false;
        if (!version.equals(that.version)) return false;
        if (!dateCreated.equals(that.dateCreated)) return false;
        return dateUpdated.equals(that.dateUpdated);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + uuid.hashCode();
        result = 31 * result + registeredBy.hashCode();
        result = 31 * result + updatedBy.hashCode();
        result = 31 * result + version.hashCode();
        result = 31 * result + dateCreated.hashCode();
        result = 31 * result + dateUpdated.hashCode();
        return result;
    }
}
