package app.ui.activity.exercises;

import org.json.JSONException;
import org.json.JSONObject;

import myclass.manager.R;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import app.ui.TitleActivity;
import app.util.BaseInfo;

public class ExerciseRushActivity extends TitleActivity implements OnClickListener{
	private HttpUtils http = new HttpUtils();

	private BaseInfo baseInfo;
	private String stuBeginResponder = "stuBeginResponder.do";

	private String stuBeginResponderUrl;

	private TextView result;
	private Button start;
	private String seName; 
	private String seId;
	private int rdid;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_rush);
		showBackwardView(R.string.button_backward, true);

		// 设置缓存1秒,1秒内直接返回上次成功请求的结果
		http.configCurrentHttpCacheExpiry(100);
		//获取URL
		baseInfo = (BaseInfo)getApplication();
		stuBeginResponderUrl = baseInfo.getUrl()+stuBeginResponder;

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		seName = bundle.getString("seName");
		seId= Integer.toString(bundle.getInt("seId"));
		setTitle(seName);
		start = (Button)findViewById(R.id.start);
		result = (TextView)findViewById(R.id.result);
		start.setOnClickListener(this);



	}
	public void setRdid(int rdid) {
		this.rdid = rdid;
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);//实现父类的onClick方法这样才可使使左上角的返回按钮生效
		switch (v.getId()) {
		case R.id.start:
			stuBeginResponder();
			break;
		default:
			break;
		}

	}
	public void stuBeginResponder() {
		SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
    	String sId = sharedPreferences.getString("userId", "null");
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("seid",seId);
		params.addQueryStringParameter("sid",sId);
		http.send(HttpRequest.HttpMethod.GET,
				stuBeginResponderUrl,
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
						result.setText("抢答失败");
						start.setClickable(false);
					}else {
						result.setText("抢答成功");
						start.setClickable(false);
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



}
