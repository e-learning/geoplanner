package com.samples.relativelayout;
import android.view.View;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import com.samples.relativelayout.R;
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
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View.OnClickListener;
public class GEOMaskTask extends ListActivity {
	private TextView my_txt;
    //GeoTask S_t=new GeoTask();
	String[] my_tasks={"������� 1","������� 2","������� 3","������� 4","������� 5","������� 6","������� 7"};
     @Override
     public void onCreate(Bundle savedInstanceState)
     {
    	 super.onCreate(savedInstanceState);
    	 setContentView(R.layout.list_s);
    	 setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, my_tasks)); 
     }
	
	
	public void onListItemClick(ListView parent,View v, int position,long id)
	{//
		}
}
	

