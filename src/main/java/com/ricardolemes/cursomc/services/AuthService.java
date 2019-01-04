package com.ricardolemes.cursomc.services;

import java.util.Arrays;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ricardolemes.cursomc.domain.Cliente;
import com.ricardolemes.cursomc.repositories.ClienteRepository;
import com.ricardolemes.cursomc.services.exception.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepostitory;
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	 private EmailService emailService;
	
	private Random rand= new Random();
	
	 public void sendNewPassowrd(String email) {
		 
		 Cliente cliente = clienteRepostitory.findByEmail(email);
		 if (cliente  == null) {
			 throw new ObjectNotFoundException("email não encontrado!");
			 
		 }
		 
		 String newPass = newPassword();
		 cliente.setSenha(pe.encode(newPass));

		 clienteRepostitory.saveAll(Arrays.asList(cliente));
		
		 emailService.sendNewPasswordEmail(cliente, newPass);
		 
	 }

	public String newPassword() {
	   char[] vet = new char[10];
	    for (int i=0; i<10; i++) {
	    	vet[i] = randomChar();
	    	
	    }
		
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) {
		 return (char) (rand.nextInt(10) + 48);
		 
		}
		else if (opt== 1) {		
			return (char) (rand.nextInt(26) + 65);
		}
		else {  
			return (char) (rand.nextInt(26) + 97);
		}
	
	}
}
