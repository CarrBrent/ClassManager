package app.ui.activity.evaluate;


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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import app.bean.Evaluation;
import app.ui.TitleActivity;
import app.util.BaseInfo;

public class SelfEvaluateActivity extends TitleActivity implements OnClickListener{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private int seId;
	private int sId;
	private int cId;
	private String seName;
	private String sName;
//	private TextView tvSName; 
//	private TextView tvSId; 
	private Button submit; 
	private String selfevaluatesubmit = "stuSelfEvaluationSubmit.do";
	private String selfevaluatesubmiturl;
	private String findselfevaluatekeys = "stufindSelfEvaluationKeys.do";
	private String findselfevaluatekeysUrl;
	private ListView listView;

	private HttpUtils http = new HttpUtils();
	private BaseInfo baseInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluate_outgroup_detail);
		showBackwardView(R.string.button_backward, true);

		// 设置缓存1秒,1秒内直接返回上次成功请求的结果
		http.configCurrentHttpCacheExpiry(1000);

		baseInfo = (BaseInfo)getApplication();
		selfevaluatesubmiturl = baseInfo.getUrl()+selfevaluatesubmit;
//		tvSName = (TextView) findViewById(R.id.sname);
//		tvSId = (TextView) findViewById(R.id.sId);
		submit = (Button) findViewById(R.id.submit);
		listView=(ListView)this.findViewById(R.id.listview); 

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		seId = bundle.getInt("seId");
		sId = bundle.getInt("sId");
		cId = bundle.getInt("cId");
		seName = bundle.getString("seName");
		sName = bundle.getString("sName");
		setTitle(seName);
//		tvSId.setText(Integer.toString(sId));
//		tvSName.setText(sName);

		//通过访问服务器，获取数据
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("cid", Integer.toString(cId));
		params.addQueryStringParameter("eename", "学生自评" );
		baseInfo = (BaseInfo)getApplication();
		findselfevaluatekeysUrl = baseInfo.getUrl()+findselfevaluatekeys;
		listView=(ListView)this.findViewById(R.id.listview); 
		GetData(findselfevaluatekeysUrl, params);

		submit.setOnClickListener(this);



	}
	@Override
	public void onClick(View v) {
		super.onClick(v);//实现父类的onClick方法这样才可使使左上角的返回按钮生效
		switch (v.getId()) {
		case R.id.submit:
			try {
				submit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
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
				List<Map<String, Object>> keys = getMaps("keys", responseInfo.result);
				SpinnerAdapter spinnerAdapter = new SpinnerAdapter(SelfEvaluateActivity.this, keys, R.layout.activity_evaluate_outgroup_detail_items);
				listView.setAdapter(spinnerAdapter);
			}
			@Override
			public void onFailure(HttpException error, String msg) {
			}
		});

	}
	public void submit() throws JSONException {
		// TODO Auto-generated method stub

		RequestParams params = new RequestParams();
		SpinnerAdapter adapter;
		Map<String,Object> key;
		List<Evaluation> evaluations = new ArrayList<Evaluation>();



		for (int i = 0; i < listView.getCount(); i++) {
			adapter =(SpinnerAdapter)listView.getAdapter();
			key =adapter.getItem(i);
			evaluations.add(new Evaluation(seId,sId,(Integer)key.get("keyId"),(String)key.get("value")));

		}

		params.addQueryStringParameter("selfevaluations",listToJson(evaluations));

		http.send(HttpRequest.HttpMethod.GET,
				selfevaluatesubmiturl,
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
				JSONObject jsonObject;
				try {
					
					jsonObject = new JSONObject(responseInfo.result);
					
					String flag=((Integer)jsonObject.get("flag")).toString();
					if (flag.equals("-1")) {
						Toast.makeText(SelfEvaluateActivity.this, "评价失败", 1).show();
					}else {
						Toast.makeText(SelfEvaluateActivity.this, "评价完成", 1).show();
						finish();
					}
					
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
	public String listToJson(List<Evaluation> evaluations) throws JSONException {
		if(null == evaluations){
			return "";
		}
		JSONArray json = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for(Evaluation evaluation : evaluations){
			JSONObject jo = new JSONObject();
			jo.put("seid", evaluation.getSeId());
			jo.put("eeid", evaluation.getEeId());
			jo.put("object", evaluation.getObject());
			jo.put("evalRank", evaluation.getEvalRank());
			json.put(jo);
		}
		jsonObject.put("selfevaluations", json);
		return jsonObject.toString();
	}
	

	private static List<Map<String, Object>> getMaps(String key,  
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


	@Override
	protected void onBackward(View backwardView) {
		super.onBackward(backwardView);
	}

}
