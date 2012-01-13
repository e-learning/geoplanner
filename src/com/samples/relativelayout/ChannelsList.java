package com.samples.relativelayout;
import java.util.ArrayList;

import org.json.*;

import android.util.Log;

import com.samples.relativelayout.Connector;
import com.samples.relativelayout.GEO2tag_user.*;


public class ChannelsList{
	/*This class presents ability to work with lists of channels,
	 * Each instance includes a list of full filled channel objects */
	
	private ArrayList Channels = new ArrayList();
	private GEO2tag_user User = null;

	
	public int len(){
		return Channels.size();
	}
	
	public Channels get(int i){
		return (Channels)Channels.get(i);
	}
	public ChannelsList(GEO2tag_user user){
		this.User(user);
	}
	
	public boolean User(GEO2tag_user user){
		if (user == null) {
			Log.e("ChannelList","User(set): user has null value!");
			return false;
		}
		User = user;
		return true;
	}
	
	public GEO2tag_user User(){
		return User;
	}
	
	public boolean PlusChannel(Channels channel){
		if (channel == null){
			Log.e("ChannelList","Method AddChannel have not recieved parameter channel");
			return false;
		}
		Channels.add(channel);
		return true;
	}
	
	public boolean PlusChannel(String name,String description, long radius, GEO2tag_user user){
		return this.PlusChannel(new Channels(name,description,radius,user));		
	}
	
	public void ClearChannels(){
		Channels.clear();
	}
	
	public boolean FillWithSubscribedChannels(GEO2tag_user user){
		try{
			this.ClearChannels(); //first of all let's delete all previous channels
			Connector conn = new Connector();	
			
			JSONObject AddQuery = new JSONObject();
			AddQuery.put("auth_token", (String)User.Auth_Token());
			
			AddQuery = conn.SendQuerry(AddQuery, "http://tracks.osll.spb.ru:81/service/subscribed");
			if (AddQuery == null) {
				Log.e("Channel", "GetTimeSlot: Querry returned null");
				return false;
			}
			
			//Taking from JSON object an array of channels
			JSONArray  JSONChannels = AddQuery.getJSONArray("channels"); 
			
			//Taking from each element we take fields and array of tags
			for (int i = 0;i < JSONChannels.length();i++){
				this.PlusChannel(JSONChannels.getJSONObject(i).getString("name"), JSONChannels.getJSONObject(i).getString("description"), -1, User) ;
//!!!ATTENTON!! Server gives us an arrau of items(string). In future versions they should be read too!			
			}
			return true;
		}
		catch (JSONException je) {
			je.printStackTrace();
			Log.e("ChannelGetTimeSlot",je.getMessage());
			return false;
		}
		catch (Exception e) {
			Log.e("Channel",e.getMessage());
			return false;
		}
	}
	public boolean FillWithSubscribedChannels(){
		return this.FillWithSubscribedChannels(User);
	}
	
	public String ToString(){
		//for console testing and report building
		//pints all the channels from this list formated as^ "<Name> : <Description>"
		String buffer = "";
		for (int i = 0;i < Channels.size();i++){
			buffer = buffer + ((Channels)Channels.get(i)).Name() + " : " + ((Channels)Channels.get(i)).Description() + "\n";
		}
		return buffer;
	}
}
