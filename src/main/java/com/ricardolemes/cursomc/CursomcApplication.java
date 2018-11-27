package com.ricardolemes.cursomc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardolemes.cursomc.domain.PagamentoComBoleto;
import com.ricardolemes.cursomc.domain.PagamentocomCartao;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

		
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {			

		
	}

}
