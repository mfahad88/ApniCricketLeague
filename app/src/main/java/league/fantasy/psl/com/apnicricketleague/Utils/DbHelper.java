package league.fantasy.psl.com.apnicricketleague.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import league.fantasy.psl.com.apnicricketleague.model.response.Player.Datum;


public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper DB_HELPER=null;

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

    public static final String TBL_PLAYERS = "tbl_players";
    public static final String	team_id="team_id";
    public static final String	team_name="team_name";
    public static final String	player_id="player_id";
    public static final String	name="name";
    public static final String	game="game";
    public static final String	pic_url="pic_url";
    public static final String	plays_for="plays_for";
    public static final String	skill="skill";
    public static final String	style="style";
    public static final String	runs="runs";
    public static final String	avg="avg";
    public static final String	hundreds="hundreds";
    public static final String	fifties="fifties";
    public static final String	sr="sr";
    public static final String	wkt="wkt";
    public static final String	price="price";
    public static final String	cd="cd";
    public static final String	md="md";


    public static final String CREATE_CONFIG = "CREATE TABLE " +TBL_CONFIG + "(" + PARAM_CODE + " INTEGER PRIMARY KEY " +
            "," + PARAM_TYPE + " TEXT " +
            "," + DESCRIPTION + " TEXT " +
            "," + CONFIG_VAL + " TEXT " +
            "," + USERID + " TEXT " +
            "," + CD + " TEXT " +
            "," + MD + " TEXT)";

    public static final String CREATE_PLAYERS = "CREATE TABLE "+TBL_PLAYERS + "("+ team_id + " INTEGER " +
            ", "+team_name+" TEXT" +
            ", "+player_id+" TEXT" +
            ", "+name+" TEXT" +
            ", "+game+" TEXT" +
            ", "+pic_url+" TEXT" +
            ", "+plays_for+" TEXT" +
            ", "+skill+" TEXT" +
            ", "+style+" TEXT" +
            ", "+runs+" TEXT" +
            ", "+avg+" TEXT" +
            ", "+hundreds+" TEXT" +
            ", "+fifties+" TEXT" +
            ", "+sr+" TEXT" +
            ", "+wkt+" TEXT" +
            ", "+price+" TEXT" +
            ", "+cd+" TEXT" +
            ", "+md+" TEXT)";

    Context cntxt;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cntxt=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("SQLiteDatabase",CREATE_CONFIG);
        Log.e("SQLiteDatabase",CREATE_PLAYERS);
        db.execSQL(CREATE_CONFIG);
        db.execSQL(CREATE_PLAYERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CONFIG);
        db.execSQL("DROP TABLE IF EXISTS "+ TBL_PLAYERS);
        onCreate(db);
    }

    public long saveConfig(List<league.fantasy.psl.com.apnicricketleague.model.response.Config.Datum> list) {
        // Gets the data repository in write mode
        long processId = 0;
        SQLiteDatabase db=null;

        try{
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            for(league.fantasy.psl.com.apnicricketleague.model.response.Config.Datum datum:list){
                values.put(PARAM_CODE,Integer.parseInt(datum.getParamCode()));
                values.put(PARAM_TYPE,datum.getParamType());
                values.put(DESCRIPTION,datum.getDesc());
                values.put(CONFIG_VAL,datum.getConfigVal());
                values.put(USERID,datum.getUserId());
                values.put(CD,datum.getCd());
                values.put(MD,datum.getMd());
                processId = db.insert(TBL_CONFIG, null, values);
                Log.e("SQLiteDatabase",datum.toString());
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

    public long savePlayers(List<league.fantasy.psl.com.apnicricketleague.model.response.Player.Datum> list) {
        // Gets the data repository in write mode
        long processId = 0;
        SQLiteDatabase db=null;

        try{
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            for(league.fantasy.psl.com.apnicricketleague.model.response.Player.Datum datum:list){
                values.put(team_id,datum.getTeamId().intValue());
                values.put(team_name,datum.getTeamName());
                values.put(player_id,String.valueOf(datum.getPlayerId()));
                values.put(name,datum.getName());
                values.put(game,datum.getGame());
                values.put(pic_url,datum.getPicUrl());
                values.put(plays_for,datum.getPlaysFor());
                values.put(skill,datum.getSkill());
                values.put(style,datum.getStyle());
                values.put(runs,datum.getRuns());
                values.put(avg,datum.getAvg());
                values.put(hundreds,datum.getHundreds());
                values.put(fifties,datum.getFifties());
                values.put(sr,datum.getSr());
                values.put(wkt,datum.getWkt());
                values.put(price,datum.getPrice());
                values.put(cd,datum.getCd());
                values.put(md,datum.getMd());

                processId = db.insert(TBL_PLAYERS, null, values);
                Log.e("SQLiteDatabase",datum.toString());
            }


        }catch (SQLException e){
            e.printStackTrace();
        }	finally
        {
            if(db!=null)
                if(db.isOpen())
                    db.close();
        }

        return processId;
    }

    public List<Datum> getPlayers(String team_id){
        List<Datum> list=new ArrayList<>();
        Cursor c = null ;
        try {

            String query = "SELECT * FROM " + TBL_PLAYERS ;

            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    Datum datum=new Datum();
                    datum.setTeamId(c.getInt(c.getColumnIndex(team_id)));
                    datum.setTeamName(c.getString(c.getColumnIndex(team_name)));
                    datum.setPlayerId(c.getInt(c.getColumnIndex(player_id)));
                    datum.setName(c.getString(c.getColumnIndex(name)));
                    datum.setGame(c.getString(c.getColumnIndex(game)));
                    datum.setPicUrl(c.getString(c.getColumnIndex(pic_url)));
                    datum.setPlaysFor(c.getString(c.getColumnIndex(plays_for)));
                    datum.setSkill(c.getString(c.getColumnIndex(skill)));
                    datum.setStyle(c.getString(c.getColumnIndex(style)));
                    datum.setRuns(c.getString(c.getColumnIndex(runs)));
                    datum.setAvg(c.getString(c.getColumnIndex(avg)));
                    datum.setHundreds(c.getString(c.getColumnIndex(hundreds)));
                    datum.setFifties(c.getString(c.getColumnIndex(fifties)));
                    datum.setSr(c.getString(c.getColumnIndex(sr)));
                    datum.setWkt(c.getString(c.getColumnIndex(wkt)));
                    datum.setPrice(c.getString(c.getColumnIndex(price)));
                    datum.setCd(c.getString(c.getColumnIndex(cd)));
                    datum.setMd(c.getString(c.getColumnIndex(md)));

                    list.add(datum);
                    Log.e("Value--->",list.toString());
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

    public List<Datum> getPlayersById(String teamId1,String teamId2,String player_type){
        List<Datum> list=new ArrayList<>();
        Cursor c = null ;
        try {

            String query = "SELECT * FROM " + TBL_PLAYERS +" WHERE "+team_id+" IN ('"+teamId1+"' , '"+teamId2+"') AND "+skill+" = '"+player_type+"'";

            SQLiteDatabase db = this.getReadableDatabase();
            Log.e("SQLiteDatabase",query);
            c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    Datum datum=new Datum();
                    datum.setTeamId(c.getInt(c.getColumnIndex(team_id)));
                    datum.setTeamName(c.getString(c.getColumnIndex(team_name)));
                    datum.setPlayerId(c.getInt(c.getColumnIndex(player_id)));
                    datum.setName(c.getString(c.getColumnIndex(name)));
                    datum.setGame(c.getString(c.getColumnIndex(game)));
                    datum.setPicUrl(c.getString(c.getColumnIndex(pic_url)));
                    datum.setPlaysFor(c.getString(c.getColumnIndex(plays_for)));
                    datum.setSkill(c.getString(c.getColumnIndex(skill)));
                    datum.setStyle(c.getString(c.getColumnIndex(style)));
                    datum.setRuns(c.getString(c.getColumnIndex(runs)));
                    datum.setAvg(c.getString(c.getColumnIndex(avg)));
                    datum.setHundreds(c.getString(c.getColumnIndex(hundreds)));
                    datum.setFifties(c.getString(c.getColumnIndex(fifties)));
                    datum.setSr(c.getString(c.getColumnIndex(sr)));
                    datum.setWkt(c.getString(c.getColumnIndex(wkt)));
                    datum.setPrice(c.getString(c.getColumnIndex(price)));
                    datum.setCd(c.getString(c.getColumnIndex(cd)));
                    datum.setMd(c.getString(c.getColumnIndex(md)));

                    list.add(datum);

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

    public void deleteConfig(){
        try {
            String query="delete from "+ TBL_CONFIG;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            Log.e("SQLiteDatabase",query);
            db.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deletePlayer(){
        try {
            String query="delete from "+ TBL_PLAYERS;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            Log.e("SQLiteDatabase",query);
            db.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
