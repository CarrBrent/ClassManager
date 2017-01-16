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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import app.ui.TitleActivity;
import app.util.BaseInfo;

public class CourseSelectActivity extends TitleActivity implements OnClickListener{
	private int cId;
	private String cName;
	private List<List<Map<String,Object>>> selectseminars;
	private String stuListSelectSeminarString = "stuListSelectSeminar.do";
	private HttpUtils http = new HttpUtils();
	private BaseInfo baseInfo;
	private ListView listView;
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
		//通过访问服务器，获取数据
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("cid", Integer.toString(cId));
		baseInfo= (BaseInfo)getApplication();
		listView=(ListView)this.findViewById(R.id.listview); 
		GetData(baseInfo.getUrl()+stuListSelectSeminarString, params);

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
				try {
					JSONObject jsonObject = new JSONObject(responseInfo.result);
					JSONArray seminarsJsonArray = new JSONArray(jsonObject.getString("selectseminars"));
					for (int i = 0; i < seminarsJsonArray.length(); i++) {
						
						List<Map<String, Object>> seminars = getList(seminarsJsonArray.get(i).toString());
						selectseminars.add(seminars);
					}

					SimpleAdapter adapter = new SimpleAdapter(CourseSelectActivity.this,selectseminars.get(0),R.layout.activity_course_select_items,
							new String[]{"cname"},
							new int[]{R.id.CName});
					listView.setAdapter(adapter);
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
