package co.FastApps.FastChat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class FastChatApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(FastChatApplication.class);

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
	}
	public static void main(String[] args) {
		SpringApplication.run(FastChatApplication.class, args);
		logger.debug("--Application Started--");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(FastChatApplication.class);
	}
}
