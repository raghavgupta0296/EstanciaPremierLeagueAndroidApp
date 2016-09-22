package com.example.raghav.estanciapremierleague;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = getIntent();

        final TextView c1 = (TextView) findViewById(R.id.colteams);
        final TextView c2 = (TextView) findViewById(R.id.colplayed);
        final TextView c3 = (TextView) findViewById(R.id.colwon);
        final TextView c4 = (TextView) findViewById(R.id.coldrawn);
        final TextView c5 = (TextView) findViewById(R.id.collost);
        final TextView c6 = (TextView) findViewById(R.id.colgf);
        final TextView c7 = (TextView) findViewById(R.id.colga);
        final TextView c8 = (TextView) findViewById(R.id.colgd);
        final TextView c9 = (TextView) findViewById(R.id.colpoints);
        //final TextView t2=(TextView) findViewById(R.id.textView2);
        c1.setTypeface(null, Typeface.BOLD);

        // Enable Local DataStore.
        //Parse.enableLocalDatastore(this);


        //Parse.initialize(this, "0xVug6ZtoXFuUHPoLZ06rZ9vpEX5TJO2of7q0CwQ", "6usWQ0xZwhmtfac5KkCvp7tMldfAbNwhkuDOFUuC");
        //final ParseObject LeagueTable = new ParseObject("TempLT");
        //LeagueTable.put("Team", "Metaloids");
        //LeagueTable.saveInBackground();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TempLT");

        //private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //     return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        // }

        if(activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            //query.fromLocalDatastore();

            query.whereEqualTo("Group","B");
            query.orderByDescending("Points");
            query.addDescendingOrder("GD");
            query.addDescendingOrder("GF");
            query.addAscendingOrder("Team");


            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(final List<ParseObject> qlist, ParseException e) {
                    if (e == null) {
                        //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                        //String n= String.valueOf(qlist.size());
                        String[] resultsAsString = new String[qlist.size()];
                        ParseObject.pinAllInBackground(qlist);
                        /*
                        //unpin existing records
                        ParseQuery<ParseObject> delExist = ParseQuery.getQuery("TempLT");
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
                                        ParseObject.pinAllInBackground(qlist);

                                    }
                                });
                            }
                        });
                        */


                        for (int index = 0; index < qlist.size(); index++) {

                            resultsAsString[index] = qlist.get(index).getString("Team");
                            c1.append("\n\n " + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("Played"));
                            c2.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("Won"));
                            c3.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("Drawn"));
                            c4.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("Lost"));
                            c5.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("GF"));
                            c6.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("GA"));
                            c7.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("GD"));
                            c8.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("Points"));
                            c9.append("\n\n" + resultsAsString[index]);

                        }


                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });


        } else {
            query.fromLocalDatastore();
            query.whereEqualTo("Group","B");
            query.orderByDescending("Points");
            query.addDescendingOrder("GD");
            query.addDescendingOrder("GF");
            query.addAscendingOrder("Team");


            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> qlist, ParseException e) {
                    if (e == null) {
                        //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                        //String n= String.valueOf(qlist.size());
                        String[] resultsAsString = new String[qlist.size()];

                        // Pin ParseQuery results
                        //ParseObject.pinAllInBackground(qlist);


                        for (int index = 0; index < qlist.size(); index++) {

                            resultsAsString[index] = qlist.get(index).getString("Team");
                            c1.append("\n\n " + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("Played"));
                            c2.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("Won"));
                            c3.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("Drawn"));
                            c4.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("Lost"));
                            c5.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("GF"));
                            c6.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("GA"));
                            c7.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("GD"));
                            c8.append("\n\n" + resultsAsString[index]);

                            resultsAsString[index] = String.valueOf(qlist.get(index).getInt("Points"));
                            c9.append("\n\n" + resultsAsString[index]);

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
        intent.putExtra("group",1);
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

    public void openScorers(View view) {
        Intent intent = new Intent(this, Scorers.class);
        startActivity(intent);

    }

    public void openFixtures(View view) {
        Intent intent = new Intent(this, Fixtures.class);
        startActivity(intent);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.admin_settings:
                // admin was selected
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */
}











//For Updating
/*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("LeagueTable");

        query.getInBackground("cwOHufWacv", new GetCallback<ParseObject>() {
            public void done(ParseObject gameScore, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    gameScore.put("Won", 6);
                    gameScore.saveInBackground();
                }
            }
        });
*/

//For Displaying
/*
   ParseQuery<ParseObject> query = ParseQuery.getQuery("LeagueTable");
        query.getInBackground("cwOHufWacv", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String n = object.getString("Team");
                    //t.setText(n);
                    String p=object.getString("Won");
                    t2.setText(n+p);
                    // object will be your game score
                } else {
                    // something went wrong
                }
            }
        });

*/
