package com.ftpha.teamsorter;

import android.app.Activity;
import android.provider.ContactsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Fernando on 2015-04-30.
 */
public class ftFileIO {

    public static final String fileN = "srtData";



    public static int saveD(Activity a, String dataToSave) {
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
            team.put("Nombre", "Perros");
            team.put("Imagen", "N/A");
            data.put(team);

            team = new JSONObject();
            team.put("Nombre", "Gatos");
            team.put("Imagen", "N/A");
            data.put(team);

            team = new JSONObject();
            team.put("Nombre", "Ratones");
            team.put("Imagen", "N/A");
            data.put(team);

            String txt = data.toString();

            FileOutputStream fos = openFileOutput("dataDeFT.txt",MODE_PRIVATE);
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
