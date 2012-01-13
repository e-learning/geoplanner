package com.samples.relativelayout;
import android.view.View;
import android.view.Gravity;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import com.samples.relativelayout.ContactProvider;
import com.samples.relativelayout.R;
import android.content.Intent;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.location.LocationListener;
import android.app.Activity; 
import android.location.Location; 
import android.location.LocationManager; 
import android.app.AlertDialog;
import android.app.Dialog;
import android.widget.Toast;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View.OnClickListener;
import com.samples.relativelayout.Save_tasks;
import android.content.ContentValues;

public class GEOplannerActivity extends Activity implements LocationListener{
	private final int IDD_EXIT=0;
	private final int IDD_TASK=2;
	private final int IDD_REMOVE=3;
	private Cursor mycursor;

	//public static Button Spisok= (Button) findViewById(R.id.spisok_zd);
	private static final int IDM_CREATE=101;
	private static final int IDM_EXIT=102;
	private static final int IDM_LOGON=103;
	
	//private final int IDD_LOG=0;
	private LocationManager myManager;
	
	/* Use the LocationManager class to obtain GPS locations */
    
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		myManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		myManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		Button Spisok1= (Button) findViewById(R.id.spisok_zd);
		
		Spisok1.setOnClickListener(new View.OnClickListener()
	    {
		
	     public void onClick(View v)
	      {  
	    	 
	 		showDialog(IDD_EXIT);
	 		
	 		
	      }
	    });
		 
		
	}	
	int N=3;
	
	//for (int j=1;j<N+1;j++)
	
	final String[] my_tasks={"Задание 1","Задание 2"};
	@Override
	protected Dialog onCreateDialog(int id)
	{   switch (id){
	case IDD_EXIT:
		//Cursor cursor;
		/*final String[] my_tasks={Save_tasks.MY_TASK};
		Cursor cursor=managedQuery(ContactProvider.CONTENT_URI,my_tasks,null,null,null);*/
		
		
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("Список заданий");
		builder.setItems(my_tasks,new DialogInterface.OnClickListener()
		{public void onClick(DialogInterface dialog,int item){
			
			showDialog(IDD_TASK);
			
		}});
	builder.setCancelable(false);
	return builder.create();
	case IDD_TASK:
		AlertDialog.Builder builder2=new AlertDialog.Builder(this);
		builder2.setTitle("Функции задания");
		builder2.setMessage("Вы хотите удалить задачу?");
		
		builder2.setPositiveButton("Да, удалить", new DialogInterface.OnClickListener()
				{public void onClick(DialogInterface dialog,int item){
					getContentResolver().delete(ContactProvider.CONTENT_URI,"_ID="+this, null);
					mycursor.requery();
					showDialog(IDD_REMOVE);
					dialog.cancel();
				}});
		builder2.setNegativeButton("Нет, оставить", new DialogInterface.OnClickListener()
		{
		public void onClick(DialogInterface dialog,int item){
			
		setContentView(R.layout.main);	
			
		}});
				builder2.setCancelable(false);
				return builder2.create();
	case IDD_REMOVE:
		/*LayoutInflater builder3=getLayoutInflater();
		View layout3=builder3.inflate(R.layout.custom_layout,
				(ViewGroup)findViewById(R.id.toast_layout));
		TextView text=(TextView)layout3.findViewById(R.id.my_rem_t);
		text.setText("Задача удалена!");
		Toast toast1=new Toast(getApplicationContext());
		toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast1.setDuration(Toast.LENGTH_LONG);
		toast1.setView(layout3);
		toast1.show();*/
		AlertDialog.Builder builder3=new AlertDialog.Builder(this);
		builder3.setTitle("Задание");
		builder3.setMessage("удалено!");
		
		builder3.setCancelable(true);
		return builder3.create();
		
	default:
		return null;
	
	}
	
	}
	public int IsReg=0;
	private void Regist_User(){
		
		setContentView(R.layout.user_log);
		final EditText name_user=(EditText)findViewById(R.id.login_id);
		final EditText pswd_user=(EditText)findViewById(R.id.pswd_id);
		
		final TextView t=(TextView)findViewById(R.id.err);
		Button Log_us1= (Button) findViewById(R.id.log_us);
		
		Log_us1.setOnClickListener( new View.OnClickListener() {
	    //
		public void onClick(View v)
		  { 
		  String Error_log="Такого пользователя не существует"; 
		  String Error_pswd="Введенный пароль не верен";
		  
		     
			//Проверка, есть ли такой пользователель
		  
		    GEO2tag_user my_polz=new GEO2tag_user(name_user.getText().toString(),pswd_user.getText().toString());
			if(my_polz.Login()==true)
			{t.setText("Авторизация прошла удачно!") ;
			IsReg=1;} else
			{t.setText("Нет такого пользователя");}
		   
		}});
		
        Button Registr= (Button) findViewById(R.id.reg_us);
		
	    Registr.setOnClickListener( new View.OnClickListener()
		{public void onClick(View v)
		{ 			
			GEO2tag_user my_polz=new GEO2tag_user(name_user.getText().toString(),pswd_user.getText().toString());
		    if (my_polz.Add()==true)
		    	{t.setText("Регистрация прошла успешно!");};
			
		}}
		);
	}
	@Override
	
	public void onLocationChanged(Location location) { 
		// TODO Auto-generated method stub               
		TextView tx = (TextView) findViewById(R.id.Koord);                
		tx.setText("Широта: "   + location.getLatitude()  + "\nДолгота: " 
		+ location.getLongitude() +"\n");        
		}
	
	 @Override        
	 public void onProviderDisabled(String provider) {
		 //                      
		 }       
	 @Override        
	 public void onProviderEnabled(String provider) {  
		 //                       
		 }       
	 @Override        
	 public void onStatusChanged(String provider, int status, Bundle extras) { 
		 //                       
		 }
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE,IDM_CREATE,Menu.NONE,"Создать задачу");
		menu.add(Menu.NONE,IDM_LOGON,Menu.NONE,"Залогиниться");
		menu.add(Menu.NONE,IDM_EXIT,Menu.NONE,"Выход");
		return (super.onCreateOptionsMenu(menu));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		CharSequence message;
		switch (item.getItemId())
		{
		case IDM_CREATE:
			if (IsReg==1) {
			setContentView(R.layout.make_z);
			Button Savez = (Button) findViewById(R.id.save_z1);

			Savez.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Сохранить задачу
					setContentView(R.layout.make_z);
					Button Savez = (Button) findViewById(R.id.save_z1);
					Savez.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) { 
							final EditText name_user1=(EditText)findViewById(R.id.Name_push);
							final EditText place_user1=(EditText)findViewById(R.id.editText1);
							ContentValues values=new ContentValues(1);
							values.put(Save_tasks.MY_TASK,name_user1.getText().toString());
							values.put(Save_tasks.MY_PLACE,place_user1.getText().toString());
							getContentResolver().insert(ContactProvider.CONTENT_URI,values);
							mycursor.requery();
						}});
					Button Exitz = (Button) findViewById(R.id.button2);
					Exitz.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) { 
							// выход
							//System.out.println("ttt");
							setContentView(R.layout.main);
						}

					});
				}
			});
			} else setContentView(R.layout.main);
			break;
		case IDM_EXIT:
			
			break;
		case IDM_LOGON: 
			Regist_User();
			break;
	    default: return false;
		};
		return true;
	}
		
			
}
