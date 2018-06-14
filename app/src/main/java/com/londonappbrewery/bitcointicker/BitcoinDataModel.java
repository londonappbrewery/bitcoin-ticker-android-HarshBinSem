package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

public class BitcoinDataModel {
private double displayPrice;
public static BitcoinDataModel fromJson(JSONObject jsonObject){
    BitcoinDataModel bitcoinData=new BitcoinDataModel();
    try {
        bitcoinData.displayPrice=jsonObject.getDouble("last");
        return bitcoinData;
    } catch (JSONException e) {
        e.printStackTrace();
        return null;
    }

}

    public double getDisplayPrice() {
        return displayPrice;
    }
}
