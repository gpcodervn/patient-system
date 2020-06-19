package com.gpcoder.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * This class used to configure the API documents with Swagger 2
 *
 * Check at http://localhost:8090/swagger-ui.html
 */
@Configuration
@Profile(value = { "dev", "test" })
@PropertySource(value = {"classpath:swagger.properties"})
public class Swagger3Configuration {

	@Value("${swagger.api.title}")
	private String title;

	@Value("${swagger.api.description}")
	private String description;

	@Value("${swagger.api.termsOfServiceUrl}")
	private String termsOfServiceUrl;

	@Value("${swagger.api.contact.name}")
	private String contactName;

	@Value("${swagger.api.contact.url}")
	private String contactUrl;

	@Value("${swagger.api.contact.email}")
	private String contactEmail;

	@Value("${swagger.api.license}")
	private String license;

	@Value("${swagger.api.licenseUrl}")
	private String licenseUrl;

	@Value("${swagger.api.version}")
	private String version;

	@Bean
	public OpenAPI secureApi() {
		Contact contact = new Contact();
		contact.setName(contactName);
		contact.setUrl(contactUrl);
		contact.setEmail(contactEmail);

		return new OpenAPI()
				.info(new Info().title(title)
						.description(description)
						.termsOfService(termsOfServiceUrl)
						.contact(contact)
						.version(version)
						.license(new License().name(license).url(licenseUrl))
				);
	}
}