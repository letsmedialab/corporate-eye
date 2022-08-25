package com.example.servingwebcontent.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.servingwebcontent.model.CUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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

	public static <T> String convertToJson(T object) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			return ow.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}