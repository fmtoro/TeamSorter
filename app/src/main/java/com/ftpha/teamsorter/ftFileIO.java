package com.ftpha.teamsorter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Fernando on 2015-04-30.
 * good things do happen
 */
public class ftFileIO {

    private static final String fileN = "srtData.json";
    private static final int MODE_PRIVATE = 1;

public static String fileName(){
    return fileN;
}

    public static String[] readD(Activity a, int nOfT) throws IOException, JSONException {

        FileInputStream fis = a.openFileInput(fileN);
        BufferedInputStream bis = new BufferedInputStream(fis);

        StringBuilder stringBuilder = new StringBuilder();

        while (bis.available() != 0) {
            char c = (char) bis.read();
            stringBuilder.append(c);
        }
        bis.close();
        fis.close();



        JSONArray data = new JSONArray(stringBuilder.toString());
        String[] iArr = new String[nOfT];
        for (int i = 0; i < nOfT; i++) {
            if (i >= data.length()) {
                iArr[i] = "Team " + (i + 1);
            } else {
                iArr[i] = data.getJSONObject(i).getString("Nombre");
            }
        }

        return iArr;

    }


    public static int writeD(Activity a, String[] dataToSave) throws JSONException, IOException {
//
//        if it saves OK returns 1 else 0
//
//
//

        File f = a.getFilesDir();
        //String path = f.getAbsolutePath();

        JSONArray data = new JSONArray();
        JSONObject team;


            for (int i = 0; i < dataToSave.length; i++) {
                team = new JSONObject();
                team.put("Nombre", dataToSave[i]);
                team.put("Imagen", "N/A");
                data.put(team);
            }

            String txt = data.toString();

            FileOutputStream fos = a.openFileOutput(fileN, MODE_PRIVATE);
            fos.write(txt.getBytes());

            fos.close();

            return 1;

    }
}
