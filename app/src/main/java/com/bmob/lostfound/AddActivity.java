package com.bmob.lostfound;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.lostfound.bean.Found;
import com.bmob.lostfound.bean.Lost;

/**
 *
 * 
 * @ClassName: AddActivity
 * @Description: TODO
 * @author smile
 * @date 2018-5-21 11:41:06
 */
public class AddActivity extends BaseActivity implements OnClickListener {

	EditText edit_title, edit_photo, edit_describe;
	Button btn_back, btn_true;

	TextView tv_add;
	String from = "";
	
	String old_title = "";
	String old_describe = "";
	String old_phone = "";

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_add);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		tv_add = (TextView) findViewById(R.id.tv_add);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_true = (Button) findViewById(R.id.btn_true);
		edit_photo = (EditText) findViewById(R.id.edit_photo);
		edit_describe = (EditText) findViewById(R.id.edit_describe);
		edit_title = (EditText) findViewById(R.id.edit_title);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		btn_true.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		from = getIntent().getStringExtra("from");
		old_title = getIntent().getStringExtra("title");
		old_phone = getIntent().getStringExtra("phone");
		old_describe = getIntent().getStringExtra("describe");
		
		edit_title.setText(old_title);
		edit_describe.setText(old_describe);
		edit_photo.setText(old_phone);
		
		if (from.equals("状态")) {
			tv_add.setText("寻找帮助");
		} else {
			tv_add.setText("发布消息");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_true) {
			addByType();
		} else if (v == btn_back) {
			finish();
		}
	}
	String title = "";
	String describe = "";
	String photo="";
	
	/**
	  * addByType
	  * @Title: addByType
	  * @throws
	  */
	private void addByType(){
		title = edit_title.getText().toString();
		describe = edit_describe.getText().toString();
		photo = edit_photo.getText().toString();
		
		if(TextUtils.isEmpty(title)){
			ShowToast("");
			return;
		}
		if(TextUtils.isEmpty(describe)){
			ShowToast("");
			return;
		}
		if(TextUtils.isEmpty(photo)){
			ShowToast("拍照");
			return;
		}
		if(from.equals("失物")){
			addLost();
		}else{
			addFound();
		}
	}

	private void addLost(){
		Lost lost = new Lost();
		lost.setDescribe(describe);
		lost.setPhone(photo);
		lost.setTitle(title);
		lost.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("dersww!");
				setResult(RESULT_OK);
				finish();
			}
			
			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub
				ShowToast("dersee:"+arg0);
			}
		});
	}
	
	private void addFound(){
		Found found = new Found();
		found.setDescribe(describe);
		found.setPhone(photo);
		found.setTitle(title);
		found.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("执行!");
				setResult(RESULT_OK);
				finish();
			}
			
			@Override
			public void onFailure(int code, String arg0) {
				// TODO 自动生成方法存根
				ShowToast("suesss:"+arg0);
			}
		});
	}
}
