package nd.com.getoffline;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by DravitLochan on 17-12-2016.
 */

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GetOffline";
    private static final String TABLE_PAGE = "PageInfo";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SRC_CODE = "src_code";


    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //this function is called if there are some upgrades or at the first time. i.e. when the db is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PAGE_TABLE = "CREATE TABLE " + TABLE_PAGE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SRC_CODE + " TEXT" + ")";
        db.execSQL(CREATE_PAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAGE);
        // Create tables again
        onCreate(db);
    }

    void addPage(PageInfo pageinfo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,pageinfo.getName());
        cv.put(KEY_SRC_CODE,pageinfo.getSrcCode());
        db.insert(TABLE_PAGE,null,cv);
        db.close();
    }
        //add in the first version
    /*
    void deletePage()
    {

    }
    */

    int countPages()
    {
        String countQuery = "SELECT  * FROM " + TABLE_PAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int x=cursor.getCount();
        cursor.close();
        db.close();
        return x;
    }

    //to fetch the name of all the pages and put them in a recycler view
    public List<PageInfo> getAllPages() {
        List<PageInfo> contactList = new ArrayList<PageInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PageInfo pageinfo = new PageInfo(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
                // Adding contact to list
                contactList.add(pageinfo);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return contactList;
    }


}
