package com.service;

import java.util.List;

import core.DBMgr;

public class Tag {

	
	
	public static List getAll() {
		List list = DBMgr.executeQuery("select tag from main_article order by tag desc");
		return list;
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
