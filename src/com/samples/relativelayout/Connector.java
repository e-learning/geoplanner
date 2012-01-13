
	package com.samples.relativelayout;
	
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.OutputStream;
	import java.io.OutputStreamWriter;
	import java.net.MalformedURLException;
	import java.net.URL;
	import java.net.URLConnection;

	import org.json.JSONObject;
	import org.json.JSONTokener;



	public class Connector{
	    	//Служебный класс для отправки на сервер JSON объектов и возвращения JSON объектов в ответ
	    	
	    	public JSONObject SendQuerry(JSONObject object, String url){
	    		try {
	    		URL svr = new URL(url);
	    		URLConnection con = (URLConnection) svr.openConnection();
				con.setDoOutput(true);
				con.setDoInput(true);
				OutputStream outStream = con.getOutputStream();
				OutputStreamWriter writer = new OutputStreamWriter(outStream);
				writer.write(object.toString());
				writer.close();

				InputStream ins = con.getInputStream();
				BufferedReader fromServer = new BufferedReader(new InputStreamReader(ins));
				String resp = fromServer.readLine();
				
				return (JSONObject) new JSONTokener(resp).nextValue();
	    		} catch (MalformedURLException e) {
					System.out.println("wrong url");
					return null;
				} catch (IOException e) {
					System.out.println(e.getMessage());
					return null;
				} catch (Exception e) {
					System.out.println("other");
					return null;
				}   			
	    	}
	    }