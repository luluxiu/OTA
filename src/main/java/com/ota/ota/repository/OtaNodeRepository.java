package com.ota.ota.repository;

import com.ota.ota.model.OtaGroup;
import com.ota.ota.model.OtaNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface OtaNodeRepository extends JpaRepository<OtaNode, Long> {

    @Query("SELECT o FROM OtaNode o INNER JOIN o.groupName g WHERE g.id = :groupId")
	Page<OtaNode> findByGroupName(@Param("groupId")Long groupId, Pageable pageRequest);

    @Query("select o from OtaNode o where (o.startMac <= :mac and o.endMac >= :mac)")
    public OtaNode findNodeByMac(@Param("mac") String mac);

    @Query( "select o from OtaNode o where (" +
            "(o.startMac <= :a and :a <= o.endMac) or " +
            "(o.startMac <= :b and :b <= o.endMac) or " +
            "(o.startMac >= :a and :b >= o.endMac)) ")
    public Page<OtaNode> isMACExist(@Param("a") String a, @Param("b") String b, Pageable pageRequest);
/*
    @Query( "select g from OtaGroup g inner join OtaNode o on (" +
            "g.id=o.groupName and " +
            "((o.startMac <= :a and :a <= o.endMac) or " +
            "(o.startMac <= :b and :b <= o.endMac) or " +
            "(o.startMac >= :a and :b >= o.endMac))) ")
    public Page<OtaGroup> findGroupByMAC(@Param("a") String a, @Param("b") String b);
*/
    @Query( "select g from OtaGroup g inner join OtaNode o on (" +
            "g.id=o.groupName and (o.startMac <= :mac and :mac <= o.endMac)) ")
    public Page<OtaGroup> findGroupByMAC(@Param("mac") String mac, Pageable pageRequest);
}
