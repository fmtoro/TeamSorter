package com.ftpha.teamsorter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


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

        tNames = new String[]{
                "NIU",
                "Team 1",
                "Team 2",
                "Team 3",
                "Team 4",
                "Team 5",
                "Team 6",
                "Team 7",
                "Team 8",
                "Team 9",
                "Team 10"
        };

        ListAdapter theAdapter= new ArrayAdapter<String>(this,
                R.layout.row_layout,
                tNames);
        ListView theListView = (ListView) findViewById(R.id.ftListView);

        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String teamSelected = "You clicked " +
                                String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(MainActivity.this, teamSelected, Toast.LENGTH_SHORT).show();
                //parent.getItemAtPosition(0).
            }
        });

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
                }
            }
        });


     /*   Spinner spinner = (Spinner) findViewById(R.id.mode_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.modes_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
*/
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
