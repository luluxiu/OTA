package com.ota.ota.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ota.ota.model.OtaGroup;
import com.ota.ota.repository.OtaGroupRepository;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.ota.ota.model.OtaGroup;
import com.ota.ota.bean.GroupBean;


@Service
public class OtaGroupService {

    @Autowired
    private OtaGroupRepository otaGroupRepository;

    private static final Logger logger = LoggerFactory.getLogger(OtaGroupService.class);


    public Page<OtaGroup> getAllByPage(int page, int pageSize, String sortName, String sortOrder) {
        logger.debug("====== Get groups by page : " + page);

        return otaGroupRepository.findAll(new PageRequest(page, pageSize, Sort.Direction.valueOf(sortOrder.toUpperCase()), sortName));
    }
    
    public void createGroup(GroupBean gb, MultipartFile file, HttpServletRequest request) {
        byte[]          uploadBytes = null;
        MessageDigest   md5 = null;
        byte[] 			digest ;
        String 			hashString ;
        File 			target ;
    	OtaGroup        group = new OtaGroup();

    	group.setGroupName(gb.getGroupName());
    	group.setProductModel(gb.getProductModel());
    	
    	logger.debug("====== name: " + gb.getGroupName() + ", model: " + gb.getProductModel());
    	
    	if(gb.getFirmwareVersion() != null) {
    		group.setFirmwareVersion(gb.getFirmwareVersion());
    	}
    	
    	if(file != null) {
    		logger.debug("====== filename: " + file.getOriginalFilename());
    		group.setFirmwareFileName(file.getOriginalFilename());
    		
    		try {
    			uploadBytes = file.getBytes();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    		try {
    			md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
    		digest = md5.digest(uploadBytes);
    	    hashString = new BigInteger(1, digest).toString(16);
    	    group.setFirmwareMd5(hashString.toLowerCase());
    	    logger.debug("====== md5: " + hashString.toLowerCase());
    	    
    	    String path = request.getSession().getServletContext().getRealPath("/upload/");
    	    group.setFirmwareUrl(request.getContextPath() + "/upload/" + group.getFirmwareFileName());
    	    
    	    logger.debug("====== path: " + path);
    	    logger.debug("====== url: " + group.getFirmwareUrl());
    	    
    	    
    	    target = new File(path);
    	    
            if(!target.exists()) {  
            	target.mkdirs();  
            }
            
            
            try {  
                //file.transferTo(target);  
            	file.transferTo(new File(path, group.getFirmwareFileName()));
            } catch (Exception e) {  
                e.printStackTrace();  
            }              
    	}
    	
    	otaGroupRepository.save(group);
    }
    
    
    public void deleteGroup(OtaGroup group) {
    	otaGroupRepository.delete(group);
    }

    public void deleteFile(OtaGroup group, HttpServletRequest request) {
    	if(group.getFirmwareFileName() != null) {
    		String path = request.getSession().getServletContext().getRealPath("/upload/");

    		File file = new File(path + "/" + group.getFirmwareFileName());
            logger.debug("====== path: " + path + ", name: " + group.getFirmwareFileName());
    	    if (file.isFile() && file.exists()) {
                logger.debug("====== delete file");
    	        file.delete();
    	    }
    	}
    }

    public void deleteFile(String filename) {
    	if(filename != null) {
    		File file = new File(filename);
    	    if (file.isFile() && file.exists()) {  
    	        file.delete(); 
    	    }
    	}
    }    
    
    public void editGroup(GroupBean gb, MultipartFile file, HttpServletRequest request) {
    	OtaGroup 		group       = otaGroupRepository.findOne(gb.getId());
        String 			path        = request.getSession().getServletContext().getRealPath("/upload/");
		byte[] 			uploadBytes = null;
		MessageDigest 	md5         = null;
		byte[] 			digest      ;
		String 			hashString  ;
		File 			target      ;


    	group.setGroupName(gb.getGroupName());
    	group.setProductModel(gb.getProductModel());

    	if(gb.getFirmwareVersion() != null) {
    		group.setFirmwareVersion(gb.getFirmwareVersion());
    	}
    	
    	if(file != null) {
    		
    		/* remove old file */
    		deleteFile(path + "/" + group.getFirmwareFileName());
    		
    		/* save file */
    		group.setFirmwareFileName(file.getOriginalFilename());
    		try {
    			uploadBytes = file.getBytes();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    		try {
    			md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
    		
    		digest = md5.digest(uploadBytes);
    	    hashString = new BigInteger(1, digest).toString(16);
    	    group.setFirmwareMd5(hashString.toLowerCase());

            group.setFirmwareUrl(request.getContextPath() + "/upload/" + group.getFirmwareFileName());
            logger.debug("====== path: " + path + ", name: " + group.getFirmwareFileName());
            target = new File(path);
            if(!target.exists()) {  
            	target.mkdirs();  
            }
            
            try {  
                file.transferTo(new File(path, group.getFirmwareFileName()));
            } catch (Exception e) {  
                e.printStackTrace();  
            }              
    	}
    	
    	otaGroupRepository.save(group);
    }

}


