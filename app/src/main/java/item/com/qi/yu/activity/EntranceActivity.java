package item.com.qi.yu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qiyukf.nimlib.sdk.NimIntent;

import item.com.qi.yu.MainActivity;

/**
 *
 */
public class EntranceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent(); // 网易七鱼
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        parseIntent();
    }

    /**
     * 进入七鱼
     */
    private void parseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            MainActivity.consultService(this, null);
            // 最好将intent清掉，以免从堆栈恢复时又打开客服窗口
            setIntent(new Intent());
            EntranceActivity.this.finish();
        }
    }
}
