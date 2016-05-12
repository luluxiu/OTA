package com.ota.ota.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class PageBean {
    @NotNull
	private int page;

    @NotNull
	private int size;

	private String sortName = "firmwareUpdatedAt";
	private String sortOrder = "asc";
}
