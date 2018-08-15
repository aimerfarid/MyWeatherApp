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
    public static final String EXTRA_TEXT = "my.com.aimerfarid.emirweatherapp.EXTRA_TEXT";


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

        Button button = (Button) findViewById(R.id.weatherButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeatherActivity();
            }
        });
    }

    public void openWeatherActivity() {
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    private class GeoHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            latLongTV.setText(locationAddress);
        }
    }
}
