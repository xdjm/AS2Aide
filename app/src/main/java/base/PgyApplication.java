package base;
import android.app.Application;
import com.pgyersdk.crash.PgyCrashManager;

/**
 * Created by yjm on 2017/1/14.
 */

public class PgyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);
    }
}
