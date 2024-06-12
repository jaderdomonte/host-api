package com.hostfully.bookingapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="Hostfully Booking API"))
public class BookingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApiApplication.class, args);
	}

}
