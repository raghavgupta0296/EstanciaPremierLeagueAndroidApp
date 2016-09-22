package com.example.raghav.estanciapremierleague;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class UpdateDB extends AppCompatActivity {

    private LinearLayout mLayout;
    private EditText mEditText;
    private Button mButton;

    public Spinner scorerSpinner;
    public EditText scorerg;

    public ArrayList<String> Scorer=new ArrayList<String>();                      // Scorers
    public ArrayList<String> ScorerTeam=new ArrayList<String>();                  // Scorer's Team
    public ArrayList<String> addGoals=new ArrayList<String>();                    // Scorer's Goals

    public String pushMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_db);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Parse.initialize(this, "0xVug6ZtoXFuUHPoLZ06rZ9vpEX5TJO2of7q0CwQ", "6usWQ0xZwhmtfac5KkCvp7tMldfAbNwhkuDOFUuC");
        //ParseInstallation.getCurrentInstallation().saveInBackground();

        mLayout = (LinearLayout) findViewById(R.id.linearlayout4);
        mEditText = (EditText) findViewById(R.id.DBscorer);
        mButton = (Button) findViewById(R.id.DBaddname);
        scorerSpinner=(Spinner) findViewById(R.id.DBteamscorer);
        mButton.setOnClickListener(onClick());
        scorerg=(EditText) findViewById(R.id.DBscorergoals);
        TextView textView = new TextView(this);
        textView.setText("\n ");


        final ArrayList<String> mylist = new ArrayList<String>();
        mylist.add("Select a team ... ");
        final Spinner spinner = (Spinner) findViewById(R.id.DBteam1);
        spinner.setOnItemSelectedListener(new SpinnerActivity());

        final Spinner spinner2 = (Spinner) findViewById(R.id.DBteam2);
        spinner2.setOnItemSelectedListener(new SpinnerActivity());



        //Parse Stuff #Spinners
        ParseQuery<ParseObject> query4 = ParseQuery.getQuery("TempLT");
        query4.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> qlist4, ParseException e) {
                if (e == null) {
                    //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    //String n= String.valueOf(qlist4.size());

                    for (int index = 0; index < qlist4.size(); index++)
                        mylist.add(qlist4.get(index).getString("Team"));


                    ArrayAdapter adapter = new ArrayAdapter(UpdateDB.this, android.R.layout.simple_spinner_item, mylist);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner2.setAdapter(adapter);
                    scorerSpinner.setAdapter(adapter);





                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
        // Parse Stuff Ends

    }



    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {


        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            String selected = parent.getItemAtPosition(pos).toString();

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }



    private View.OnClickListener onClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLayout.addView(createNewTextView(mEditText.getText().toString(),scorerSpinner.getSelectedItem().toString(),scorerg.getText().toString()));
            }
        };
    }


    private TextView createNewTextView(String text,String text2,String sg) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        String sm;
        if(Integer.parseInt(sg)==1)
            sm="goal";
        else sm="goals";
        textView.setText("\n " + text + "  ( " + text2 + " )  - " + sg + " "+sm);
        Scorer.add(text);
        ScorerTeam.add(text2);
        addGoals.add(sg);
        Log.i(" add goals ",String.valueOf(addGoals));
        return textView;
    }


    public void updateDB()
    {
        // Fixtures Add
        ParseObject TempF = new ParseObject("TempF");

        EditText DateOfFixture=(EditText) findViewById(R.id.DBeditdate);
        EditText TimeOfFixture=(EditText) findViewById(R.id.DBedittime);
        EditText Score1=(EditText) findViewById(R.id.DBscore1);
        EditText Score2=(EditText) findViewById(R.id.DBscore2);

        Spinner spinner = (Spinner) findViewById(R.id.DBteam1);
        final String team1 = spinner.getSelectedItem().toString();
        spinner.setOnItemSelectedListener(new SpinnerActivity());
        Spinner spinner2 = (Spinner) findViewById(R.id.DBteam2);
        final String team2 = spinner2.getSelectedItem().toString();
        spinner2.setOnItemSelectedListener(new SpinnerActivity());

        String dof= DateOfFixture.getText().toString();
        TempF.put("Date",dof);                                      // Date Of Fixture
        String tof=TimeOfFixture.getText().toString();
        TempF.put("Time", tof);                                     // Time Of Fixture
        final String s1=Score1.getText().toString();                      // Score 1
        final String s2=Score2.getText().toString();                      // Score 2
        TempF.put("Fixture", team1 + " " + s1 + " - " + s2 + " " + team2);
        pushMessage=team1 + " " + s1 + " - " + s2 + " " + team2;
        if(pushMessage.length()>140)
        {
            pushMessage=pushMessage.substring(0,137)+"...";
        }
        TempF.saveInBackground();
        // Fixtures Add End

        // League Table Update
        final int score1=Integer.parseInt(s1);
        final int score2=Integer.parseInt(s2);
        ParseQuery<ParseObject> query6=new ParseQuery<ParseObject>("TempLT");
        ParseQuery<ParseObject> query7=new ParseQuery<ParseObject>("TempLT");

        // if team1 wins
        if (score1 > score2) {

            query6.whereEqualTo("Team",team1);
            query6.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject t1, ParseException e) {
                    if (t1 == null) {
                        Log.d("score", "The getFirst request failed.");
                    } else {
                        t1.increment("Played");
                        t1.increment("Won");
                        t1.increment("GF", score1);
                        t1.increment("GA", score2);
                        t1.increment("GD", score1 - score2);
                        t1.increment("Points", 3);
                        t1.saveInBackground();
                    }
                }
            });

            query7.whereEqualTo("Team",team2);
            query7.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject t2, ParseException e) {
                    if (t2 == null) {
                        Log.d("score", "The getFirst request failed.");
                    } else {
                        t2.increment("Played");
                        t2.increment("Lost");
                        t2.increment("GF", score2);
                        t2.increment("GA", score1);
                        t2.increment("GD", score2 - score1);
                        t2.saveInBackground();
                    }
                }
            });

        }

        // if team2 wins

        else if(score2>score1)
        {

            query6.whereEqualTo("Team",team2);
            query6.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject t2, ParseException e) {
                    if (t2 == null) {
                        Log.d("score", "The getFirst request failed.");
                    } else {
                        t2.increment("Played");
                        t2.increment("Won");
                        t2.increment("GF", score2);
                        t2.increment("GA", score1);
                        t2.increment("GD", score2 - score1);
                        t2.increment("Points", 3);
                        t2.saveInBackground();
                    }
                }
            });

            query7.whereEqualTo("Team",team1);
            query7.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject t1, ParseException e) {
                    if (t1 == null) {
                        Log.d("score", "The getFirst request failed.");
                    } else {
                        t1.increment("Played");
                        t1.increment("Lost");
                        t1.increment("GF", score1);
                        t1.increment("GA", score2);
                        t1.increment("GD", score1 - score2);
                        t1.saveInBackground();
                    }
                }
            });


        }

        else if(score1==score2)
        {

            query6.whereEqualTo("Team",team1);
            query6.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject t1, ParseException e) {
                    if (t1 == null) {
                        Log.d("score", "The getFirst request failed.");
                    } else {
                        t1.increment("Played");
                        t1.increment("Draw");
                        t1.increment("GF", score1);
                        t1.increment("GA", score2);
                        t1.increment("GD", score1 - score2);
                        t1.increment("Points");
                        t1.saveInBackground();
                    }
                }
            });

            query7.whereEqualTo("Team",team2);
            query7.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject t2, ParseException e) {
                    if (t2 == null) {
                        Log.d("score", "The getFirst request failed.");
                    } else {
                        t2.increment("Played");
                        t2.increment("Draw");
                        t2.increment("GF", score2);
                        t2.increment("GA", score1);
                        t2.increment("GD", score2 - score1);
                        t2.increment("Points");
                        t2.saveInBackground();
                    }
                }
            });

        }

        // League Table Update Ends




        // Scorer Update

        final ArrayList<String> ListedScorers= new ArrayList<String>();
        final ArrayList<String> ListedScorerTeam=new ArrayList<String>();


        final ParseObject[] TempS = new ParseObject[1];

        ParseQuery<ParseObject> query5 = ParseQuery.getQuery("TempS");
        final int[] goals = new int[1];
        final int[] g = new int[1];
        final String[] name = new String[1];
        final String[] team = new String[1];

        query5.whereContainedIn("Name", Scorer);

        query5.whereContainedIn("Team", ScorerTeam);

        query5.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> qlist5, ParseException e) {
                if (e == null) {

                    for (int index = 0; index < qlist5.size(); index++) {

                        goals[0] = qlist5.get(index).getInt("Goals");
                        name[0] = qlist5.get(index).getString("Name");
                        Log.i(" qlist Name ", name[0]);
                        team[0] = qlist5.get(index).getString("Team");
                        Log.i(" qlist Team ", team[0]);

                        Log.i("UpdateDB : ", String.valueOf(qlist5));

                        for (int x = 0; x < Scorer.size(); x++) {
                            Log.i(" Scorer ", Scorer.get(x));
                            Log.i(" ScorerTeam ", ScorerTeam.get(x));

                            if (Scorer.get(x).contains(name[0]) && ScorerTeam.get(x).contains(team[0])) {
                                g[0] = Integer.parseInt(addGoals.get(x));
                                Log.i(" goals scored ", String.valueOf(g[0]));
                            }

                        }

                        TempS[0] = new ParseObject("TempS");
                        TempS[0].put("Name", name[0]);
                        ListedScorers.add(name[0]);
                        Log.i("Listed Name  ", name[0]);
                        TempS[0].put("Team", team[0]);
                        ListedScorerTeam.add(team[0]);
                        Log.i("Listed Team ", team[0]);
                        TempS[0].put("Goals", goals[0] + g[0]);
                        TempS[0].saveInBackground();

                        qlist5.get(index).deleteInBackground();


                    }

                    ArrayList<String> addScorer = new ArrayList<String>();
                    ArrayList<String> addScorerTeam = new ArrayList<String>();
                    ArrayList<String> addScorerg = new ArrayList<String>();

                    for (int k = 0; k < Scorer.size(); k++) {
                        if (!(ListedScorers.contains(Scorer.get(k)) && ListedScorerTeam.contains(ScorerTeam.get(k)))) {

                            addScorer.add(Scorer.get(k));
                            Log.i("**Added**", Scorer.get(k));
                            addScorerTeam.add(ScorerTeam.get(k));
                            addScorerg.add(addGoals.get(k));
                            pushMessage=pushMessage+"\n"+Scorer.get(k)+" - "+addGoals.get(k);
                            if(pushMessage.length()>140) {
                                pushMessage = pushMessage.substring(0, 137) + "...";
                            }
                        }

                    }

                    for (int index2 = 0; index2 < addScorer.size(); index2++) {

                        TempS[0] = new ParseObject("TempS");
                        TempS[0].put("Name", addScorer.get(index2));
                        TempS[0].put("Team", addScorerTeam.get(index2));
                        TempS[0].put("Goals", Integer.parseInt(addScorerg.get(index2)));
                        TempS[0].saveInBackground();

                    }


                } else {

                    Log.d("score", "Error: " + e.getMessage());

                }
            }
        });

        // Scorer Update Ends


    }


    public void afclick(View view) {

        updateDB();
        Toast.makeText(getApplicationContext(), " Result Added ", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext()," Reopen app to view changes ",Toast.LENGTH_SHORT).show();
        // Push Notification
        ParsePush message=new ParsePush();
        message.setMessage(pushMessage);
        message.setExpirationTimeInterval(60 * 60 * 24);
        message.sendInBackground();
        // Push Notification ends

        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void aarclick(View view){
        updateDB();
        Toast.makeText(getApplicationContext(), " Result Added ", Toast.LENGTH_SHORT).show();

        // Push Notification
        ParsePush message=new ParsePush();
        message.setMessage(pushMessage);
        message.setExpirationTimeInterval(60 * 60 * 24);
        message.sendInBackground();
        // Push Notification ends

        Intent intent=new Intent(this,UpdateDB.class);
        startActivity(intent);

    }

    public void clickclear(View view){
        Toast.makeText(getApplicationContext(), " Values Cleared ", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,UpdateDB.class);
        startActivity(intent);

    }




}
