package coo.suzl.jiqiren;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import coo.suzl.jiqiren.bean.ChatMessage;
import coo.suzl.jiqiren.bean.ChatMessage.Type;
import coo.suzl.jiqiren.utils.HttpUtils;

public class MainActivity extends Activity {
	private ListView mMsg;
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mDatas;

	private Button mBtn;
	private EditText mInput;
	
	@SuppressLint("HandlerLeak")
	private Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			ChatMessage frommsg=(ChatMessage) msg.obj;
			mDatas.add(frommsg);
			mAdapter.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		initDatas();
		initListener();
		
	}

	private void initListener() {
	 mBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			final String tomsg=mInput.getText().toString();
			if(TextUtils.isEmpty(tomsg)){
				Toast.makeText(MainActivity.this, "发送消息不能为空", Toast.LENGTH_SHORT).show();
				return ;
			}
			
			ChatMessage tomesssage=new ChatMessage();
			tomesssage.setMsg(tomsg);
			tomesssage.setDate(new Date());
			tomesssage.setType(Type.OUTCONMING);
			mDatas.add(tomesssage);
			mAdapter.notifyDataSetChanged();
			
			mInput.setText("");
			
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			new Thread(){
				public void run() {
					ChatMessage frommsg =HttpUtils.sendMessage(tomsg);
					Message m=Message.obtain();
					m.obj=frommsg;
					mhandler.sendMessage(m);
				};
			}.start();
		
			
		}
	});
		
	}

	private void initDatas() {
		mDatas=new ArrayList<ChatMessage>(); 
		mDatas.add(new ChatMessage("亲爱的你好",Type.INCOMING,new Date()));
		mAdapter =new ChatMessageAdapter(this, mDatas);
		
		mMsg.setAdapter(mAdapter);
	}

	private void initView() {
		mMsg=(ListView) findViewById(R.id.lv_main_msgs);
		mInput=(EditText)findViewById(R.id.et_main_input);
		mBtn=(Button)findViewById(R.id.b_main_sendmsg);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE,Menu.FIRST+1, 1, "呵呵");
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// 响应每个菜单项(通过菜单项的ID)

		case Menu.FIRST+1:
			shareText(this,"hehe","nihao");
			break;
	
		default:
			// 对没有处理的事件，交给父类来处理
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	public static void shareText(Context context, String title, String text) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, title);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		context.startActivity(Intent.createChooser(intent, title));
	}


}
