package com.snailxr.base.task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Date;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.snailxr.base.support.PropertyCache;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskTest.
 * @author Mohit Raj
 */
@Component
//@PropertySource("classpath:config.properties")
public class TaskTest {
	
	/** The log. */
	public final Logger log = Logger.getLogger(this.getClass());
	/*@Value("${ds_url}")
	private String urlstr;
	@Value("${ds_remurl}")
	private String remstr;
	@Value("${ds_schurl}")
	private String schUrl;*/
	/**
	 * Run.
	 */
	/*
	ds_url=http://bizlem.io:8087/GASalesConverter/rest/biz/callGAdataFetch
ds_schurl=http://bizlem.io:8082/portal/servlet/service/saveFunnel.checkexplorewithflag
ds_remurl=http://bizlem.io:8082/portal/servlet/service/saveFunnel.SendOtherCategoryMail
	*/public void run() {
		log.debug(" run" + (new Date()));
		String res= null;
		
		try {
			
			res=callScheduler(PropertyCache.getInstance().getValue("ds_url"));
			log.debug(" run" + res );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Run 1.
	 */
	public void run1() {
		log.debug(" run1" + (new Date()) );
		//remstr
        String res= null;
		
		try {
			res=callScheduler(PropertyCache.getInstance().getValue("ds_remurl"));
			log.debug(" run1" + res );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Test schedule.
	 */
	public void testSchedule() {
		log.debug(" testSchedule" + (new Date()) );
        String res= null;
		try {
			res=callScheduler(PropertyCache.getInstance().getValue("ds_schurl"));
			log.debug(" testSchedule" + res );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Test schedule 1.
	 */
	public void testSchedule1() {
		log.debug(" testSchedule1" + (new Date()) );
        String res= null;
		try {
			res=callScheduler(PropertyCache.getInstance().getValue("ds_leadurl"));
			log.debug(" testSchedule1" + res );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ds_readMailurl

	/**
	 * Read mail schedule.
	 */
	public void readMailSchedule() {
		log.debug(" readMailSchedule" + (new Date()) );
        String res= null;
		try {
			res=callScheduler(PropertyCache.getInstance().getValue("ds_readMailurl"));
			log.debug(" readMailSchedule" + res );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
 	/**
 	 * Call scheduler.
 	 *
 	 * @param gaUrl the ga url
 	 * @return the string
 	 */
 	public String callScheduler(String gaUrl) {
	        StringBuilder response = null;
	        URL url = null;
	        HttpURLConnection con = null;
	        try {

	            int responseCode = 0;

	            url = new URL(gaUrl);
	            con = (HttpURLConnection) url.openConnection();
//	            con.setConnectTimeout(10 * 1000);
	            con.setRequestMethod("GET");
	            con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
	            con.addRequestProperty("User-Agent", "Mozilla");
	            con.addRequestProperty("Referer", "google.com");

	            //	System.out.println("Request URL ... " + url);
	            con.setDoOutput(true);

	            responseCode = con.getResponseCode();

	            // System.out.println("Response Code : " + responseCode);
	            boolean redirect = false;

	            // normally, 3xx is redirect
	            if (responseCode != HttpURLConnection.HTTP_OK) {
	                if (responseCode == 308
	                        || responseCode == HttpURLConnection.HTTP_MOVED_TEMP
	                        || responseCode == HttpURLConnection.HTTP_MOVED_PERM
	                        || responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
	                    redirect = true;
	                }
	            }
	            log.debug("Response " + responseCode + "Url " + gaUrl);

	            if (redirect) {

	                // get redirect url from "location" header field
	                String newUrl = con.getHeaderField("Location");

	                log.debug("Location " + newUrl);
	                // get the cookie if need, for login
	                String cookies = con.getHeaderField("Set-Cookie");

	                // open the new connnection again
	                con = (HttpURLConnection) new URL(newUrl).openConnection();
	                con.setInstanceFollowRedirects(true);  //you still need to handle redirect manully.
	                HttpURLConnection.setFollowRedirects(true);
	                con.setRequestProperty("Cookie", cookies);
	                con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
	                con.addRequestProperty("User-Agent", "Mozilla");
	                con.addRequestProperty("Referer", "google.com");

	                //	System.out.println("Redirect to URL : " + newUrl);
	            }
	            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            String inputLine;
	            response = new StringBuilder();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            in = null;
	        } catch (Exception e) {

	            return "Json Not Returned";
	        } finally {
	            try {
	                con.disconnect();
	                con = null;
	            } catch (Exception e) {
	            	 con = null;
	                e.printStackTrace();
	               
	            }
	        }
	        return response.toString();

	    }
	 
	 
	/*
		String c=null;
	    Map<String, Charset> charsets = Charset.availableCharsets();
	    for (Map.Entry<String, Charset> entry : charsets.entrySet()) {
	       System.out.println(entry.getKey());
	    }

	*/

		/**
	 * Update category.
	 */
	public void updateCategory() {
			MongoClientURI connectionString = null;
			MongoClient mongo = null;
			DB db = null;
			DBCollection table = null;
			DBCursor cursor =  null;
			DBCursor cursor2 =  null;
			try {

				/**** Connect to MongoDB ****/
				// Since 2.10.0, uses MongoClient  "jdk.tls.trustNameService
				System.setProperty("javax.net.ssl.trustStore","/etc/ssl/firstTrustStore");
				System.setProperty("javax.net.ssl.trustStorePassword","bizlem123");
				System.setProperty ("javax.net.ssl.keyStore","/etc/ssl/MongoClientKeyCert.jks");
				System.setProperty ("javax.net.ssl.keyStorePassword","bizlem123");
				String uri = "mongodb://localhost:27017/?ssl=true";
					connectionString = new MongoClientURI(uri);
					mongo = new MongoClient(connectionString);
				//MongoClient mongo = new MongoClient(new ServerAddress("localhost", 27017),sslOptions);

				
				/**** Get database ****/
				// if database doesn't exists, MongoDB will create it for you
			    db = mongo.getDB("testdb");

				/**** Get collection / table from 'testdb' ****/
				// if collection doesn't exists, MongoDB will create it for you
				table = db.getCollection("user");

				/**** Insert ****/
				// create a document to store key and value
				BasicDBObject document = new BasicDBObject();
				document.put("name", "mkyong");
				document.put("age", 30);
				document.put("createdDate", new Date());
				table.insert(document);

				/**** Find and display ****/
				BasicDBObject searchQuery = new BasicDBObject();
				searchQuery.put("name", "mkyong");

				cursor = table.find(searchQuery);

				while (cursor.hasNext()) {
					System.out.println(cursor.next());
				}

				/**** Update ****/
				// search document where name="mkyong" and update it with new values
				BasicDBObject query = new BasicDBObject();
				query.put("name", "mkyong");

				BasicDBObject newDocument = new BasicDBObject();
				newDocument.put("name", "mkyong-updated");

				BasicDBObject updateObj = new BasicDBObject();
				updateObj.put("$set", newDocument);

				table.update(query, updateObj);

				/**** Find and display ****/
				BasicDBObject searchQuery2 
					= new BasicDBObject().append("name", "mkyong-updated");

				cursor2 = table.find(searchQuery2);

				while (cursor2.hasNext()) {
					System.out.println(cursor2.next());
				}

				/**** Done ****/
				System.out.println("Done");

			} catch (Exception e) {
				e.printStackTrace();
			} 
	         finally{
	        	 cursor.close();
	        	 cursor2.close();
	        	 mongo.close();
	        }
		}
	
	}

