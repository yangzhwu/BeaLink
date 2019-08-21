package com.bealink.zhengwuy.bealink.constant;

/**
 * Created by zhengwuy on 2017/2/20.
 * Emali: 963460692@qq.com
 * description:常量类
 */

public class Constants {

    /**
     * Created by zhengwuy on 2017/2/20.
     * Emali: 963460692@qq.com
     * description:存储文件相关的常量
     */

    public static class FileConstant {
        public static final String PROVINCE_FILE_NAME = "province.txt";
        public static final String CITY_FILE_NAME = "city.txt";
        public static final String COUNTY_FILE_NAME = "county.txt";
    }

    /**
     * Created by zhengwuy on 2017/2/19.
     * Email: 963460692@qq.com
     * description: 存储sharedPreference的key
     */
    public static class SharedPreferenceKeyConstant {
        public static final String KEY_HAS_LOAD_DATA = "has_load_data";
        public static final String KEY_CHOOSE_COUNTY_WEATHER_ID = "choose_county_weather_id";
        public static final String KEY_CHOOSE_COUNTY_NAME = "choose_county_name";
    }

    /**
     * Created by zhengwuy on 2017/1/31.
     * Email: 963460692@qq.com
     * description: http api的常量
     */

    public static class ApiConstant {
        public static final String URL = "http://www.wordbl.com:8080/bealink/";

        public static final int CODE_SERVER_RESPONSE_OK = 200;
    }

    public static class Key {
        public static final String KEY_OP_FRIENDS = "op_friends";
    }
}
