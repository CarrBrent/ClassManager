package app.ui.activity.myclass;

import myclass.manager.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import app.ui.TitleActivity;

public class SelectedClassActivity extends TitleActivity implements OnClickListener{
	/* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectedclass);
        setTitle("课程选项");
        showBackwardView(R.string.button_backward, true);
        
        
        
        
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);//实现父类的onClick方法这样才可使使左上角的返回按钮生效
        switch (v.getId()) {
		case R.id.layout_class1:
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
