package rodriguez.diego.com.week7_sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create database
        SQLiteDatabase mDatabase;
        mDatabase = openOrCreateDatabase("my_sqlite_database.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        mDatabase.setLocale(Locale.getDefault());
        mDatabase.setVersion(1);

        // create a table in database
        String sqlCreateTableCommand = "CREATE TABLE if not exists tbl_authors (id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT, lastname TEXT);";
        mDatabase.execSQL(sqlCreateTableCommand);

        // insert records in table tbl_authors
        // insert first record
        ContentValues values = new ContentValues();
        values.put("firstname", "J.K.");
        values.put("lastname", "Rowling");
        long newAuthorID = mDatabase.insert("tbl_authors", null, values);

        // insert second record
        values.clear();
        values.put("firstname", "Mark");
        values.put("lastname", "Twain");
        mDatabase.insert("tbl_authors", null, values);

        values.clear();
        values.put("firstname", "Michael");
        values.put("lastname", "Jackson");
        mDatabase.insert("tbl_authors", null, values);

        // updating records
        // update firstname to "James" for all records where lastname="Twain"
        values.clear();
        values.put("firstname", "James");
        mDatabase.update("tbl_authors", values, "lastname=?", new String[]{"Twain"});

        // delete all records where lastname=Twain
        mDatabase.delete("tbl_authors", "lastname=?", new String[]{"Twain"});

        // query the database
        // select all records from table tbl_authors
        Cursor c = mDatabase.query("tbl_authors",null,null,null,null,null,null);
        // iterate through cursor and display one record at a time
        System.out.println("... executing equivalent of SELECT * FROM tbl_authors");
        while (c.moveToNext()) {
            String id = c.getString(0); // unique id of the record
            String firstName = c.getString(1); // first name column
            String lastName = c.getString(2);
            System.out.println(id + " " + firstName + " " + lastName);
        }
        // when we are done with the cursor we should close it
        c.close();

        // now we do a more selective query
        System.out.println("... executing equivalent of SELECT * FROM tbl_authors WHERE lastname='Jackson'");
        c = mDatabase.query("tbl_authors", null,
                "lastname=?", new String[]{"Jackson"}, null, null, null);
        while (c.moveToNext()) {
            String id = c.getString(0); // unique id of the record
            String firstName = c.getString(1); // first name column
            String lastName = c.getString(2);
            System.out.println(id + " " + firstName + " " + lastName);
        }
        c.close();

        // delete table tbl_authors
        //mDatabase.execSQL("DROP TABLE tbl_authors;");

        // when we are done working with the database we should close it
        mDatabase.close();

        // delete the database"
        //deleteDatabase("my_sqlite_database.db");
    }
}
