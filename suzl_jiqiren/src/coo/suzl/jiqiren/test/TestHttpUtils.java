package coo.suzl.jiqiren.test;

import coo.suzl.jiqiren.utils.HttpUtils;
import android.test.AndroidTestCase;
import android.util.Log;

public class TestHttpUtils extends AndroidTestCase{

	public void testSendInfo(){
		String res=HttpUtils.doGet("给我讲个笑话");
		Log.e("TAG",res);
		res=HttpUtils.doGet("给我讲个鬼故事");
		Log.e("TAG",res);
		res=HttpUtils.doGet("你喜欢谁");
		Log.e("TAG",res);
		res=HttpUtils.doGet("你好");
		Log.e("TAG",res);
	}
}