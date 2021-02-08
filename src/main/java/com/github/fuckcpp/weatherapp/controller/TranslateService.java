package com.github.fuckcpp.weatherapp.controller;/*
作者Crying711
日期:2021/2/7
时间:19:54
*/

import com.github.fuckcpp.weatherapp.natives.Weather;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The type Translate service.
 */
@Service
@Slf4j
public class TranslateService
{

    /**
     * Sets text.
     *
     * @param text the text
     * @return the text  设置要翻译的文字
     */
    public TranslateService setText(String text) {

        this.text = text;

        return  this;

    }

    private String text;

    private static int[] TKK() {
        return new int[]{406398, 2087938574};
    }

    private static int shr32(int x, int bits) {
        if (x < 0) {
            long x_l = 4294967295L + (long)x + 1L;
            return (int)(x_l >> bits);
        } else {
            return x >> bits;
        }
    }

    private static int RL(int a, String b) {
        for(int c = 0; c < b.length() - 2; c += 3) {
            int d = b.charAt(c + 2);
            d = d >= 'A' ? d - 87 : d - 48;
            d = b.charAt(c + 1) == '+' ? shr32(a, d) : a << d;
            a = b.charAt(c) == '+' ? a + (d & -1) : a ^ d;
        }

        return a;
    }

    private static String generateToken(String text) {
        int[] tkk = TKK();
        int b = tkk[0];
        int e = 0;
        int f = 0;

        ArrayList d;
        int g;
        for(d = new ArrayList(); f < text.length(); ++f) {
            g = text.charAt(f);
            if (128 > g) {
                d.add(e++, g);
            } else {
                if (2048 > g) {
                    d.add(e++, g >> 6 | 192);
                } else if (55296 == (g & 'ﰀ') && f + 1 < text.length() && 56320 == (text.charAt(f + 1) & 'ﰀ')) {
                    int var10000 = 65536 + ((g & 1023) << 10);
                    ++f;
                    g = var10000 + (text.charAt(f) & 1023);
                    d.add(e++, g >> 18 | 240);
                    d.add(e++, g >> 12 & 63 | 128);
                } else {
                    d.add(e++, g >> 12 | 224);
                    d.add(e++, g >> 6 & 63 | 128);
                }

                d.add(e++, g & 63 | 128);
            }
        }

        g = b;

        for(e = 0; e < d.size(); ++e) {
            g += (Integer)d.get(e);
            g = RL(g, "+-a^+6");
        }

        g = RL(g, "+-3^+b+-f");
        g ^= tkk[1];
        long a_l;
        if (0 > g) {
            a_l = 2147483648L + (long)(g & 2147483647);
        } else {
            a_l = (long)g;
        }

        a_l = (long)((double)a_l % Math.pow(10.0D, 6.0D));
        return String.format(Locale.US, "%d.%d", a_l, a_l ^ (long)b);
    }

    /**
     * 拼接要请求的URL 其中使用tkk 算法
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    private String Url() throws UnsupportedEncodingException
    {
        String url = null;
        String encoded = URLEncoder.encode(this.text, "UTF-8");
        String token = generateToken(this.text);
        url = Weather.WEATHER.translate_api_key(encoded)+token;
        System.out.println(url);
        return  url;
    }

    /**
     * Translate string.
     * 请求URL 获取JSON 数据 并用正则拆分
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public  String translate() throws UnsupportedEncodingException {
        OkHttpClient    client = new OkHttpClient();  //using OKHTTP dependency . You have to add this mannually form OKHTTP website
        Request request = new Request.Builder()
                .url(this.Url())
                .build();

        try {
            Response response = client.newCall(request).
                    execute();
            return this.resultset(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }

    return null;
    }
    private String resultset(String Tobematched)
    {
        String pattern = "\\[\\[\\[\"(.*?)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(Tobematched);
        if (m.find( ))
        {
            return m.group(1);
        }

        return null;

    }

}
