package com.maly.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.qos.logback.core.net.SyslogOutputStream;

public class Demp {

	public static void main(String[] args) throws Exception{
		ThreadLocal<String> threadLocal = new ThreadLocal<>();
		threadLocal.set("maly");
		int pairLen=10;
		int max;
		for(int i=0;i<=99;i=i+pairLen) {
			System.out.println("("+i+","+(pairLen+i-1)+")");
		}
		
	}

}
