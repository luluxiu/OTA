package com.ota.ota.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import javax.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * An abstract base model class for entities
 *
**/
@MappedSuperclass
@Getter @Setter
public abstract class BaseModel implements Comparable<BaseModel>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    
    @Override
    public int compareTo(BaseModel o) {
        return this.getId().compareTo(o.getId());
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass())
            return false;

        return this.getId().equals(((BaseModel) other).getId());
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }
    
}