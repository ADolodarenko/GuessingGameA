package org.dav.android.guessinggame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
            message = "Enter a whole number between 1 and 100.";
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
        theNumber = (int)(Math.random() * 100 + 1);
        tries = TRYING_LIMIT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGuess = (EditText)findViewById(R.id.txtGuess);
        btnGuess = (Button)findViewById(R.id.btnGuess);
        lblOutput = (TextView)findViewById(R.id.lblOutput);

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
