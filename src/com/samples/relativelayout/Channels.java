package com.samples.relativelayout;
	import org.json.*;

	import android.util.Log;

	import com.samples.relativelayout.GEO2tag_user.*;
	import com.samples.relativelayout.Connector;

	public class Channels{
		//Channel class
		/*Can be used as for full online work (use methods 
		 * with full set of parameters and just one instance of a class)
		 * or to combined mode - create instances for each channel
		 * 
		 * another reason for storing data into the class instance is to work
		 * lists of channels */
		 
		private String Name;
		private String Description;
		private String URL = "http://osll.spb.ru/";
		private long Radius = -1;
		private GEO2tag_user User = null;
		
		public Channels(String name,String description, long radius, GEO2tag_user user){
			this.Name(name);
			this.Description(description);
			this.Radius(radius);
			this.User(user);		
		}
		
		public Channels(){
			//is needed to use for using methods with full set of parameters
		}
		
		public GEO2tag_user User(){
			return User;
		}
		
		public boolean User(GEO2tag_user user)
		{
			if (user == null) return false;
			User = user;
			return true;
		}
		
		public String Name(){
			return Name;
		}
		
		public boolean Name(String name){
			Name = name;
			return true;
		}
		
		public String Description(){
			return Description;
		}
		
		public boolean Description(String description){
			Description = description;
			return true;
		}
		
		public long Radius(){
			return Radius;
		}
		
		public boolean Radius(long radius){
			//set radius = -1 to mark it not set
			if (radius < 0 & !(radius == -1)) return false;
			Radius = radius;
			return true;
		}
		
		public boolean Add(){
			//Add this channel to Geo2Tag server
			try
			{
			Connector conn = new Connector();	
				
			JSONObject AddQuery = new JSONObject();
			AddQuery.put("auth_token", (String)User.Auth_Token());
			AddQuery.put("name", Name);
			AddQuery.put("description", Description);
			AddQuery.put("url", URL);
			AddQuery.put("activeRadius", Radius);
			
			AddQuery = conn.SendQuerry(AddQuery, "http://tracks.osll.spb.ru:81/service/addChannel");
			if (AddQuery == null){ 
				Log.e("Channel", "Add: Querry returned null");
				return false;
			}
			String str = AddQuery.getString("status"); 

			//Читаем ответ от сервера. Если OK то возвращаем True
			if (str.equals("Ok")) {
				Log.i("Channel","ChannelAdd Succesful");
				return true;
			}
			else return false;
			
			} 
			catch (JSONException je) {
				je.printStackTrace();
				Log.e("ChannelAdd",je.getMessage());
				return false;
			}
			catch (Exception e) {
				Log.e("ChannelAdd",e.getMessage());
				return false;
			}
		}
		
		public boolean Subscribe(GEO2tag_user user,String channel){
			//подписаться на канал с параметрами: <Пользователь> и <название канала>
			try{
				Connector conn = new Connector();	
				
				JSONObject AddQuery = new JSONObject();
				AddQuery.put("auth_token", (String)User.Auth_Token());
				AddQuery.put("channel", channel);
				
				AddQuery = conn.SendQuerry(AddQuery, "http://tracks.osll.spb.ru:81/service/subscribe");
				if (AddQuery == null) {
					Log.e("Channel", "Subscribe: Querry returned null");
					return false;
				}
				String str = AddQuery.getString("status"); 

				//Читаем ответ от сервера. Если OK то возвращаем True
				if (str.equals("Ok")) {
					Log.i("Channel","ChannelSubscribe Succesful");
					return true;
				}
				else return false;
			}
			catch (JSONException je) {
				je.printStackTrace();
				Log.e("ChannelSubscribe",je.getMessage());
				return false;
			}
			catch (Exception e) {
				Log.e("ChannelSubscribe",e.getMessage());
				return false;
			}
		}
		
		public boolean Subscribe(){
			//подписаться на данный канал
			return this.Subscribe(User, Name);
		}
		
		public boolean Unsubscribe(GEO2tag_user user,String channel){
			//завершить подписку на канал с параметрами: <Пользователь> и <название канала>
			try{
				Connector conn = new Connector();	
				
				JSONObject AddQuery = new JSONObject();
				AddQuery.put("auth_token", (String)User.Auth_Token());
				AddQuery.put("channel", channel);
				
				AddQuery = conn.SendQuerry(AddQuery, "http://tracks.osll.spb.ru:81/service/unsubscribe");
				if (AddQuery == null) {
					Log.e("Channel", "Unsubscribe: Querry returned null");
					return false;
				}
				String str = AddQuery.getString("status"); 

				//Читаем ответ от сервера. Если OK то возвращаем True
				if (str.equals("Ok")) {
					Log.i("Channel","ChannelUnsubscribe Succesful");
					return true;
				}
				else return false;
			}
			catch (JSONException je) {
				je.printStackTrace();
				Log.e("ChannelUnsubscribe",je.getMessage());
				return false;
			}
			catch (Exception e) {
				Log.e("ChannelUnsubscribe",e.getMessage());
				return false;
			}
		}
		
		public boolean Unsubscribe(){
			//завершить подписку на данный канал
			return this.Unsubscribe(User, Name);
		}
		
		public long GetTimeSlot(GEO2tag_user user,String channel){
			//returns -1 when error occurred
			try{
				Connector conn = new Connector();	
				
				JSONObject AddQuery = new JSONObject();
				AddQuery.put("auth_token", (String)User.Auth_Token());
				AddQuery.put("channel", channel);
				
				AddQuery = conn.SendQuerry(AddQuery, "http://tracks.osll.spb.ru:81/service/getTimeSlot");
				if (AddQuery == null) {
					Log.e("Channel", "GetTimeSlot: Querry returned null");
					return -1;
				}
				Long Slot = AddQuery.getLong("timeSlot"); 

				//Читаем ответ от сервера. Если время > 0 то возвращаем время, иначе: -1
				if (Slot > 0) {
					Log.i("ChannelGetTimeSlot","ChannelGetTimeSlot Succesful");
					return (long)Slot;
				}
				else return -1;
			}
			catch (JSONException je) {
				je.printStackTrace();
				Log.e("ChannelGetTimeSlot",je.getMessage());
				return -1;
			}
			catch (Exception e) {
				Log.e("Channel",e.getMessage());
				return -1;
			}
		}
		public long GetTimeSlot(){
			return GetTimeSlot(User,Name);
		}
		public boolean SetTimeSlot(GEO2tag_user user,String channel,long TimeSlot){
			//returns -1 when error occurred
			try{
				Connector conn = new Connector();	
				
				JSONObject AddQuery = new JSONObject();
				AddQuery.put("auth_token", (String)User.Auth_Token());
				AddQuery.put("channel", channel);
				AddQuery.put("timeSlot", TimeSlot);
				
				AddQuery = conn.SendQuerry(AddQuery, "http://tracks.osll.spb.ru:81/service/setTimeSlot");
				if (AddQuery == null) {
					Log.e("Channel", "SetTimeSlot: Querry returned null");
					return false;
				}
				String str = AddQuery.getString("status"); 

				//Читаем ответ от сервера. Если OK то возвращаем True
				if (str.equals("Ok")) {
					Log.i("Channel","ChannelSetTimeSlot Succesful");
					return true;
				}
				else return false;
			}
			catch (JSONException je) {
				je.printStackTrace();
				Log.e("ChannelSetTimeSlot",je.getMessage());
				return false;
			}
			catch (Exception e) {
				Log.e("ChannelSetTimeSlot",e.getMessage());
				return false;
			}
		}
		
		public boolean SetTimeSlot(long timeSlot){
			return SetTimeSlot(User,Name,timeSlot);
		}
		
		public boolean SetTimeSlotDefault(GEO2tag_user user,String channel){
			//returns -1 when error occurred
			try{
				Connector conn = new Connector();	
				
				JSONObject AddQuery = new JSONObject();
				AddQuery.put("auth_token", (String)User.Auth_Token());
				AddQuery.put("channel", channel);
				
				AddQuery = conn.SendQuerry(AddQuery, "http://tracks.osll.spb.ru:81/service/setDefaultTimeSlot");
				if (AddQuery == null) {
					Log.e("Channel", "SetTimeSlotDefault: Querry returned null");
					return false;
				}
				String str = AddQuery.getString("status"); 

				//Читаем ответ от сервера. Если OK то возвращаем True
				if (str.equals("Ok")) {
					Log.i("Channel","ChannelSetTimeSlotDefault Succesful");
					return true;
				}
				else return false;
			}
			catch (JSONException je) {
				je.printStackTrace();
				Log.e("ChannelSetTimeSlotDefault",je.getMessage());
				return false;
			}
			catch (Exception e) {
				Log.e("ChannelSetTimeSlotDefault",e.getMessage());
				return false;
			}
		}
		public boolean SetTimeSlotDefault(){
			return SetTimeSlotDefault(User,Name);
		}
	
}
