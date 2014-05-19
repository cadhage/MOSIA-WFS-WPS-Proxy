/**
 * Copyright (C) 2014
 * by Institute for Geoinformatics, University of Muenster
 *
 * Contact: http://www.ifgi.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ifgi.mosia.wpswfs;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.Singleton;

import de.ifgi.mosia.geodesic.RouteUtil;
import de.ifgi.mosia.wpswfs.wps.WPSConnector;

@Singleton
public class Configuration {

	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
	
	private Properties config;

	public Configuration() {
		this.config = new Properties();
		try {
			this.config.load(getClass().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
	}
	
	public String getWFSURL() {
		return getStringProperty("WFS_URL");
	}
	
	public String getWPSURL() {
		return getStringProperty("WPS_URL");
	}
	
	public String getServiceURL() {
		return getStringProperty("SERVICE_URL");
	}
	
	protected String getStringProperty(String key) {
		String result = this.config.getProperty(key);
		
		if (result != null) {
			return result.trim();
		}
		
		return null;
	}
	
	protected boolean getBooleanProperty(String key) {
		String result = this.config.getProperty(key);
		
		if (result != null) {
			return Boolean.parseBoolean(result);
		}
		
		return false;
	}
	
	protected int getIntegerProperty(String key) {
		String result = this.config.getProperty(key);
		
		if (result != null) {
			return Integer.parseInt(result);
		}
		
		return 0;
	}
	
	public static class ConfigurationModule extends AbstractModule {

		@Override
		protected void configure() {
			bind(Configuration.class).in(Scopes.SINGLETON);
			bind(RouteUtil.class).in(Scopes.SINGLETON);
			bind(WPSConnector.class).in(Scopes.SINGLETON);
		}
		
	}
}
