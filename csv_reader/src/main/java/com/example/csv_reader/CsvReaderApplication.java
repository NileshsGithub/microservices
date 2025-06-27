package com.example.csv_reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CsvReaderApplication {
	public static void main(String[] args) {
		SpringApplication.run(CsvReaderApplication.class, args);
	}
}
