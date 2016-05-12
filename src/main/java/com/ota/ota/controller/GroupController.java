package com.ota.ota.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ota.ota.model.OtaGroup;
import com.ota.ota.repository.OtaGroupRepository;
import com.ota.ota.service.OtaGroupService;
import com.ota.ota.bean.GroupBean;
import com.ota.ota.bean.PageBean;



@Controller
@RequestMapping("group")
public class GroupController {

    @Autowired
    private OtaGroupService otaGroupService;

    @Autowired
    private OtaGroupRepository otaGroupRepository;

    private static Logger logger = LoggerFactory.getLogger(GroupController.class);

  
    @RequestMapping(value = "show/", method = GET)
    public String index(PageBean pb, Model model) {


    	logger.debug("====== page: " + pb.getPage() + ", size: " + pb.getSize() +
    				", name: " + pb.getSortName() + ", oder: " + pb.getSortOrder());

    	Page<OtaGroup> pog = otaGroupService.getAllByPage(
        								pb.getPage() - 1,
        								pb.getSize(),
        								pb.getSortName(),
        								pb.getSortOrder()
        							);

    	model.addAttribute("rows", pog.getContent());
    	model.addAttribute("total", pog.getTotalElements());
    	/*
    	for(OtaGroup o : pog.getContent()) {
    		logger.debug("====== " + o.getFirmwareUpdatedAt());
    	}
        */

    	return "jsonTemplate";
    }
    

    @RequestMapping(value = "new/", method = POST)
    public String newGroup( GroupBean gb,
			    			@RequestParam(value = "file", required = false) MultipartFile file,
			    			HttpServletRequest request) {

    	otaGroupService.createGroup(gb, file, request);

        return "msg/success";
    }

    @RequestMapping(value="delete/", method=POST)
    public String deleteGroup(  GroupBean gb,
				    			Model model,
				    			HttpServletRequest request) {

    	OtaGroup group = otaGroupRepository.findOne(gb.getId());

    	otaGroupService.deleteFile(group, request);
    	otaGroupService.deleteGroup(group);

    	return "msg/success";
    }

    @RequestMapping(value="edit/", method=POST)
    public String editGroup(GroupBean gb,
							@RequestParam(value = "file", required = false) MultipartFile file,
							HttpServletRequest request) {

    	otaGroupService.editGroup(gb, file, request);

    	return "msg/success";
    }

}
