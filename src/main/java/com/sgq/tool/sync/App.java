package com.sgq.tool.sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.sgq.tool.log.TLogger.TLog;

@SpringBootApplication
public class App
{
	public static void main(final String[] args)
	{
		TLog.info("Test {}", "abc");
		SpringApplication.run(App.class, args);
	}
}
