package app.ui.activity.score;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import app.ui.TitleActivity;
import app.util.BaseInfo;

public class ScoreActivity extends TitleActivity implements OnClickListener{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private ListView listView;
	private String scoreString = "stuFindMySeminarScore.do";
	private HttpUtils http = new HttpUtils();
	private BaseInfo baseInfo;
	private String seName;
	private String seId;
	private TextView tvScore;
	private Double totalScore;
	private Integer intTotalScore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		showBackwardView(R.string.button_backward, true);

		// 设置缓存1秒,1秒内直接返回上次成功请求的结果
		http.configCurrentHttpCacheExpiry(1000);
		baseInfo=(BaseInfo)getApplication();
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		seName = bundle.getString("seName");
		seId= Integer.toString(bundle.getInt("seId"));
		setTitle(seName);


		tvScore =(TextView)this.findViewById(R.id.score);
		listView=(ListView)this.findViewById(R.id.listview); 

		SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
		String sId = sharedPreferences.getString("userId", "0");
		//设置访问服务器时需要传递的参数
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("seid",seId);
		params.addQueryStringParameter("sid",sId);
		//通过访问服务器，获取数据
		GetData(baseInfo.getUrl()+scoreString, params);


	}

	@Override
	public void onClick(View v) {
		super.onClick(v);//实现父类的onClick方法这样才可使使左上角的返回按钮生效
		switch (v.getId()) {
		default:
			break;
		}

	}


	@Override
	protected void onBackward(View backwardView) {
		super.onBackward(backwardView);
	}
	//从服务器获取数据
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
					JSONObject seminarscore = (JSONObject) jsonObject.get("seminarscore");
					if (seminarscore.length()==0) {
						tvScore.setText("成绩尚未得出");
					}else{
						try {
							totalScore =(Double)seminarscore.get("totalScore");
							tvScore.setText(Double.toString(totalScore));
							
						} catch (Exception e) {
							intTotalScore =(Integer)seminarscore.get("totalScore");
							tvScore.setText(Integer.toString(intTotalScore));
						}
						
						
						//获取评价列表
						List<Map<String, Object>> groups = getMaps("elementScores", seminarscore.toString());
						SimpleAdapter adapter = new SimpleAdapter(ScoreActivity.this,groups,R.layout.activity_score_items,
								new String[]{"eename","eescore"},
								new int[]{R.id.grName,R.id.stNames});
						listView.setAdapter(adapter);
					}
						
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					tvScore.setText("成绩尚未得出");
					e.printStackTrace();
				}
				
				
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
