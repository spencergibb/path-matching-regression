package com.example.demosubdirpatternmatching;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DemosubdirpatternmatchingApplication {

	@Autowired
	ResourceLoader resourceLoader;

	@Component
 	class Runner implements ApplicationRunner {

		@Override
		public void run(ApplicationArguments args) throws Exception {
			File configdir = new File("configdir");
			System.out.printf("dir: %s, exists: %s, isdir: %s\n", configdir, configdir.exists(), configdir.isDirectory());
			List<String> directories = matchingDirectories(resourceLoader, configdir, "sub*/");
			System.out.println(directories);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(DemosubdirpatternmatchingApplication.class, args);
	}


	private static List<String> matchingDirectories(ResourceLoader resourceLoader,
													File dir, String value) {
		List<String> output = new ArrayList<String>();
		try {
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(resourceLoader);
			String path = new File(dir, value).toURI().toString();
			for (Resource resource : resolver.getResources(path)) {
				if (resource.getFile().isDirectory()) {
					output.add(resource.getURI().toString());
				}
			}
		}
		catch (IOException e) {
		}
		return output;
	}
}
