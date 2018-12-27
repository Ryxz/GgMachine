package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import service.LongRunningService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LongRunningService.class);
        context.startService(i);
    }
}
