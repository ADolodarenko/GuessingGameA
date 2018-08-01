package org.dav.android.guessinggame;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int TRYING_LIMIT = 7;

    private EditText txtGuess;
    private Button btnGuess;
    private TextView lblOutput;
    private int theNumber;
    private int tries;
    private int range = 100;
    private TextView lblRange;

    public void checkGuess()
    {
        String guessText = txtGuess.getText().toString();
        String message = "";

        try
        {
            int guess = Integer.parseInt(guessText);

            tries--;

            if (guess == theNumber)
            {
                message = guess + " is correct. You win after " + (TRYING_LIMIT - tries) + " tries! Let's play again!";

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                int gamesWon = preferences.getInt("gamesWon", 0) + 1;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("gamesWon", gamesWon);
                editor.apply();

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                newGame();
            }
            else
            {
                if (guess < theNumber)
                    message = guess + " is too low.";
                else
                    message = guess + " is too high.";

                if (tries > 0)
                    message = message + " Try again.";
                else
                {
                    message = message + " You lost. The number was " + theNumber + ". Let's play again!";

                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    newGame();
                }
            }
        }
        catch (Exception e)
        {
            message = "Enter a whole number between 1 and " + range + ".";
        }
        finally
        {
            lblOutput.setText(message);
            txtGuess.requestFocus();
            txtGuess.selectAll();
        }
    }

    public void newGame()
    {
        theNumber = (int)(Math.random() * range + 1);
        lblRange.setText("Enter a number between 1 and " + range + ":");
        txtGuess.setText("" + range/2);
        txtGuess.requestFocus();
        txtGuess.selectAll();

        tries = TRYING_LIMIT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGuess = (EditText)findViewById(R.id.txtGuess);
        btnGuess = (Button)findViewById(R.id.btnGuess);
        lblOutput = (TextView)findViewById(R.id.lblOutput);
        lblRange = (TextView)findViewById(R.id.textView4);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        range = preferences.getInt("range", 100);

        newGame();

        btnGuess.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkGuess();
            }
        });

        /*txtGuess.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                checkGuess();

                return true;
            }
        });*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_settings:
                final CharSequence[] items = {"1 to 10", "1 to 100", "1 to 1000"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select the Range:");
                builder.setItems(items, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        switch (i)
                        {
                            case 0:
                                range = 10;
                                break;
                            case 1:
                                range = 100;
                                break;
                            case 2:
                                range = 100;
                                break;
                        }

                        storeRange(range);
                        newGame();

                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            case R.id.action_newgame:
                newGame();

                return true;
            case R.id.action_gamestats:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                int gamesWon = preferences.getInt("gamesWon", 0);
                AlertDialog statDialog = new AlertDialog.Builder(MainActivity.this).create();
                statDialog.setTitle("Guessing Game Stats");
                statDialog.setMessage("You have won " + gamesWon + " games. Way to go!");
                statDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dialogInterface.dismiss();
                            }
                        });
                statDialog.show();

                return true;
            case R.id.action_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("About Guessing Game");
                aboutDialog.setMessage("(c) 2018 Alex.");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dialogInterface.dismiss();
                            }
                        });
                aboutDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void storeRange(int newRange)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("range", newRange);
        editor.apply();
    }
}
