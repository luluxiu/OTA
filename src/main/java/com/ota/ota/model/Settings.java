package com.ota.ota.model;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A generic setting model
 *
 */
@Entity
@Table(name = "settings")
@Getter @Setter
public class Settings extends BaseModel{
    @Column(name = "_key", unique = true, nullable = false)
    private String key;

    @Lob
    @Column(name = "_value")
    private Serializable value;

}

