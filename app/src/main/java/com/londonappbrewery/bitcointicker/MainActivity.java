package com.londonappbrewery.bitcointicker;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";
//    private final String SYMBOL_SET="global";
//    private final String SYMBOL="BTCUSD";

    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Bitcoin", "item is" + parent.getItemAtPosition(position)+"and its position is "+position);
                String currency=(String) parent.getItemAtPosition(position);
                Log.d("Bitcoin","here is the final url on which you are clicking.  "+BASE_URL+currency);
                Log.d("Bitcoin","your parent is  "+parent);
                Log.d("Bitcoin","your view is  "+view);
                Log.d("Bitcoin","your id is  "+id);
                letsDoSomeNetworking(BASE_URL+currency);


//                if (parent.getItemAtPosition(position)=="AUD"){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCAUD");
//                }
//                 if (parent.getItemAtPosition(position).equals("BRL")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCBRL");
//                }
//                 if (parent.getItemAtPosition(position).equals("CAD")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCCAD");
//                }
//                 if (parent.getItemAtPosition(position).equals("CNY")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCCNY");
//                }
//                 if (parent.getItemAtPosition(position).equals("EUR")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCEUR");
//                }
//                 if (parent.getItemAtPosition(position).equals("GBP")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCGBP");
//                }
//                 if (parent.getItemAtPosition(position).equals("HKD")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCHKD");
//                }
//                 if (parent.getItemAtPosition(position).equals("JPY")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCJPY");
//                }
//                 if (parent.getItemAtPosition(position).equals("PLN")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCPLN");
//                }
//                 if (parent.getItemAtPosition(position).equals("RUB")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCRUB");
//                }
//                 if (parent.getItemAtPosition(position).equals("SEK")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCSEK");
//                }
//                 if (parent.getItemAtPosition(position).equals("USD")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCUSD");
//                }
//                 if (parent.getItemAtPosition(position).equals("ZAR")){
//                    letsDoSomeNetworking("https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCZAR");
//                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Bitcoin", "Nothing selected");

            }
        });

    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Bitcoin", "JSON: " + response.toString());
//                BitcoinDataModel weatherData =BitcoinDataModel.fromJson(response);
//                updateUI(weatherData);
                BitcoinDataModel bitcoinData=BitcoinDataModel.fromJson(response);
                updateUI(bitcoinData);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Bitcoin", "Request fail! Status code: " + statusCode);
                Log.d("Bitcoin", "Fail response: " + response);
                Log.e("Bitcoin", e.toString());
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void updateUI(BitcoinDataModel bitcoinData){
        String finalPrice= String.valueOf(bitcoinData.getDisplayPrice());
        mPriceTextView.setText(finalPrice);

    }


}
