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
			//@ ��������� ������� ������������ �� ������
			try{
				Connector conn = new Connector(); //������� ��������� ���������� ������
				
				//��������� JSON ������
				JSONObject QueryAdd = new JSONObject();
				QueryAdd.put("login", userName);
				QueryAdd.put("password", Password);
				
				//� ������� Connector  ���������� ������
				QueryAdd = conn.SendQuerry(QueryAdd, "http://tracks.osll.spb.ru:81/service/addUser");
				if (QueryAdd == null) return false;
				String str = QueryAdd.getString("status"); 

				//������ ����� �� �������. ���� OK �� ���������� True
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
			//������� ����� �� ������
			try{
				if (! auth_token.equals("")) return false; //������ ��� ������������ ��� ��������� 
				Connector conn = new Connector(); //������� ��������� ���������� ������
				
				//������� JSON ����� ��� ��������
				JSONObject QueryLogin = new JSONObject();
					QueryLogin.put("login", userName);
					QueryLogin.put("password", Password);   			

				//� ������� Connector  ���������� ������
				QueryLogin = conn.SendQuerry(QueryLogin, "http://tracks.osll.spb.ru:81/service/login");
				if (QueryLogin == null) return false;
				String str = QueryLogin.getString("status"); 
				auth_token = QueryLogin.getString("auth_token");
				//������ ����� �� �������. ���� OK �� ���������� True
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
			//����������� �����
			try{ 		
				Connector conn = new Connector(); //������� ��������� ���������� ������
				
				//������� JSON ����� ��� ��������
				JSONObject QueryLogout = new JSONObject();
				QueryLogout.put("auth_token", auth_token);
				
				//� ������� Connector  ���������� ������
				QueryLogout = conn.SendQuerry(QueryLogout, "http://tracks.osll.spb.ru:81/service/quit");
				if (QueryLogout == null) return false;
				String str = QueryLogout.getString("status"); 
				//������ ����� �� �������. ���� OK �� ���������� True
				if (str.equals("Ok")) {
					auth_token = ""; //�������� ���� ������
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

