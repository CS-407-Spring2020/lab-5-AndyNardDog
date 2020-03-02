package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addNote extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText notesText = (EditText) findViewById(R.id.noteText);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if(noteid != -1) {
            Note note = notes.notes.get(noteid);
            String noteContent = note.getContent();
            notesText.setText(noteContent);
        }
    }

    public void save(View view) {

        EditText notesText = (EditText) findViewById(R.id.noteText);
        String content = notesText.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase database = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        DBHelper dbHelper = new DBHelper(database);

        SharedPreferences sharedPref = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPref.getString(MainActivity.usernameKey, "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid == -1) {
            title = "NOTE_" + (notes.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNotes(title, date, content, username);
        }

        Intent intent = new Intent(this, notes.class);
        startActivity(intent);
    }
}
