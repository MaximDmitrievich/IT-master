package highlevellangs.code.hw.repos.simple.settings;

import java.util.Properties;

public class Settings {
    private static final Settings INSTANCE = new Settings();
	private static Properties properties;

	String file;

	private Settings() {
		/*try {
			// file =
			// this.getClass().getClassLoader().getResource("/WEB-INF/resources/univer.properties").getFile();
			/*if (context != null){
				InputStream resourceContent = context.getResourceAsStream("/WEB-INF/resources/univer.properties");
				//properties.load(new FileInputStream("/WEB-INF/resources/univer.properties"));
				properties.load(resourceContent);
			}
		} catch (IOException e) {
			System.out.println("cannot load: " + file);
			e.printStackTrace();
		}*/
	}


	public static Settings getInstance() {
		return INSTANCE;
	}

	public String value(String key) {
		return properties.getProperty(key);
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void setProperties(Properties properties) {
		Settings.properties = properties;
	}
}