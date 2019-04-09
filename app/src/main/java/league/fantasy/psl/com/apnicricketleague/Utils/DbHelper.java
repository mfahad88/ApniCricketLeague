package league.fantasy.psl.com.apnicricketleague.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import league.fantasy.psl.com.apnicricketleague.model.response.Config.Datum;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "acl";
    public static final String TBL_CONFIG = "tbl_config";
    public static final String PARAM_CODE = "param_code";
    public static final String PARAM_TYPE = "param_type";
    public static final String DESCRIPTION = "desc";
    public static final String CONFIG_VAL = "config_val";
    public static final String USERID = "userId";
    public static final String CD = "cd";
    public static final String MD = "md";


    public static final String CREATE_CONFIG = "CREATE TABLE " +TBL_CONFIG + "(" + PARAM_CODE + " INTEGER PRIMARY KEY " +
            "," + PARAM_TYPE + " TEXT " +
            "," + DESCRIPTION + " TEXT " +
            "," + CONFIG_VAL + " TEXT " +
            "," + USERID + " TEXT " +
            "," + CD + " TEXT " +
            "," + MD + " TEXT)";
    Context cntxt;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cntxt=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONFIG);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CONFIG);
        onCreate(db);
    }

    public long saveConfig(List<Datum> list) {
        // Gets the data repository in write mode
        long processId = 0;
        SQLiteDatabase db=null;

        try{
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            for(Datum datum:list){
                values.put(PARAM_CODE,Integer.parseInt(datum.getParamCode()));
                values.put(PARAM_TYPE,datum.getParamType());
                values.put(DESCRIPTION,datum.getDesc());
                values.put(CONFIG_VAL,datum.getConfigVal());
                values.put(USERID,datum.getUserId());
                values.put(CD,datum.getCd());
                values.put(MD,datum.getMd());
                processId = db.insert(TBL_CONFIG, null, values);
            }


        }catch (Exception e){
            e.printStackTrace();
        }	finally
        {
            if(db!=null)
                if(db.isOpen())
                    db.close();
        }
        
        return processId;
    }

    public List<String> getConfig(){
        List<String> list = new ArrayList<>();
        Cursor c = null ;
        try {

            String query = "SELECT * FROM " + TBL_CONFIG ;

            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {

                list.add(c.getString(c.getColumnIndex(CONFIG_VAL)));
                    Log.e("Value--->",c.getString(c.getColumnIndex(CONFIG_VAL)));
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(c!=null)
                c.close();

        }
        return list;
    }
}
