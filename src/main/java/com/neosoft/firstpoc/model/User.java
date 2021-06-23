package com.neosoft.firstpoc.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_tab")
/*
@SQLDelete(sql = "UPDATE users_tab SET flag = true WHERE id=?")
@Where(clause = "flag=false")//if where is not annoted then us e filter
@FilterDef(
        name = "deletedUserFilter",
        parameters = @ParamDef(name = "isDeleted", type = "boolean")
)
@Filter(
        name = "deletedUserFilter",
        condition = "flag = :isDeleted"
)*/
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id_col")
	private Integer id;
	
	@Column(name = "user_firstName_col",nullable = false)
	@NotNull(message = "Please Enter First Name")
	@Size(min = 2,max=10,message = "First Name Should have At least 8 Character")
	private String firstName;
	
	@Column(name = "user_middleName_col",nullable = false)
	@NotNull(message = "Please Enter Middle Name")
	@Size(min = 2,max=10,message = "Middle Name Should have At least 8 Character")
	private String middleName;
	
	@Column(name = "user_lastName_col",nullable = false)
	@NotNull(message = "Please Enter Last Name")
	@Size(min = 2,max=10,message = "Last Name Should have At least 8 Character")
	private String lastName;
	
	@Column(name = "user_email_col",nullable = false)
	@NotNull(message = "Please Enter Your Email")
	@Email
	private String email;
	
	@Column(name = "user_phoneNumber_col",nullable = false)
	@NotNull(message = "Please Enter Your Phone Number")
	@Pattern(regexp="(^$|[0-9]{10})")
	private String phoneNumber;
	
	@Column(name = "user_dob_col",nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd")
	@NotNull(message = "Please Enter Your Date Of Birth")
	@Past(message = "User Date of Birth is Invalid")
	private Date dob;
	
	@Column(name = "user_joiningDate_col",nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "Please Enter Your Date Of Joining")
	@Past(message = "Please Enter Correct Date Of Joining")
	private Date joiningDate;
	
	@Column(name = "user_department_col",nullable = false)
	@NotNull(message = "Please Enter Your Department")
	@Size(min = 2,max=8,message = "Department Should Have At Least 6 Character")
	private String department;
	
	@Column(name = "user_technology_col",nullable = false)
	@NotNull(message = "Please Enter Your Technology")
	@Size(min = 2,max=8,message = "Technology Should Have At Least 6 Character")
	private String technology;
	
	@Column(name = "user_salary_col",nullable = false)
	@NotNull(message =  "Please Enter Your Salary")
	private Double salary;
	
	@Column(name = "user_address_col",nullable = false)
	@NotNull(message = "Please Enter Your Address")
	@Size(min = 3,max = 32,message = "Address Should Have At Least 30 Character")
	private String address;
	
	private Boolean flag=Boolean.FALSE;
	

	


	
	
	
}
