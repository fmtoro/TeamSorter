package com.ftpha.teamsorter;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Fernando on 2015-04-30.
 * good things do happen
 */
public class ftFileIO {

    public static final String fileN = "srtData";
    private static final int MODE_PRIVATE = 1;


    public static String[] readD(Activity a) {

        try {
            FileInputStream fis = a.openFileInput(fileN);
            BufferedInputStream bis = new BufferedInputStream(fis);

            StringBuffer stringBuffer = new StringBuffer();

            while (bis.available() != 0) {
                char c = (char)bis.read();
                stringBuffer.append(c);
            }
            bis.close();
            fis.close();

            JSONArray data = new JSONArray(stringBuffer.toString());


            String[] iArr = new String[data.length()];
            for (int i = 0; i < data.length(); i++) {
                iArr[i] = data.getJSONObject(i).getString("Nombre");
           }

            return iArr;

        } catch (IOException e) {
            return  null;
        } catch (JSONException e) {
            return  null;
        }

    }


    public static int writeD(Activity a, String[] dataToSave) {
//
//        if it saves OK returns 1 else 0
//
//
//

        File f = a.getFilesDir();
        String path = f.getAbsolutePath();

        JSONArray data = new JSONArray();
        JSONObject team;

        team = new JSONObject();

        try {
            for (int i = 0; 1 < dataToSave.length; i++) {
                team.put("Nombre", dataToSave[i]);
                team.put("Imagen", "N/A");
                data.put(team);
            }

            String txt = data.toString();

            FileOutputStream fos = a.openFileOutput(fileN, MODE_PRIVATE);
            fos.write(txt.getBytes());

            fos.close();

            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
