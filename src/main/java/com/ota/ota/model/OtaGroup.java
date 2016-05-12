package com.ota.ota.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_ota_group")
@Getter @Setter
@JsonIgnoreProperties(value = {"nodes", "hibernateLazyInitializer", "handler"})
public class OtaGroup extends BaseModel {

	@Column(nullable = false, unique=true, length=16)
	private String groupName;
	
	@Column(length=16)
	private String productModel;
	
	@Column(length=16)
	private String firmwareVersion;
	
	@Column(length=64)
	private String firmwareFileName;
	
	@Column(length=32)
	private String firmwareMd5;
	
	@Column(length=128)
	private String firmwareUrl;

    //@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupName", cascade = CascadeType.REMOVE)
    private Collection<OtaNode> nodes = new ArrayList<>();	
    
    @Column(nullable = false)
    private Date firmwareCreatedAt;

    @Column(nullable = false)
    private Date firmwareUpdatedAt;

    @PrePersist
    public void prePersist(){
    	firmwareCreatedAt = firmwareUpdatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate(){
    	firmwareUpdatedAt = new Date();
    }
/*
    @JsonIgnore(value = true)
    public Collection<OtaNode> getNodes() {
        return nodes;
    }

    @JsonIgnore(value = true)
    public void setNodes(Collection<OtaNode> o) {
        nodes = o;
    }
*/
}



