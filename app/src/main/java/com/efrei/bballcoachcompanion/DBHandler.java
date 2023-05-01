package com.efrei.bballcoachcompanion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.efrei.bballcoachcompanion.Modal.RencontreModal;
import com.efrei.bballcoachcompanion.Modal.StatistiqueModal;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "bbCoachCompanion";

    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME1 = "rencontre";
    private static final String TABLE_NAME2 = "statistique";
    private static final String ID = "id_match";
    private static final String EQUIPE_1 = "equipe_1";
    private static final String EQUIPE = "equipe";
    private static final String EQUIPE_2 = "equipe_2";
    private static final String SCORE = "score";
    private static final String BEST_SCOREUR = "meilleur_marqueur";
    private static final String PTS_INSCRIT = "point_inscrit";
    private static final String DATE = "date_match";
    private static final String ID_STAT = "id_stat";
    private static final String Q1 = "point_inscrit_Q1";
    private static final String Q2 = "point_inscrit_Q2";
    private static final String Q3 = "point_inscrit_Q3";
    private static final String Q4 = "point_inscrit_Q4";
    private static final String TT_ASSIST = "total_assist";
    private static final String TT_CONTRE = "total_contre";
    private static final String TT_REBOND = "total_rebond";
    private static final String TT_FAUTE = "total_faute";
    private static final String TT_TO = "total_turnover";





    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE "+ TABLE_NAME1+" ( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                EQUIPE_1+" TEXT, "+EQUIPE_2 +" TEXT,"+ SCORE+" TEXT,"+ BEST_SCOREUR+" TEXT,"+ PTS_INSCRIT+" INTEGER," +
                DATE+" TEXT)";

        String query2 = "CREATE TABLE "+TABLE_NAME2+" ( "+ID_STAT+" INTEGER primary key,"+ID+" INTEGER," +
                EQUIPE+" text," +Q1+" INTEGER,"+Q2+" INTEGER,"+Q3+"  INTEGER," +Q4+
                " INTEGER," +TT_ASSIST+" INTEGER, "+ TT_CONTRE+" INTEGER,"+TT_REBOND+" INTEGER, " +
                TT_TO+" INTEGER,"+TT_FAUTE+" INTEGER, foreign key(" + ID+ ") references "+TABLE_NAME1+"(" + ID+ "))";

        db.execSQL(query1);
        db.execSQL(query2);
    }

    public void addNewMatch(String eq1, String eq2, String score, String mm, int pts, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EQUIPE_2,eq2);
        values.put(SCORE,score);
        values.put(DATE,date);
        values.put(EQUIPE_1,eq1);
        values.put(BEST_SCOREUR,mm);
        values.put(PTS_INSCRIT,pts);

        db.insert(TABLE_NAME1, null,values);
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME1,null);
        cursor.close();
        db.close();
    }

    public ArrayList<RencontreModal> readRencontre(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME1,null);

        ArrayList<RencontreModal> arrayList =new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                arrayList.add(new RencontreModal(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6)));
            }while (cursor.moveToNext());
        }
        Log.d("aeptibaetb", arrayList.toString());
        cursor.close();
        return arrayList;
    }


    public void addNewStatistique(int id, String eq, int q1, int q2, int q3, int q4, int as, int rb, int cnt, int to, int pf){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID,id);
        values.put(EQUIPE,eq);
        values.put(Q1,q1);
        values.put(Q2,q2);
        values.put(Q3,q3);
        values.put(Q4,q4);
        values.put(TT_ASSIST,as);
        values.put(TT_REBOND,rb);
        values.put(TT_CONTRE,cnt);
        values.put(TT_TO,to);
        values.put(TT_FAUTE,pf);

        db.insert(TABLE_NAME2, null,values);
        db.close();
    }

    public ArrayList<StatistiqueModal> readStatistique(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME2+" WHERE "+ID+" IN(SELECT * FROM "+TABLE_NAME1+"ORDER BY"+DATE+"DESC LIMIT 5)",null);
        ArrayList<StatistiqueModal>statistiqueModals = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                statistiqueModals.add(new StatistiqueModal(cursor.getInt(1),
                        cursor.getString(2), cursor.getInt(3),cursor.getInt(4),
                        cursor.getInt(5), cursor.getInt(6), cursor.getInt(7),
                        cursor.getInt(8), cursor.getInt(9), cursor.getInt(10),
                        cursor.getInt(11)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return statistiqueModals;
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }
}
