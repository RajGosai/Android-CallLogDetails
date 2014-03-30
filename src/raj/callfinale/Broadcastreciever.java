package raj.callfinale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Broadcastreciever extends BroadcastReceiver {
	boolean isPhoneRinging = false;
	boolean isPhoneCalling = false;
	
	Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		mContext = context;
		
		try {
			Log.i("BradCast Call", "Calling BroadCats");

			// TELEPHONY MANAGER class object to register one listner
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			isPhoneRinging = true;
			isPhoneCalling = true;
			PhoneStateListener callStateListener = new PhoneStateListener() {
				public void onCallStateChanged(int state, String incomingNumber) {

					if (state == TelephonyManager.CALL_STATE_RINGING) {

						Log.i("Call Result", "Phone Is Riging");
						isPhoneRinging = false;
					}
					if (state == TelephonyManager.CALL_STATE_OFFHOOK) {

						Log.i("Call Result", "Phone is Currently in A call");
						isPhoneCalling = false;
					}

					if (state == TelephonyManager.CALL_STATE_IDLE) {
						Log.i("Call Result",
								"phone is neither ringing nor in a call");

						if (isPhoneRinging) {
							if (isPhoneCalling) {
								Log.i("Call My Service",
										"Call My Service");
								Intent myIntent = new Intent(mContext,
										myservice.class);
								myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

								mContext.stopService(myIntent);
								Handler handler = new Handler();

								handler.postDelayed(new Runnable() {

									@Override
									public void run() {
										
										
										Intent myIntent = new Intent(mContext,
												myservice.class);
										myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

										mContext.startService(myIntent);
									}

								}, 1000);

								isPhoneCalling = false;
								isPhoneRinging = false;
							}
						}

						// if (isPhoneCalling) {
						//
						// Handler handler = new Handler();
						//
						//
						// handler.postDelayed(new Runnable() {
						//
						// @Override
						// public void run() {
						// // get start of cursor
						// Log.i("Call My Service", "Call My Service");
						//
						// }
						// }, 500);

						// isPhoneCalling = false;
						// }
					}

				}
			};
			telephonyManager.listen(callStateListener,
					PhoneStateListener.LISTEN_CALL_STATE);

		} catch (Exception e) {
			Log.e("Phone Receive Error", " " + e);
		}
	}

	public void callService() {
		// TODO Auto-generated method stub

	}
}