package my.com.aimerfarid.emirweatherapp;

/**
 * Created by Emir Rodzi on 8/13/2018.
 */

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GeoActivity extends AppCompatActivity {
    public static final String EXTRA_LAT = "my.com.aimerfarid.emirweatherapp.EXTRA_LAT";
    public static final String EXTRA_LNG = "my.com.aimerfarid.emirweatherapp.EXTRA_LNG";
    private String locationAddress;

    Button addressButton;
    TextView addressTV;
    TextView latLongTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        addressTV = (TextView) findViewById(R.id.addressTV);
        latLongTV = (TextView) findViewById(R.id.latLongTV);

        addressButton = (Button) findViewById(R.id.addressButton);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                EditText editText = (EditText) findViewById(R.id.addressET);
                String address = editText.getText().toString();

                GeoLocation locationAddress = new GeoLocation();
                locationAddress.getAddressFromLocation(address, getApplicationContext(), new GeoHandler());
            }
        });

//        Button button = (Button) findViewById(R.id.weatherButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                openWeatherActivity();
//            }
//        });
    }

    public void passData(View v) {
        String coord = locationAddress.substring(locationAddress.lastIndexOf(" ")+2);
        int i = coord.indexOf("\n",1);
        String latW = coord.substring(1, i);
        String lngW = coord.substring((i+1), coord.length()-1);

        Intent passdata_intent = new Intent(this, WeatherActivity.class);

        passdata_intent.putExtra("latdata", latW);
        passdata_intent.putExtra("lngdata", lngW);

        startActivity(passdata_intent);
    }

    public void openWeatherActivity() {
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    public class GeoHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            //String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            String lng = locationAddress.substring(locationAddress.lastIndexOf(" ") + 2);
            int i = lng.indexOf("\n",1);
            String word = lng.substring(1, i);
            String rest = lng.substring((i + 1), lng.length()-1);
//            latLongTV.setText(rest);
            latLongTV.setText(locationAddress);
        }
    }

    public String passLat() {
        String coord = locationAddress.substring(locationAddress.lastIndexOf(" ")+2);
        int i = coord.indexOf("\n",1);
        String lat = coord.substring(1, i);
        return lat;
    }

    public String passLong() {
        String coord = locationAddress.substring(locationAddress.lastIndexOf(" ")+2);
        int i = coord.indexOf("\n",1);
        String lng = coord.substring((i+1), coord.length()-1);
        return lng;
    }

    public void passLongLat(String latW, String lngW) {
        String coord = locationAddress.substring(locationAddress.lastIndexOf(" ")+2);
        int i = coord.indexOf("\n",1);
        latW = coord.substring(1, i);
        lngW = coord.substring((i+1), coord.length()-1);
    }
}
