package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    static String usernameKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usernameKey = "username";

        SharedPreferences sharedPref = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        if(!sharedPref.getString(usernameKey, "").equals("")) {
            String user = sharedPref.getString(usernameKey, "");

            Intent intent = new Intent(this, notes.class);
            intent.putExtra("username", user);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
        }
    }

    public void login(View view) {
        EditText usernameField = (EditText) findViewById(R.id.username);
        EditText passwordField = (EditText) findViewById(R.id.password);

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        SharedPreferences sharedPref = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        sharedPref.edit().putString("username", username).apply();

        goToNotesActivity(username);
    }

    public void goToNotesActivity(String username) {
        Intent intent = new Intent(this, notes.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
