package item.com.qi.yu.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.qiyukf.unicorn.api.OnBotEventListener;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFOptions;

import item.com.qi.yu.R;
import item.com.qi.yu.activity.EntranceActivity;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 如果这里不想要通知栏提醒，Options直接写null了
        Unicorn.init(this, "6fd48dbe10b74d03d10857e79fd3b7b0", ysfOptions(), new GlideImageLoader(this));
    }

    /**
     * 当用户不处在聊天界面时，收到客服的消息，App 应当在通知栏或者聊天入口给出提醒。
     * 通知栏提醒可以显示最近一条消息的内容，并提供给用户快速进入 App 的入口。
     */
    private YSFOptions ysfOptions() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.statusBarNotificationConfig.notificationEntrance = EntranceActivity.class; // 这里是APP快速入口
        options.statusBarNotificationConfig.notificationSmallIconId = R.drawable.ic_launcher_background;
        options.onBotEventListener = new OnBotEventListener() {
            @Override
            public boolean onUrlClick(Context context, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
                return true;
            }
        };
        return options;
    }
}
