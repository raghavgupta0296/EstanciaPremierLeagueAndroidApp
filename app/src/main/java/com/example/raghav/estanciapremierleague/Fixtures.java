package com.example.raghav.estanciapremierleague;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Fixtures extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final TextView c1=(TextView) findViewById(R.id.coldate);
        final TextView c2=(TextView) findViewById(R.id.coltime);
        final TextView c3=(TextView) findViewById(R.id.colfixture);

        c3.setTypeface(null, Typeface.BOLD);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("TempF");

        if(activeNetworkInfo!=null&&activeNetworkInfo.isConnected()) {

            query3.orderByDescending("createdAt");

            query3.findInBackground(new FindCallback<ParseObject>() {
                public void done(final List<ParseObject> qlist3, ParseException e) {
                    if (e == null) {
                        //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                        //String n= String.valueOf(qlist.size());
                        String[] resultsAsString3 = new String[qlist3.size()];

                        //unpin existing records
                        ParseQuery<ParseObject> delExist=ParseQuery.getQuery("TempF");
                        delExist.fromLocalDatastore();
                        delExist.findInBackground(new FindCallback<ParseObject>() {
                            public void done(final List<ParseObject> delList, ParseException e) {
                                if (e != null) {
                                    // There was an error or the network wasn't available.
                                    return;
                                }

                                // Release any objects previously pinned for this query.
                                ParseObject.unpinAllInBackground(delList, new DeleteCallback() {
                                    public void done(ParseException e) {
                                        if (e != null) {
                                            // There was some error.
                                            return;
                                        }

                                        // Pin ParseQuery results
                                        ParseObject.pinAllInBackground(qlist3);

                                    }
                                });
                            }
                        });





                        for (int index = 0; index < qlist3.size(); index++) {

                            resultsAsString3[index] = qlist3.get(index).getString("Date");
                            c1.append("\n\n  " + resultsAsString3[index]);

                            resultsAsString3[index] = qlist3.get(index).getString("Time");
                            c2.append("\n\n" + resultsAsString3[index]);

                            resultsAsString3[index] = qlist3.get(index).getString("Fixture");
                            c3.append("\n\n" + resultsAsString3[index]);

                        }


                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });
        }

        else{
            query3.fromLocalDatastore();
            query3.orderByDescending("createdAt");


            query3.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> qlist3, ParseException e) {
                    if (e == null) {
                        //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                        //String n= String.valueOf(qlist.size());
                        String[] resultsAsString3 = new String[qlist3.size()];

                        // Pin ParseQuery results
                        //ParseObject.pinAllInBackground(qlist);


                        for (int index = 0; index < qlist3.size(); index++) {

                            resultsAsString3[index] = qlist3.get(index).getString("Date");
                            c1.append("\n\n  " + resultsAsString3[index]);

                            resultsAsString3[index] = qlist3.get(index).getString("Time");
                            c2.append("\n\n" + resultsAsString3[index]);


                            resultsAsString3[index] = qlist3.get(index).getString("Fixture");
                            c3.append("\n\n" + resultsAsString3[index]);


                        }


                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });
        }



    }

    public void openLeagueTable(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openLeagueTable2(View view)
    {
        Intent intent=new Intent(this,MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    public void openScorers(View view)
    {
        Intent intent=new Intent(this,Scorers.class);
        startActivity(intent);
    }

}
