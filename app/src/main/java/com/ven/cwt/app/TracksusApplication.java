package com.ven.cwt.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ven.cwt.app.entity.Request;
import com.ven.cwt.app.repository.RequestRepository;

@SpringBootApplication
public class TracksusApplication implements CommandLineRunner {

	@Autowired
	private RequestRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(TracksusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();

		repository.save(new Request("http://tracksus.com/profile/1", "ObtenerPerfil", "GET", null, null, "1"));
		repository.save(new Request("http://tracksus.com/invoice", "ObtenerFactura", "POST", "{ invoice: 1 }", null, "2"));

	}

}
