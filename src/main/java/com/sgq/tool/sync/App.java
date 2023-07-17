package com.sgq.tool.sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.sgq.tool.sync.log.TLogger.TLog;

@SpringBootApplication
public class App
{
	public static void main(final String[] args)
	{
		SpringApplication.run(App.class, args);
		TLog.info("Test {}", "abc");
		TLog.error("Test exception", new RuntimeException("def"));
	}
}
