package app.ui.activity.barcode;


import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import myclass.manager.R;
import app.ui.TitleActivity;
import app.ui.activity.barcode.CaptureActivity;
import app.ui.activity.myclass.MyClassActivity;
import app.ui.activity.myclass.SelectedClassActivity;
import app.ui.activity.myclass.SeminarActivity;
import app.ui.activity.myclass.SeminarDetailActivity;
import app.util.BaseInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends TitleActivity {
    /** Called when the activity is first created. */
	private TextView resultTextView;
	private String url = "stuSignIn.do";
	private HttpUtils http = new HttpUtils();
	private String seId = null;
	private String seName = null;
	private String cId = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        setTitle("课程签到");
		showBackwardView(R.string.button_backward, true);//设置左上角返回箭头生效
        
		http.configCurrentHttpCacheExpiry(1000);
        resultTextView = (TextView) this.findViewById(R.id.tv_scan_result);
        
        Button scanBarCodeButton = (Button) this.findViewById(R.id.btn_scan_barcode);
        
        scanBarCodeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(SignInActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
    }
	@Override
	protected void onBackward(View backwardView) {
		super.onBackward(backwardView);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(bundle.getString("result"));
				seId = jsonObject.getString("seId");
				seName = jsonObject.getString("seName");
				cId = jsonObject.getString("cId");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
	    	String userId = sharedPreferences.getString("userId", "null");
			//通过访问服务器，获取数据
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("cid",cId);
			params.addQueryStringParameter("seid",seId);
			params.addQueryStringParameter("sid",userId);
			final BaseInfo baseInfo = (BaseInfo)getApplication();
			http.send(HttpRequest.HttpMethod.GET,
					baseInfo.getUrl()+url,
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
							resultTextView.setText("签到失败");
						}else {
							Intent intent = new Intent();
							intent.setClass(SignInActivity.this, SeminarDetailActivity.class);
							Bundle bundle = new Bundle();
							bundle.putInt("seId",Integer.parseInt(seId));
							bundle.putString("seName",seName);
							intent.putExtras(bundle);
							startActivity(intent);
							resultTextView.setText("签到成功");
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
				}
				@Override
				public void onFailure(HttpException error, String msg) {
					resultTextView.setText("签到失败");
				}
			});
			
		}
	}
}