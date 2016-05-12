package com.ota.ota.repository;

import com.ota.ota.model.OtaGroup;
import com.ota.ota.model.OtaNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface OtaGroupRepository extends JpaRepository<OtaGroup, Long> {
	OtaGroup findByGroupName(String name);


    @Query( "select g from OtaGroup g inner join OtaNode o on (g.id=o.groupName " +
            "and o.startMac <= :mac and o.endMac >= :mac)")
    public OtaGroup findByMAC(@Param("mac") String mac);

}
