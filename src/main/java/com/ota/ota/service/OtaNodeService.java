package com.ota.ota.service;

import com.ota.ota.bean.NodeBean;
import com.ota.ota.model.OtaNode;
import com.ota.ota.repository.OtaGroupRepository;
import com.ota.ota.repository.OtaNodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by freedom on 2016/3/21.
 */
@Service
public class OtaNodeService {
    @Autowired
    private OtaNodeRepository otaNodeRepository;

    @Autowired
    private OtaGroupRepository otaGroupRepository;

    private static final Logger logger = LoggerFactory.getLogger(OtaGroupService.class);

    public Page<OtaNode> getAllByGroupId(int page, int pageSize, String sortName, String sortOrder, Long groupId) {
        //logger.debug("====== Get nodes by page " + page + ", groupId: " + groupId);

        return otaNodeRepository.findByGroupName(groupId, new PageRequest(page, pageSize, Sort.Direction.valueOf(sortOrder.toUpperCase()), sortName));
    }

    public void createNode(NodeBean nb) {
        OtaNode otanode = new OtaNode();

        otanode.setStartMac(nb.getStartMac());
        otanode.setEndMac(nb.getEndMac());
        otanode.setDescription(nb.getDescription());
        otanode.setGroupName(otaGroupRepository.findByGroupName(nb.getGroupName()));

        otaNodeRepository.save(otanode);
    }

    public void deleteNode(Long id) {
        otaNodeRepository.delete(id);
    }
}
