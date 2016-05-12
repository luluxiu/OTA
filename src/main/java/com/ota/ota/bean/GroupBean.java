package com.ota.ota.bean;





import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class GroupBean {

	private Long id;

    @NotNull
	private String groupName;

    @NotNull
	private String productModel;

    @NotNull
	private String firmwareVersion;
	
	
	
	@Override
	public String toString() {
		return "id : " + id + ", groupName : " + groupName + ", model : " + productModel + ", version : " + firmwareVersion;
	}


}