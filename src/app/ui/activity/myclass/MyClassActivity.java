package app.ui.activity.myclass;

import mobi.kuaidian.qunakao.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import app.ui.TitleActivity;

public class MyClassActivity extends TitleActivity implements OnClickListener{
	/* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclass);
        setTitle("我的课程");
        showBackwardView(R.string.button_backward, true);
        
        
        
        
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);//实现父类的onClick方法这样才可使使左上角的返回按钮生效
        switch (v.getId()) {
		case R.id.layout_class1:
			Intent intent1 = new Intent(MyClassActivity.this,SelectedClassActivity.class);
			MyClassActivity.this.startActivity(intent1);
			break;
		case R.id.layout_class2:
			Intent intent2 = new Intent(MyClassActivity.this,SelectedClassActivity.class);
			MyClassActivity.this.startActivity(intent2);
			break;
		case R.id.layout_class3:
			Intent intent3 = new Intent(MyClassActivity.this,SelectedClassActivity.class);
			MyClassActivity.this.startActivity(intent3);
			break;
		case R.id.layout_class4:
			Intent intent4 = new Intent(MyClassActivity.this,SelectedClassActivity.class);
			MyClassActivity.this.startActivity(intent4);
			break;
		case R.id.layout_class5:
			Intent intent5 = new Intent(MyClassActivity.this,SelectedClassActivity.class);
			MyClassActivity.this.startActivity(intent5);
			break;

		default:
			break;
		}
        
    }
    
    
    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
    }

}
