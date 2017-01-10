package com.logto.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class GPSService extends Service {
	private LocationManager lm;
	private SharedPreferences sp;

	@Override
	public void onCreate() {
		super.onCreate();
		sp = getSharedPreferences("config", MODE_PRIVATE);
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		// �����õĶ�λЧ��  
		criteria.setAccuracy(Criteria.ACCURACY_FINE);  
		criteria.setAltitudeRequired(false);  
		criteria.setBearingRequired(false);  
		criteria.setCostAllowed(false);
		// ʹ��ʡ��ģʽ  
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		//�ڷ����м���λ�õı仯
		String provider = lm.getBestProvider(criteria, true);
		lm.requestLocationUpdates(provider,6000, 0, listener);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		lm.removeUpdates(listener);
		listener = null;
	}

	private LocationListener listener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(Location location) {
			String longitude = "Longitude:" + location.getLongitude() + "\n";
			String latitude = "Latitude:" + location.getLatitude() + "\n";
			String accuracy = "accuracy:" + location.getAccuracy() + "\n";

			//λ�ñ仯--�Ƚ�λ�ñ��棬Ȼ�󷢶��Ÿ���ȫ����
			Editor editor = sp.edit();
			editor.putString("LOCATION", longitude+latitude+accuracy);
			editor.commit();
		}
	};
}
