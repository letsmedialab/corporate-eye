package com.example.servingwebcontent.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneralUtils {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "0spyn@123";
		String encodedPassword = encoder.encode(rawPassword);

		System.out.println(encodedPassword);
	}

	public static String bCryptEncode(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

}