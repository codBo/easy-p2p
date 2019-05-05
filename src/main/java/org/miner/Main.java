package main.java.org.miner;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class Main {

    static Random r = new Random();

    public static void main(String[] args) {

        int dificulty = 3;
        String zeros = String.join("", Collections.nCopies(dificulty, "0"));
        System.out.println(zeros);

        Date start = new Date();
        long times = 0;
        while (true) {
            String sha = getSHA256StrJava(r.nextInt() + "");
            System.out.println(sha);
            times++;
            if (sha.startsWith(zeros)) {
                break;
            }
        }
        Date end = new Date();
        long use = end.getTime() - start.getTime();

        System.out.println(times + " in " + use + "ms with dificulty " + dificulty);

    }
    public static String getSHA256StrJava(String content){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(content.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
