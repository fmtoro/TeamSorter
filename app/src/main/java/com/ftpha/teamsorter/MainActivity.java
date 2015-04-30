package com.ftpha.teamsorter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private int ftNoOfPeople = 6;
    private int ftNoOfTeams = 2;

    private EditText noPeepsTxt;
    private EditText TeamsEditTxt;

    private String[] tNames;

    public final static String XtraInfo = "com.ftpha.TeamSorter.MESSAGE";

    @Override
    public void onSaveInstanceState(Bundle oS) {
        super.onSaveInstanceState(oS);

        oS.putInt("ftNoOfPeople", ftNoOfPeople);
        oS.putInt("ftNoOfTeams", ftNoOfTeams);


        try {
            ftFileIO.writeD(MainActivity.this,tNames);
        } catch (JSONException e) {
            Log.i("*========ft --   ", "JSON Error");
            //e.printStackTrace();
        } catch (IOException e) {
            Log.i("*========ft --   ", "IO Error");
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle sS) {
        super.onRestoreInstanceState(sS);

        ftNoOfPeople = sS.getInt("ftNoOfPeople");
        ftNoOfTeams = sS.getInt("ftNoOfTeams");

        noPeepsTxt.setText(String.valueOf(ftNoOfPeople));
        TeamsEditTxt.setText(String.valueOf(ftNoOfTeams));

    }

    @Override
    protected void onPause() {
        super.onPause();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt("ftNoOfPeople", ftNoOfPeople);
        editor.putInt("ftNoOfTeams", ftNoOfTeams);

        // Commit the edits!
        editor.commit();
        try {
            ftFileIO.writeD(MainActivity.this,tNames);
        } catch (JSONException e) {
            Log.i("*========ft --   ", "JSON Error");
            //e.printStackTrace();
        } catch (IOException e) {
            Log.i("*========ft --   ", "IO Error");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences settings = getPreferences(MODE_PRIVATE);

        ftNoOfPeople = settings.getInt("ftNoOfPeople",6);
        ftNoOfTeams = settings.getInt("ftNoOfTeams",2);

        noPeepsTxt.setText(String.valueOf(ftNoOfPeople));
        TeamsEditTxt.setText(String.valueOf(ftNoOfTeams));

    }

    @Override
    protected void onCreate(Bundle sS) {
        super.onCreate(sS);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);


        noPeepsTxt = (EditText) findViewById(R.id.noOfPeeps);
        TeamsEditTxt = (EditText) findViewById(R.id.noOfTeams);
        noPeepsTxt.addTextChangedListener(new TextWatcher() {


            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                if(s.length() == 0 ){
                    ftNoOfPeople = 0;
                } else {
                    ftNoOfPeople =  Integer.parseInt(String.valueOf(s));
                }
            }
        });
        TeamsEditTxt.addTextChangedListener(new TextWatcher() {


            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                if( s.length() == 0 ){
                    ftNoOfTeams = 0;
                } else {
                    ftNoOfTeams = Integer.parseInt(String.valueOf(s));
                    try {
                        ftFileIO.writeD(MainActivity.this,tNames);
                    } catch (JSONException e) {
                        Log.i("*========ft --   ", "JSON Error");
                        //e.printStackTrace();
                    } catch (IOException e) {
                        Log.i("*========ft --   ", "IO Error");
                    }
                }
                populateList();
            }
        });


        File file = this.getFileStreamPath(ftFileIO.fileName());
        boolean itsThere = file.exists();
        if(! itsThere) {
            populateArr();
        }

        populateList();


    }

    private void populateArr() {
        tNames = new String[ftNoOfTeams];
        for (int i = 0; i < ftNoOfTeams; i++ ) {
            tNames[i] = getString( R.string.team ) + (i+1);
        }

        try {
            ftFileIO.writeD(MainActivity.this,tNames);
        } catch (JSONException e) {
            Log.i("*========ft --   ", "JSON Error");
            //e.printStackTrace();
        } catch (IOException e) {
            Log.i("*========ft --   ", "IO Error");
        }
    }

    private void populateList() {
        try {
            tNames = ftFileIO.readD(this, ftNoOfTeams);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter lA = new ArrayAdapter<String>(this, R.layout.ft_row_layout, R.id.textView1 , tNames);

        ListView lV = (ListView) findViewById(R.id.ftListView);
        lV.setAdapter(lA);

        lV.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String ftTms = parent.getItemAtPosition(position).toString();
                        ftTms += " Clicked";
                        Toast.makeText(MainActivity.this, ftTms, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void OnClickSort(View view){

        Intent goSortScreen = new Intent(this,
                Sorter.class);
        //final int result = 1;


        ftNoOfPeople =  Integer.parseInt(noPeepsTxt.getText().toString());

        ftNoOfTeams =  Integer.parseInt(TeamsEditTxt.getText().toString());




        goSortScreen.putExtra(XtraInfo + "noOfPeeps", ftNoOfPeople);
        goSortScreen.putExtra(XtraInfo + "noOfTeams", ftNoOfTeams);

        startActivity(goSortScreen);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}