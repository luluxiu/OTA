package com.ota.ota.model;


import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_ota_node")
@Getter @Setter
@JsonIgnoreProperties(value = {"groupName"})
public class OtaNode extends BaseModel {

	@Column(nullable = false, unique=true, length=18)
	private String startMac;
	
	@Column(nullable = false, unique=true, length=18)
	private String endMac;


	@ManyToOne(fetch = FetchType.LAZY)
	private OtaGroup groupName;

    @Column(length=128)
    private String description;
	
    @Column(nullable = false)
    private Date nodeCreatedAt;

    @Column(nullable = false)
    private Date nodeUpdatedAt;

    @PrePersist
    public void prePersist(){
    	nodeCreatedAt = nodeUpdatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate(){
    	nodeUpdatedAt = new Date();
    }
/*
    @JsonIgnore(value = true)
    public OtaGroup getGroupName() {
        return groupName;
    }

    @JsonIgnore(value = true)
    public void setGroupName(OtaGroup g) {
        groupName = g;
    }
*/
}



