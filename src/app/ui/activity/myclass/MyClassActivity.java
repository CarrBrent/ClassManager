package app.ui.activity.myclass;

import java.util.ArrayList;
import java.util.HashMap;
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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import app.ui.TitleActivity;

public class MyClassActivity extends TitleActivity implements OnClickListener{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	
	private ListView listView;
	

	// 基本地址：服务器ip地址：端口号/Web项目逻辑地址+目标页面（Servlet）的url-pattern
	private String baseURL = "http://219.216.65.200:8080/spring/helloworld.do";
	private HttpUtils http = new HttpUtils();
	List<HashMap<String, String>> myclass = new ArrayList<HashMap<String, String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myclass);
		setTitle("我的课程");
		showBackwardView(R.string.button_backward, true);//设置左上角返回箭头生效
		
        //通过访问服务器，获取数据
        RequestParams params = new RequestParams();
		params.addQueryStringParameter("username", "jaishil");

		http.send(HttpRequest.HttpMethod.GET,
				baseURL,
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
				
			}


			@Override
			public void onFailure(HttpException error, String msg) {
				
			}
		});
		
		
		 //将数据适配到相应的ListView
		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.activity_myclass_items,
                new String[]{"classname"},
                new int[]{R.id.classname});
		listView=(ListView)this.findViewById(R.id.listview);
		listView.setAdapter(adapter);


	}
	private List<HashMap<String, String>> getData() {
		
        List<HashMap<String, String>> myclass = new ArrayList<HashMap<String, String>>();
 
        HashMap<String, String> map = new HashMap<String, String>();
		map.put("classname", "数据仓库");
		myclass.add(map);
		
		map = new HashMap<String, String>();
		map.put("classname", "机器学习");
		myclass.add(map);
		
		map = new HashMap<String, String>();
		map.put("classname", "数据结构");
		myclass.add(map);
		
		map = new HashMap<String, String>();
		map.put("classname", "软件重构技术");
		myclass.add(map);
         
        return myclass;
    }
	

	@Override
	protected void onBackward(View backwardView) {
		super.onBackward(backwardView);
	}

}
