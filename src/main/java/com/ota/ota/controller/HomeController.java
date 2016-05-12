package com.ota.ota.controller;

import com.ota.ota.bean.VersionBean;
import com.ota.ota.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ota.ota.model.OtaGroup;
import com.ota.ota.service.OtaGroupService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class HomeController {

    @Autowired
    private OtaGroupService otaGroupService;

    @Autowired
    private VersionService versionService;

    private static final Logger logger = LoggerFactory.getLogger(OtaGroupService.class);

    @RequestMapping(value = "", method = GET)
    public String index(Model model, HttpServletRequest request) {
        return "group/group";
    }

    @RequestMapping(value = "upload", method= GET)
    public String list(Model model, HttpServletRequest request) {
        ServletContext sc = request.getSession().getServletContext();
        String uploadFilePath = sc.getRealPath("/upload/");
        File files[] = new File(uploadFilePath).listFiles();

        //logger.debug("====== root path: " + sc.getRealPath("/"));

        model.addAttribute("files", files);

        return "home/dirlist";
    }

    @RequestMapping(value="ota", method = GET)
    public String otaVersion(VersionBean vb, Model model, HttpServletRequest request) {
        OtaGroup otaGroup = null;

        if(!versionService.checkMD5(vb.getC(), vb.getB())) {
            model.addAttribute("error", 1);
        }
        else {
            otaGroup = versionService.getGroupRecordByMac(vb.getA());
            model.addAttribute("error", 0);
            model.addAttribute("ota", otaGroup);
            model.addAttribute("url",
                    versionService.getRequestURL(request, otaGroup.getFirmwareUrl()));
        }

        return "home/version";
    }

}
