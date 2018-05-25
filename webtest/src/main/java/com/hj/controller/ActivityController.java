package com.hj.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class ActivityController {
	
	
	public static void main(String[] args) {
		 //final List<Integer> list = new CopyOnWriteArrayList<>();
		final List<Integer> list = new Vector<Integer>();
	    for(int i = 0; i < 5; i++) {
	        list.add(i);
	    }

	    // 线程一：通过Iterator遍历List
	    new Thread(new Runnable() {
	        public void run() {
	        	synchronized(list){
	        		for(int item : list) {
		                System.out.println("遍历元素：" + item);
		                // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
		                try {
		                    Thread.sleep(1000);
		                } catch (InterruptedException e) {
		                    e.printStackTrace();
		                }
		            }
	        	}
	            
	        }
	    }).start();

	    // 线程二：remove一个元素
	    new Thread(new Runnable() {
	        public void run() {
	            // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	    	      
	            list.add((int)(Math.random()*100));
	            System.out.println(list.size());
	        }
	    }).start();
	}
}