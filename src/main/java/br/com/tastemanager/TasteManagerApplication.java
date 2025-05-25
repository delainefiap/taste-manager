package br.com.tastemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.tastemanager")
public class TasteManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TasteManagerApplication.class, args);
	}
}


