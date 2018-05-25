package com.hj.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;



public class T {

	public static void main(String[] args) throws ParseException {
		Long timestamp = 1475224335l*1000;  
		Date date = new Date(timestamp);
		System.out.println(date);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(date));
//		System.out.println(date);
	}
	
}

