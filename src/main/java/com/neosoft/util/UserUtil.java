package com.neosoft.util;

import com.neosoft.firstpoc.model.User;

public interface UserUtil {
	
	public static void copyNonNullValues(User req,User db) {
		if(req.getFirstName()!=null) {
			db.setFirstName(req.getFirstName());
		}
		if(req.getMiddleName()!=null) {
			db.setMiddleName(req.getMiddleName());
		}
		if(req.getLastName()!=null ) {
			db.setLastName(req.getLastName());
		}
		if(req.getEmail()!=null) {
			db.setEmail(req.getEmail());
		}
		if(req.getPhoneNumber()!=null) {
			db.setPhoneNumber(req.getPhoneNumber());
		}
		if(req.getDob()!=null) {
			db.setDob(req.getDob());
		}
		if(req.getJoiningDate()!=null) {
			db.setJoiningDate(req.getJoiningDate());
		}
		if(req.getDepartment()!=null) {
			db.setDepartment(req.getDepartment());
		}
		if(req.getTechnology()!=null) {
			db.setTechnology(req.getTechnology());
		}
		if(req.getSalary()!=null) {
			db.setSalary(req.getSalary());
		}
		if(req.getAddress()!=null) {
			db.setAddress(req.getAddress());
		}
		
	}


}
