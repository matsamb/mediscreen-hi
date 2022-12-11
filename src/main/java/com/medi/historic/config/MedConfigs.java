package com.medi.historic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("medconfigs")
@Configuration
//@RefreshScope
public class MedConfigs {

	private String angularurl;
	
	public String getAngularurl() {
		return angularurl;
	}
	
	public void setAngularurl(String url) {
		this.angularurl = url;
	}
	
}
