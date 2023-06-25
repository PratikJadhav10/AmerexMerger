package com.br.amerex;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.br.amerex.repo.TFInterfaceRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
public class AmerexMergerApplication extends Thread {
	
	@Autowired
	private TFInterfaceRepository brRepository;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AmerexMergerApplication.class, args);
		new AmerexMergerApplication().start();
	}

	public void run() {
		try {
			
			Path p=Paths.get(System.getProperty("user.home"), "br_helper_stop.txt");
			File f=p.toFile();
			while(true) {
				if(f.exists()) {
					System.out.print("stop file found.");
					System.exit(0);
				}else {
					TimeUnit.SECONDS.sleep(30);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
}
