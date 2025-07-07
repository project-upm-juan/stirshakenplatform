package com.upm.pfg.stirshakenplatform;

import com.upm.pfg.stirshakenplatform.database.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StShPlatformApp {


	public static void main(String[] args) {

		System.out.println("Starting STIR/SHAKEN Platform Application...");
		System.out.println("Initializing database...");
		System.out.println("Loading country data...");
		SpringApplication.run(StShPlatformApp.class, args);
	}

}
