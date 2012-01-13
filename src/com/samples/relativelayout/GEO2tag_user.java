package com.samples.relativelayout;
import org.json.JSONException;
import org.json.JSONObject;
import com.samples.relativelayout.Connector;
public class GEO2tag_user{
		private String userName;
		private String Password;
		private String auth_token = "";
		
		public GEO2tag_user(String name,String password){
			userName = name;
			Password = password;
		}
		
		public void Name(String name){
				userName = name;
		}

		public String Name(){
			return userName;
		}
		
		public void Password(String pass){
			Password = pass;
		}
		public String Auth_Token(){
			return auth_token;
		}
		
		public boolean Add(){
			//@ добавляет данного пользователя на сервер
			try{
				Connector conn = new Connector(); //создаем экземпляр служебного класса
				
				//формируем JSON объект
				JSONObject QueryAdd = new JSONObject();
				QueryAdd.put("login", userName);
				QueryAdd.put("password", Password);
				
				//с помощью Connector  отправляем запрос
				QueryAdd = conn.SendQuerry(QueryAdd, "http://tracks.osll.spb.ru:81/service/addUser");
				if (QueryAdd == null) return false;
				String str = QueryAdd.getString("status"); 

				//Читаем ответ от сервера. Если OK то возвращаем True
				if (str.equals("Ok")) {
					return true;
				}
				else return false;
			 } 
			 catch (JSONException je) {
				je.printStackTrace();
				return false;
			 }
			 catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			 }
		}
		
		public boolean Login(){
			//логинит юзера на сервер
			try{
				if (! auth_token.equals("")) return false; //значит наш пользователь уже залогинен 
				Connector conn = new Connector(); //создаем экземпляр служебного класса
				
				//создаем JSON оъект для отправки
				JSONObject QueryLogin = new JSONObject();
					QueryLogin.put("login", userName);
					QueryLogin.put("password", Password);   			

				//с помощью Connector  отправляем запрос
				QueryLogin = conn.SendQuerry(QueryLogin, "http://tracks.osll.spb.ru:81/service/login");
				if (QueryLogin == null) return false;
				String str = QueryLogin.getString("status"); 
				auth_token = QueryLogin.getString("auth_token");
				//Читаем ответ от сервера. Если OK то возвращаем True
				if (str.equals("Ok")) {
					//auth_token = QueryAdd.getString("auth_token");
					return true;
				}
				else return false;
			}
			catch (JSONException je) {
				je.printStackTrace();
				return false;
			}
			catch (Exception e) {
					System.out.println(e.getMessage());
					return false;
			}
		}
		
		public boolean Logout(){
			//осуществить выход
			try{ 		
				Connector conn = new Connector(); //создаем экземпляр служебного класса
				
				//создаем JSON оъект для отправки
				JSONObject QueryLogout = new JSONObject();
				QueryLogout.put("auth_token", auth_token);
				
				//с помощью Connector  отправляем запрос
				QueryLogout = conn.SendQuerry(QueryLogout, "http://tracks.osll.spb.ru:81/service/quit");
				if (QueryLogout == null) return false;
				String str = QueryLogout.getString("status"); 
				//Читаем ответ от сервера. Если OK то возвращаем True
				if (str.equals("Ok")) {
					auth_token = ""; //обнуляем ключ сессии
					return true;
				}
				else return false;
				
			}
			catch (JSONException je){
				System.out.println(je.getMessage());
				return false;
			}
			catch (Exception e) {
					System.out.println(e.getMessage());
					return false;
			}

		}
		
	}

