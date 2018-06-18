package com.bealink.zhengwuy.bealink.utils;

/**
 * Created by zhengwuy on 2018/6/17.
 * Email: 963460692@qq.com
 * description:
 */
public class PinYinUtil {
//    /**
//     * 将hanzi转成拼音
//     *
//     * @param hanzi 汉字或字母
//     * @return 拼音
//     */
//    public static String getPinyin(String hanzi) {
//        StringBuilder sb = new StringBuilder();
//        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
//        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        //由于不能直接对多个汉子转换，只能对单个汉子转换
//        char[] arr = hanzi.toCharArray();
//        for (int i = 0; i < arr.length; i++) {
//            if (Character.isWhitespace(arr[i])) {
//                continue;
//            }
//            try {
//                String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(arr[i], format);
//                if (pinyinArr != null) {
//                    sb.append(pinyinArr[0]);
//                } else {
//                    sb.append(arr[i]);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                //不是正确的汉字
//                sb.append(arr[i]);
//            }
//
//        }
//        return sb.toString();
//    }
}
