package com.example.contentprovidertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> words = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getAllWord();
        listView = findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView, words);
        listView.setAdapter(arrayAdapter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getAllWord() {
        Uri newUri;
        ContentValues mNewValues = new ContentValues();
        mNewValues.put(UserDictionary.Words.LOCALE, "vn");
        mNewValues.put(UserDictionary.Words.WORD, "sample");
        newUri = getContentResolver().insert(UserDictionary.Words.CONTENT_URI,mNewValues);

        String[] projection = {UserDictionary.Words._ID,
                UserDictionary.Words.WORD,
                UserDictionary.Words.LOCALE};
        Cursor cursor = getContentResolver().query(UserDictionary.Words.CONTENT_URI, projection
                , null, null, null);
        if (cursor != null) {
            int idWord = cursor.getColumnIndex(UserDictionary.Words.WORD);
            while (cursor.moveToNext()) {
                String newWord = cursor.getString(idWord);
                words.add(newWord);
            }
            cursor.close();
        }
    }
}
