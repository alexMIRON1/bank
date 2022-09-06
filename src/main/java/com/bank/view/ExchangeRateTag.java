package com.bank.view;

import org.json.JSONObject;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExchangeRateTag extends SimpleTagSupport {
    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        String sell = getExchangeRate("USD", "UAH");
        String buy = getExchangeRate("UAH", "USD");
        out.println("$"+sell + "/" +String.format("%.4f",1/ Double.parseDouble(buy)).replace(",","."));
    }
    private static String getExchangeRate(String sell, String buy) throws IOException {
        URL url = new URL("https://v6.exchangerate-api.com/v6/dde80aa8f75ebfb52f4f1eec/latest/"+ sell);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));

        String jsonText = readAll(in);
        JSONObject yourData = new JSONObject(jsonText);
        double yourRate = yourData.getJSONObject("conversion_rates").getDouble(buy);
        return String.valueOf(yourRate);
    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
