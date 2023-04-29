package com.Damera.utils;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class PwdUtils {

	public String generatePwd(Integer len) {
		/*
		  String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		    Random rnd = new Random();

		    StringBuilder sb = new StringBuilder(len);
		    for (int i = 0; i < len; i++) {
		        sb.append(AB.charAt(rnd.nextInt(AB.length())));
		    }
		    return sb.toString();
		 */

		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String pwd = RandomStringUtils.random(len, characters );
		return pwd;
	}

}
