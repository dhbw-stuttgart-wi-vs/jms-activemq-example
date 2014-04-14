package activemqtest;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	public static void main(String[] args) {
		configureLog4JFromClasspath();
		Logger log = LoggerFactory.getLogger(Main.class);

		log.debug("Starting...");
		
		// TODO do something here
		
		log.debug("Terminating.");
		System.exit(0);
	}

	public static void configureLog4JFromClasspath() {
		InputStream log4JPropertiesStream = Main.class.getClassLoader().getResourceAsStream("log4j.properties");

		if (log4JPropertiesStream != null) {
			try {
				Properties properties = new Properties();
				properties.load(log4JPropertiesStream);
				PropertyConfigurator.configure(properties);
			} catch (Exception e) {
				System.err.println("Tried to load log4j configuration from classpath, resulting in the following exception: {}"
						+ e);
				System.err.println("Using default logging configuration.");
			}
		}
	}

}
