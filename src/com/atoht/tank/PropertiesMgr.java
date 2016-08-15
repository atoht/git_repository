package com.atoht.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertiesMgr {
	private static Properties pro = new Properties();
	static {
		try {
			pro.load(PropertiesMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    public static String getProperty(String key) {
		return pro.getProperty(key);
    }
}
