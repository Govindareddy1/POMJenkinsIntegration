package com.qa.utils;



public class GeneralUtil {

	// Generates random emial ID
	public static String randonemilId()
	{
		String emailid="uiautomation."+System.currentTimeMillis()+"@example.com";
		return emailid;
		
	}
	// Generates random Org name 
	public static String randomStringname()
	{
		String orgName="uiautomation"+System.currentTimeMillis();
		return orgName;
		
	}
	
	// Generates random number 
		public static long randomnumber()
		{
			long orgName=System.currentTimeMillis();
			return orgName;
			
		}
		
		
}