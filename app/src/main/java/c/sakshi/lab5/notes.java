package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class notes extends AppCompatActivity {

    TextView header;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        SharedPreferences sharedPref = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        header = (TextView) findViewById(R.id.header);
        Intent intent = getIntent();
        //String username = intent.getStringExtra("username");
        String username  = sharedPref.getString(MainActivity.usernameKey, "");
        header.setText("Welcome " + username + "!");

        Context context = getApplicationContext();
        SQLiteDatabase database = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        DBHelper dbHelper = new DBHelper(database);

        notes = dbHelper.readNotes(username);

        ArrayList<String> displayNotes = new ArrayList<>();
        for(Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), addNote.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(this, MainActivity.class);
                SharedPreferences sharedPref = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
                sharedPref.edit().remove(MainActivity.usernameKey).apply();
                startActivity(intent);
                return true;
            case R.id.addNote:
                Intent addNoteIntent = new Intent(this, addNote.class);
                startActivity(addNoteIntent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
