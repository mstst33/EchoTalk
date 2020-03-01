package com.mstst33.setting;

import java.util.*;

import com.mstst33.echoproject.R;

import android.*;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;

public class Dialog_Setting_Alert extends Dialog{

	Context context;
	LinearLayout settingAlert;
	ListView list;
	ArrayAdapter<CharSequence> Adapter;
	
	public Dialog_Setting_Alert(Context context) {
		super(context);
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated constructor stub
		
		settingAlert = (LinearLayout) View.inflate(context, R.layout.setting_notice_linear_listview, null);
		Adapter = ArrayAdapter.createFromResource(context, R.array.setting_alert, android.R.layout.simple_list_item_multiple_choice);
		list =(ListView)settingAlert.findViewById(R.id.setting_notice_listview);
		list.setAdapter(Adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		list.setOnItemClickListener(itemClick);
		setContentView(settingAlert);
	}
	
	AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			//String tv = (String)parent.getAdapter().getItem(position);
			//Toast.makeText(context, tv, 0).show();
			
			switch(position){
			case 0:
				Toast.makeText(context, "toast0", 0).show();
				break;
			case 1:
				Toast.makeText(context, "toast1", 0).show();
				break;
			case 2:
				Toast.makeText(context, "toast2", 0).show();
				break;
			case 3:
				Toast.makeText(context, "toast3", 0).show();
				break;
			}
			
		}
	};

}
