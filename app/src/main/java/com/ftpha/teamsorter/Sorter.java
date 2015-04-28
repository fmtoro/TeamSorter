package com.ftpha.teamsorter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Random;


public class Sorter extends ActionBarActivity {
    private int noPeeps;
    private int noTeams;
    private int pPT;
    private int peepInProg;
    private int currT;

    public TextView ftTitUp;
    public TextView ftTitle;
    public TextView ftAnsw;

    public int[] aPinT;// = new String[4];

    @Override
    protected void onSaveInstanceState(Bundle oS) {
        super.onSaveInstanceState(oS);

        oS.putInt("noPeeps", noPeeps);
        oS.putInt("noTeams",noTeams);
        oS.putInt("pPT",pPT);
        oS.putInt("peepInProg",peepInProg);
        oS.putInt("currT",currT);
        oS.putIntArray("aPinT", aPinT);

    }

    @Override
    protected void onRestoreInstanceState(Bundle sS) {
        super.onRestoreInstanceState(sS);

        noPeeps= sS.getInt("noPeeps");
        noTeams= sS.getInt("noTeams");
        pPT= sS.getInt("pPT");
        peepInProg= sS.getInt("peepInProg");
        currT= sS.getInt("currT");
        aPinT = sS.getIntArray("aPinT");

        ftTitUp.setText("Person " + String.valueOf(peepInProg + 1) + " touch the hat");
        ftTitle.setText("Person " + String.valueOf(peepInProg) + " is in team:");
        ftAnsw.setText(String.valueOf(currT));
        if(peepInProg == noPeeps) {
            ftTitUp.setText("All done");
        }
        ftAnsw.setAlpha(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_layout);

        ImageButton ftIB = (ImageButton) findViewById(R.id.imageButton);
        ftIB.setBackgroundResource(R.drawable.hat_ft);

        ftTitUp = (TextView) findViewById(R.id.titUp);
        ftTitle = (TextView) findViewById(R.id.txtTitle);
        ftAnsw = (TextView) findViewById(R.id.answerTxt);

        ftAnsw.setAlpha(0);
        ftTitle.setText("");

        //

        Bundle bundle = getIntent().getExtras();

        if(bundle.getInt(MainActivity.XtraInfo + "noOfPeeps") > 0)
        {
            noPeeps = bundle.getInt(MainActivity.XtraInfo + "noOfPeeps");
        } else {
            noPeeps = -1;
        }

        if(bundle.getInt(MainActivity.XtraInfo + "noOfTeams") > 0)
        {
            noTeams = bundle.getInt(MainActivity.XtraInfo + "noOfTeams");
        } else {
            noTeams = -1;
        }
        pPT = Math.round(noPeeps / noTeams) ;
        aPinT = new int[noTeams+1];

        for(int i = 1; i == noTeams; i++){
            aPinT[i] = 0;
        }



        peepInProg = 0;
        //goSortScreen.putExtra(XtraInfo + "noOfPeeps", ftNoOfPeeps);
        //goSortScreen.putExtra(XtraInfo + "noOfTeams", noOfTeams);
    }

    public int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public int nextTeam(){

        boolean foundOne = false;
        int temp;
        int j =1;
        do{
            temp = randInt(1, noTeams);
            if(aPinT[temp]<pPT){
                aPinT[temp]++;
                foundOne = true;
            }else if(aPinT[temp]== pPT & j>=10){
                aPinT[temp]++;
                foundOne = true;
            }
            j++;
        }while(!foundOne);

        return temp;
    }

    public void showTeam(View v){

        if(peepInProg < noPeeps) {
            int ftSelTeam = nextTeam();
            peepInProg++;
            ftAnsw.setAlpha(1);
            if(peepInProg == noPeeps){
                ftTitUp.setText("All done");
            } else {
                ftTitUp.setText("Person " + String.valueOf(peepInProg + 1) + " touch the hat");
            }
            ftTitle.setText("Person " + String.valueOf(peepInProg) + " is in team:");
            currT = ftSelTeam;
            ftAnsw.setText(String.valueOf(currT));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sorter, menu);
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
