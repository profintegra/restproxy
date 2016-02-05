package com.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;


public class Config {

    public static Config parseDefault() throws IOException {
        File f = new File("test.conf");
        if (!f.exists())
            f = tildeExpand("~/etc/test.conf");
        if (!f.exists())
            f = new File("/etc/test/test.conf");
        if (!f.exists())
            return new Config();
        
        return parse(f);
    }

	private File configFile;
	private int port = 8081;
	private String host = "0.0.0.0";
	public String staticRoot = "/static";
	public static final String DATA_BASE_PATH = "";
	public static final int DATA_BASE_PORT = 3306;
    
    public static Config parse(File configFile) throws IOException {
        System.err.println("Reading config file: " + configFile.getAbsolutePath());

        Config cfg = parse(loadProperties(configFile));
        cfg.configFile = configFile;
        return cfg;
    }
    
    public static Properties loadProperties(File file) throws IOException {
        Reader reader = null;
        try {
            Properties props = new Properties();
            if (file.exists()) {
                reader = new BufferedReader(new FileReader(file));
                props.load(reader);
            }
            return props;
        } finally {
            closeQuietly(reader);
        }
    }
    
    public static Config parse(Properties config) throws IOException {
        Config cfg = new Config();
        
        cfg.port = getProperty(config, "port", cfg.port);
        cfg.host = getProperty(config, "host", cfg.host);
        cfg.staticRoot = getProperty(config, "static.root", cfg.staticRoot);
        
        return cfg;
    }
    
    public static int getProperty(Properties p, String propertyName, int defaultValue){
    	String s = p.getProperty(propertyName);
    	if (s == null)
    		return defaultValue;
    	
    	return Integer.parseInt(s);
    }
    
    public static String getProperty(Properties p, String propertyName, String defaultValue){
    	String s = p.getProperty(propertyName);
    	if (s == null)
    		return defaultValue;
    	
    	return s;
    }
    
    public static File tildeExpand(String path) {
    	if (path.startsWith("~")) {
    		path = path.replaceFirst("~", getUserHome().getAbsolutePath());
    	}
    	return new File(path);
    }
    
    public static File getUserHome() {
        return new File(System.getProperty("user.home"));
    }
    
    public static void closeQuietly(Reader input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

	public int getPort() {
		return port;
	}
	
	public String getHost(){
		return this.host;
	}
}
