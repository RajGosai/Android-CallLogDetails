package raj.callfinale;

import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.CallLog;
import android.util.Log;

public class myservice extends Service {
	boolean isServiceCall = false;
	String Tag = "Service Flow";

	public myservice() {

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not at implement");

	}

	public void onCreate() {
//		Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG)
//				.show();

		Log.i(Tag, "Create");
		isServiceCall = true;
	}

	public void onStart(Intent intent, int startID) {
		if (isServiceCall) {
			Log.i(Tag, "onStart");
			
			StringBuffer sb = new StringBuffer();
			Cursor managedCursor = this.getContentResolver().query(
					CallLog.Calls.CONTENT_URI, null, null, null, null);
			int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
			int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
			int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
			int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
			sb.append("Call Details :");

			managedCursor.moveToLast();
			String phNumber = managedCursor.getString(number);
			String callType = managedCursor.getString(type);
			String callDate = managedCursor.getString(date);
			Date callDayTime = new Date(Long.valueOf(callDate));
			String callDuration = managedCursor.getString(duration);
			String dir = null;
			int dircode = Integer.parseInt(callType);
			switch (dircode) {
			case CallLog.Calls.OUTGOING_TYPE:
				dir = "OUTGOING";
				break;

			case CallLog.Calls.INCOMING_TYPE:
				dir = "INCOMING";
				break;

			case CallLog.Calls.MISSED_TYPE:
				dir = "MISSED";
				break;
			}
			sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
					+ dir + " \nCall Date:--- " + callDayTime
					+ " \nCall duration in sec :--- " + callDuration);
			sb.append("\n----------------------------------");

			// txt.setText(sb);

			Log.i("Result", sb.toString());
			isServiceCall = false;
		}
	}

	public void onDestroy() {
		Log.i(Tag, "onDestroy");
		
		super.onDestroy();
	}
}
