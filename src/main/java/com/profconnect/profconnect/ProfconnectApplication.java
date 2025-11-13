package com.profconnect.profconnect;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EntityScan(basePackages = "com.profconnect.profconnect.model") // Replace with your entity package
@EnableJpaRepositories(basePackages = "com.profconnect.profconnect.repository") // Replace with your repository package
public class ProfconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfconnectApplication.class, args);
	}
	@Component
	public class DBChecker implements CommandLineRunner {
		private final JdbcTemplate jdbcTemplate;
		public DBChecker(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}

		@Override
		public void run(String... args) throws Exception {
			String url = jdbcTemplate.getDataSource().getConnection().getMetaData().getURL();
			System.out.println("🔍 Connected to DB: " + url);
		}
	}

}
