package com.example.demo.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Member {
	private int id;
	private String memberNo;
	private String name;
	private String address;
	private String phone;
	private String gender;
	private String comment;
	private Date createdAt;
	private Date updatedAt;

}
