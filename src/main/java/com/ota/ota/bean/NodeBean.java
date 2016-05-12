package com.ota.ota.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class NodeBean {

	private Long id;

    @NotNull
	private String groupName;

    @NotNull
    @Pattern(regexp = "^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$", message = "Invalid MAC Address")
	private String startMac;

    @NotNull
    @Pattern(regexp = "^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$", message = "Invalid MAC Address")
	private String endMac;

	private String description;


	@Override
	public String toString() {
		return "id : " + id + ", group : " + groupName + ", start : " + startMac + ", end : " + endMac + ", desc : " + description;
	}
}
