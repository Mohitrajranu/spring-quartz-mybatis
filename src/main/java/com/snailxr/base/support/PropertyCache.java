package com.snailxr.base.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyCache.
 */
public class PropertyCache {

	/** The prop. */
	//private final Properties configProp = new Properties();
	static Properties prop = null;
	
	/** The in. */
	static InputStream in = null;
	
	/** The instance. */
	// Static and Volatile attribute.
    private static volatile PropertyCache instance = null;
 
    /**
     * Instantiates a new property cache.
     */
    // Private constructor.
    private PropertyCache() {
 
    	try {
    		   in = this.getClass().getResourceAsStream("/config.properties");
    		   prop = new Properties();
    		   prop.load(in);
		//	configProp.load(this.getClass().getResourceAsStream("/META-INF/urlConfig.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally{
      	  if(in !=null){
      		  try {
  				in.close();
  				in = null;
  			} catch (IOException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
      	  }}
    }
 
    /**
     * Gets the single instance of PropertyCache.
     *
     * @return single instance of PropertyCache
     */
    // Static function.
    public static PropertyCache getInstance() {
        // Double check locking principle.
        // If there is no instance available, create new one (i.e. lazy initialization).
        if (instance == null) {
 
            // To provide thread-safe implementation.
            synchronized(PropertyCache.class) {
 
                // Check again as multiple threads can reach above step.
                if (instance == null) {
                    instance = new PropertyCache();
                }
            }
        }
        return instance;
    }
    
    /**
     * Gets the value.
     *
     * @param key the key
     * @return the value
     */
    public String getValue(String key) {
        return prop.getProperty(key);
    }
}
