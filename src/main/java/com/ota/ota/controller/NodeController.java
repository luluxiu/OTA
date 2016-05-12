package com.ota.ota.controller;

import com.ota.ota.bean.NodeBean;
import com.ota.ota.bean.PageBean;
import com.ota.ota.model.OtaGroup;
import com.ota.ota.model.OtaNode;
import com.ota.ota.repository.OtaGroupRepository;
import com.ota.ota.repository.OtaNodeRepository;
import com.ota.ota.service.OtaNodeService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by freedom on 2016/3/21.
 */
@Controller
@RequestMapping("node")
public class NodeController {

    @Autowired
    private OtaNodeService otaNodeService;

    @Autowired
    private OtaNodeRepository otaNodeRepository;

    @Autowired
    private OtaGroupRepository otaGroupRepository;

    private static Logger logger = LoggerFactory.getLogger(GroupController.class);


    @RequestMapping(value = "", method = GET)
    public String index(Model model) {

        return "node/node";
    }

    @RequestMapping(value = "show/", method = GET)
    public String index(@RequestParam(value = "groupName") String groupName, PageBean pb, Model model) {
        OtaGroup group = otaGroupRepository.findByGroupName(groupName);

        Page<OtaNode> nodes = otaNodeService.getAllByGroupId(
                                    pb.getPage() - 1,
                                    pb.getSize(),
                                    pb.getSortName(),
                                    pb.getSortOrder(),
                                    group.getId()
                            );

        model.addAttribute("rows", nodes.getContent());
        model.addAttribute("total", nodes.getTotalElements());

        return "jsonTemplate";
    }

    @RequestMapping(value = "new/", method = POST)
    public String newNode(NodeBean nb, Model model) {
        Page<OtaNode> otanodes = otaNodeRepository.isMACExist(nb.getStartMac(), nb.getEndMac(), new PageRequest(0, 1));

        if(otanodes != null && otanodes.getContent().isEmpty() == false) {
            /**/
            Hibernate.initialize(otanodes.getContent().get(0).getGroupName());
            model.addAttribute("group", otanodes.getContent().get(0).getGroupName());
            return "jsonTemplate";
        }
        else {
            otaNodeService.createNode(nb);

            return "msg/success";
        }
    }


    @RequestMapping(value = "delete/", method = POST)
    public String deleteNode(@RequestParam("id") Long id) {

        otaNodeService.deleteNode(id);

        return "msg/success";
    }

    @RequestMapping(value = "check", method = GET)
    public String search(PageBean pb, @RequestParam("search") String mac, Model model) {
        Page<OtaGroup> groups = otaNodeRepository.findGroupByMAC(mac,
                    new PageRequest(pb.getPage() - 1,
                                    pb.getSize(),
                                    Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                                    pb.getSortName()));

        model.addAttribute("total", groups.getTotalElements());
        model.addAttribute("rows", groups.getContent());

        return "jsonTemplate";
    }

    @RequestMapping(value = "search", method = GET)
    public String search(Model model) {
        return "node/search";
    }
}
