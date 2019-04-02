package item.com.qi.yu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qiyukf.nimlib.sdk.NimIntent;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.ProductDetail;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnreadCountChangeListener;

import item.com.qi.yu.activity.OtherActivity;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        // 监听未读的数量
        Unicorn.addUnreadCountChangeListener(mUnreadCountListener,true);
        updateUnreadCount(Unicorn.getUnreadCount());
    }

    private void findViews() {
        button = findViewById(R.id.btnText);
        textView = findViewById(R.id.number);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultService(MainActivity.this, null);
            }
        });
        findViewById(R.id.btnOther).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调到其他界面，然后来消息提示 点击状态栏 七鱼怎么处理的
                Intent intent = new Intent(MainActivity.this,OtherActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    /**
     *
     * @param context
     * @param productDetail
     */
    public static void consultService(final Context context, ProductDetail productDetail) {
        // 启动聊天界面
        String title = "聊天窗口的标题";

        ConsultSource source = new ConsultSource("Main", title, null);

        source.productDetail = productDetail;

        Unicorn.openServiceActivity(context, title, source);
    }


    private UnreadCountChangeListener mUnreadCountListener = new UnreadCountChangeListener() {
        @Override
        public void onUnreadCountChange(int count) {
            updateUnreadCount(count);
        }
    };
    private void updateUnreadCount(int count) {
        if (count > 99) {
            textView.setText("联系客服" + "(99+)");
        } else if (count > 0) {
            textView.setText("联系客服" + "(" + count + ")");
        } else {
            textView.setText("联系客服");
        }
    }

    @Override
    protected void onDestroy() {
        Unicorn.addUnreadCountChangeListener(mUnreadCountListener,false);
        // 调用Unicorn.toggleNotification(true)重新打开
        // Unicorn.toggleNotification(false); // APP退出 不需要添加提示
        super.onDestroy();
    }
}
