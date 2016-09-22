package com.example.raghav.estanciapremierleague;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class Scorers extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_scorers);
            Intent intent = getIntent();
            //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);

            final TextView c1 = (TextView) findViewById(R.id.colname);
            final TextView c2 = (TextView) findViewById(R.id.colteam);
            final TextView c3 = (TextView) findViewById(R.id.colgoals);

            c1.setTypeface(null, Typeface.BOLD);


            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //Parse.enableLocalDatastore(this);

            //Parse.initialize(this, "0xVug6ZtoXFuUHPoLZ06rZ9vpEX5TJO2of7q0CwQ", "6usWQ0xZwhmtfac5KkCvp7tMldfAbNwhkuDOFUuC");
            //final ParseObject Scorers = new ParseObject("Scorers");

            ParseQuery<ParseObject> query2 = ParseQuery.getQuery("TempS");

            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                //query.fromLocalDatastore();

                query2.orderByDescending("Goals");
                query2.addAscendingOrder("Team");

                query2.findInBackground(new FindCallback<ParseObject>() {
                    public void done(final List<ParseObject> qlist2, ParseException e) {
                        if (e == null) {
                            //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                            //String n= String.valueOf(qlist.size());
                            String[] resultsAsString2 = new String[qlist2.size()];


                            //unpin existing records
                            ParseQuery<ParseObject> delExist = ParseQuery.getQuery("TempS");
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
                                            ParseObject.pinAllInBackground(qlist2);

                                        }
                                    });
                                }
                            });


                            for (int index = 0; index < qlist2.size(); index++) {

                                resultsAsString2[index] = qlist2.get(index).getString("Name");
                                c1.append("\n\n  " + resultsAsString2[index]);

                                resultsAsString2[index] = qlist2.get(index).getString("Team");
                                c2.append("\n\n" + resultsAsString2[index]);

                                resultsAsString2[index] = String.valueOf(qlist2.get(index).getInt("Goals"));
                                c3.append("\n\n" + resultsAsString2[index]);

                            }


                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                        }
                    }
                });
            } else {
                query2.fromLocalDatastore();

                query2.orderByDescending("Goals");
                query2.addAscendingOrder("Team");

                query2.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> qlist2, ParseException e) {
                        if (e == null) {
                            //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                            //String n= String.valueOf(qlist.size());
                            String[] resultsAsString2 = new String[qlist2.size()];

                            // Pin ParseQuery results
                            //ParseObject.pinAllInBackground(qlist);


                            for (int index = 0; index < qlist2.size(); index++) {

                                resultsAsString2[index] = qlist2.get(index).getString("Name");
                                c1.append("\n\n  " + resultsAsString2[index]);

                                resultsAsString2[index] = qlist2.get(index).getString("Team");
                                c2.append("\n\n" + resultsAsString2[index]);

                                resultsAsString2[index] = String.valueOf(qlist2.get(index).getInt("Goals"));
                                c3.append("\n\n" + resultsAsString2[index]);

                            }


                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                        }
                    }
                });

            }

        }


        public void openLeagueTable(View view) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

    public void openLeagueTable2(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

        public void openFixtures(View view) {
            Intent intent = new Intent(this, Fixtures.class);
            startActivity(intent);
        }


    }





