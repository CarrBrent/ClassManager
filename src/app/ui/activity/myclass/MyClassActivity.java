package app.ui.activity.myclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;



import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import myclass.manager.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import app.ui.TitleActivity;
import app.ui.activity.setting.AboutActivity;
import app.util.BaseInfo;

public class MyClassActivity extends TitleActivity implements OnClickListener{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	private ListView listView;


	private String url = "stuListCourse.do";
	private HttpUtils http = new HttpUtils();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myclass);
		setTitle("我的课程");
		showBackwardView(R.string.button_backward, true);//设置左上角返回箭头生效
		//查看文件teacher 查看用户id
		SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
		String sId = sharedPreferences.getString("userId", "0");

		//通过访问服务器，获取数据
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("sid", sId);
		final BaseInfo baseInfo = (BaseInfo)getApplication();
		listView=(ListView)this.findViewById(R.id.listview); 
		GetData(baseInfo.getUrl()+url, params);

		//为每个item设置监听器
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Map map = (Map)listView.getItemAtPosition(arg2);
				int cid = (Integer)map.get("cid");
				String cname = (String)map.get("cname");
				Intent intent = new Intent();
				intent.setClass(MyClassActivity.this, MyClassDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("cId",cid);
				bundle.putString("cName",cname);
				intent.putExtras(bundle);
				startActivity(intent);

			}

		});




	}

	@Override
	protected void onBackward(View backwardView) {
		super.onBackward(backwardView);
	}

	private void GetData(String URL, RequestParams params){
		http.send(HttpRequest.HttpMethod.GET,
				URL,
				params,
				new RequestCallBack<String>() {

			@Override
			public void onStart() {
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {				
				List<Map<String, Object>> myclass = getMaps("courses", responseInfo.result);

				SimpleAdapter adapter = new SimpleAdapter(MyClassActivity.this,myclass,R.layout.activity_myclass_items,
						new String[]{"cname"},
						new int[]{R.id.CName});
				listView.setAdapter(adapter);
			}
			@Override
			public void onFailure(HttpException error, String msg) {
			}
		});

	}



	public static List<Map<String, Object>> getMaps(String key,  
			String jsonString) {  
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  

		try {  
			JSONObject jsonObject = new JSONObject(jsonString);  
			JSONArray mapsArray = jsonObject.getJSONArray(key);  
			for (int i = 0; i < mapsArray.length(); i++) {  
				JSONObject jsonObject2 = mapsArray.getJSONObject(i);  
				Map<String, Object> map = new HashMap<String, Object>();  
				// 查看Map中的键值对的key值  
				Iterator<String> iterator = jsonObject2.keys();  

				while (iterator.hasNext()) {  
					String json_key = iterator.next();  
					Object json_value = jsonObject2.get(json_key);  
					if(json_value==null){  
						//当键值对中的value为空值时，将value置为空字符串；  
						json_value="";  
					}  
					map.put(json_key, json_value);  
				}  
				list.add(map);  
			}  
		} catch (Exception e) {  
			// TODO: handle exception  
		}  
		return list;  
	}  



}
