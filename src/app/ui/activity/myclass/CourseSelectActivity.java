package app.ui.activity.myclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONString;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import app.ui.TitleActivity;
import app.util.BaseInfo;

public class CourseSelectActivity extends TitleActivity implements OnClickListener{
	private int cId;
	private int count=0;
	private int current = 0;
	private String cName;
	private List<Map<String, Object>> currentSeminars= new ArrayList<Map<String,Object>>();
	private List<List<Map<String,Object>>> selectseminars = new ArrayList<List<Map<String,Object>>>();
	private String stuListSelectSeminarString = "stuListSelectSeminar.do";
	private String submitString = "stuSelectSeminar.do";
	private HttpUtils http = new HttpUtils();
	private BaseInfo baseInfo;
	private ListView listView;
	private Button submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_select);
		showBackwardView(R.string.button_backward, true);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		cId = bundle.getInt("cId");
		cName = bundle.getString("cName");
		setTitle(cName);

		submit = (Button)this.findViewById(R.id.submit);
		submit.setOnClickListener(this);


		// 设置缓存1秒,1秒内直接返回上次成功请求的结果
		http.configCurrentHttpCacheExpiry(1000);

		//通过访问服务器，获取数据
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("cid", Integer.toString(cId));
		baseInfo= (BaseInfo)getApplication();
		listView=(ListView)this.findViewById(R.id.listview); 
		GetData(baseInfo.getUrl()+stuListSelectSeminarString, params);

	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.submit:
			submit();
			if (current >= count-1) {
				Toast.makeText(this, "选课完成", 0).show();
				finish();
			}else {
				current++;
				CourseSelectAdapter adapter = new CourseSelectAdapter(CourseSelectActivity.this, getSeNames(selectseminars.get(current)));  
				listView.setAdapter(adapter);
				listView.setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}

	}
	@Override
	protected void onBackward(View backwardView) {
		super.onBackward(backwardView);
	}
	private void submit(){
		SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
		String sId = sharedPreferences.getString("userId", "null");
		String seId = "";
		// 根据RadioButton的选择情况确定用户名  
		for (int i = 0, j = listView.getCount(); i < j; i++) {  
			View child = listView.getChildAt(i);  
			RadioButton rdoBtn = (RadioButton) child.findViewById(R.id.radio_btn);  
			if (rdoBtn.isChecked())  
				seId = Integer.toString((Integer)selectseminars.get(current).get(i).get("seId"));
		} 
		RequestParams submitParams = new RequestParams();
		submitParams.addQueryStringParameter("cid", Integer.toString(cId));
		submitParams.addQueryStringParameter("sid", sId);
		submitParams.addQueryStringParameter("seid", seId);
		if (seId=="") {
			Toast.makeText(this, "请选择研讨课", 0).show();
		}else {
			http.send(HttpRequest.HttpMethod.GET,
					baseInfo.getUrl()+submitString,
					submitParams,
					new RequestCallBack<String>() {

				@Override
				public void onStart() {
				}

				@Override
				public void onLoading(long total, long current, boolean isUploading) {
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {

				}
				@Override
				public void onFailure(HttpException error, String msg) {
				}
			});

		}




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
				try {
					JSONObject jsonObject = new JSONObject(responseInfo.result);
					JSONArray seminarsJsonArray = new JSONArray(jsonObject.getString("selectseminars"));
					count =  seminarsJsonArray.length();
					current = 0;
					List<Map<String, Object>> seminars= new ArrayList<Map<String,Object>>();
					for (int i = 0; i < seminarsJsonArray.length(); i++) {
						seminars = getList(seminarsJsonArray.get(i).toString());
						selectseminars.add(seminars);
					}
					CourseSelectAdapter adapter = new CourseSelectAdapter(CourseSelectActivity.this, getSeNames(selectseminars.get(0)));  
					listView.setAdapter(adapter);
					listView.setVisibility(View.VISIBLE);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
			@Override
			public void onFailure(HttpException error, String msg) {
			}
		});

	}

	public List<String> getSeNames(List<Map<String, Object>> seminars) {
		List<String> seNames = new ArrayList<String>();
		for (int i = 0; i < seminars.size(); i++) {
			seNames.add(seminars.get(i).get("seName").toString());
		}
		return seNames;
	}

	public static List<Map<String, Object>> getList(String jsonString) {  
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  

		try {  
			JSONArray mapsArray = new JSONArray(jsonString);  
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
