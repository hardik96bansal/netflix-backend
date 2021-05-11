package com.huex.netflixbacked;

import com.huex.netflixbacked.dao.MovieDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NetflixSpringServerApplication {

	@Autowired
	MovieDetailsRepository movieDetailsRepository;

	public static void main(String[] args) {
		SpringApplication.run(NetflixSpringServerApplication.class, args);
	}
}
