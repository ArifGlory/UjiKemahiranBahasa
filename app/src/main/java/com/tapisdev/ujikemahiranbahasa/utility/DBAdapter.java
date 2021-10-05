package com.tapisdev.ujikemahiranbahasa.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Glory on 28/09/2016.
 */
public class DBAdapter extends SQLiteAssetHelper {

    private static final String DB_NAME = "db_uji_kemahiran";
    private static final int DB_VER = 1;
    public static final String TABLE_MENDENGARKAN = "seksi_mendengarkan";
    public static final String TABLE_MEMBACA = "seksi_membaca";
    public static final String TABLE_KAIDAH = "seksi_merespons_kaidah";
    public static final String TABLE_HISTORY = "tb_history";

    public static final String COL_MENDENGARKAN_ID = "id_soal";
    public static final String COL_MENDENGARKAN_DIALOG = "dialog";
    public static final String COL_MENDENGARKAN_GAMBAR = "gambar";
    public static final String COL_MENDENGARKAN_SOAL = "soal";
    public static final String COL_MENDENGARKAN_JAWABAN_A = "jawaban_a";
    public static final String COL_MENDENGARKAN_JAWABAN_B = "jawaban_b";
    public static final String COL_MENDENGARKAN_JAWABAN_C = "jawaban_c";
    public static final String COL_MENDENGARKAN_JAWABAN_D = "jawaban_d";
    public static final String COL_MENDENGARKAN_JAWABAN_BENAR = "jawaban_benar";


    public static final String TAG_INSERT = "insertDB";

    public static DBAdapter dbInstance;
    public static SQLiteDatabase db;

    String[] namaLevel = {"Paket Soal 1", "Paket Soal 2", "Paket Soal 3", "Paket Soal 4", "Paket Soal 5",
            "Paket Soal 6", "Paket Soal 7", "Paket Soal 8", "Paket Soal 9", "Paket Soal 10"};


    /**
     * private Constructor, untuk menggunakan kelas ini gunakan getInstance()
     *
     * @param context
     */


    private DBAdapter(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }


    public static synchronized DBAdapter getInstance(Context context) {

        if (dbInstance == null) {
            dbInstance = new DBAdapter(context.getApplicationContext());
            db = dbInstance.getReadableDatabase();
        }

        return dbInstance;
    }

    public SQLiteDatabase ambilDB() {
        db = this.getWritableDatabase();
        return db;


    }

   /* public DBAdapter getInstance(Context context){

        if (dbInstance == null){

            dbInstance = new DBAdapter(context);
            db = dbInstance.getReadableDatabase();

        }

        return  dbInstance;
    }*/


    @Override
    public synchronized void close() {

        super.close();
        if (dbInstance != null) {

            dbInstance.close();
        }
    }


//method untuk mengambil semua data soal
    /*public List<Quiz> getAllSoal(String namaPaket){

        List<Quiz> listSoal = new ArrayList<Quiz>();
        String tabel_soal = "";
        if (namaPaket.equals(namaLevel[0])){
            tabel_soal = TABLE_SOAL;
        }else if (namaPaket.equals(namaLevel[1])){
            tabel_soal = TABLE_SOAL_2;
        }else if (namaPaket.equals(namaLevel[2])){
            tabel_soal = TABLE_SOAL_3;
        }else if (namaPaket.equals(namaLevel[3])){
            tabel_soal = TABLE_SOAL_4;
        }else if (namaPaket.equals(namaLevel[4])){
            tabel_soal = TABLE_SOAL_5;
        }else if (namaPaket.equals(namaLevel[5])){
            tabel_soal = TABLE_SOAL_6;
        }else if (namaPaket.equals(namaLevel[6])){
            tabel_soal = TABLE_SOAL_7;
        }else if (namaPaket.equals(namaLevel[7])){
            tabel_soal = TABLE_SOAL_8;
        }else if (namaPaket.equals(namaLevel[8])){
            tabel_soal = TABLE_SOAL_9;
        }else if (namaPaket.equals(namaLevel[9])){
            tabel_soal = TABLE_SOAL_10;
        }

        Cursor cursor = db.query(tabel_soal,new String[]{

               COL_SOAL_ID,
                COL_SOAL_SOAL,
                COL_SOAL_JAWABAN_A,
                COL_SOAL_JAWABAN_B,
                COL_SOAL_JAWABAN_C,
                COL_SOAL_JAWABAN_D,
                COL_SOAL_JAWABAN_BENAR
                },null,null,null,null,null);//kenapa ada 5 null ya ?

        if (cursor.moveToFirst()){

            do {
                Quiz quiz = new Quiz();

                quiz.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_SOAL_ID)));
                quiz.setSoal(cursor.getString(cursor.getColumnIndexOrThrow(COL_SOAL_SOAL)));
                quiz.setJawaban_a(cursor.getString(cursor.getColumnIndexOrThrow(COL_SOAL_JAWABAN_A)));
                quiz.setJawaban_b(cursor.getString(cursor.getColumnIndexOrThrow(COL_SOAL_JAWABAN_B)));
                quiz.setJawaban_c(cursor.getString(cursor.getColumnIndexOrThrow(COL_SOAL_JAWABAN_C)));
                quiz.setJawaban_d(cursor.getString(cursor.getColumnIndexOrThrow(COL_SOAL_JAWABAN_D)));
                quiz.setJawaban_benar(cursor.getString(cursor.getColumnIndexOrThrow(COL_SOAL_JAWABAN_BENAR)));

            listSoal.add(quiz);
            }while (cursor.moveToNext());

        }

        return listSoal;
    }

    public List<History> getAllHistory(){
        List<History> listHistory = new ArrayList<>();

        Cursor cursor = db.query(TABLE_HISTORY,new String[]{

                COL_SOAL_ID,
                COL_HISTORI_NAMA_PAKET,
                COL_HISTORI_SKOR,
                COL_HISTORI_WAKTU,
        },null,null,null,null,null);//kenapa ada 5 null ya ?

        if (cursor.moveToFirst()){

            do {
                Quiz quiz = new Quiz();
                History history = new History();

                history.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_SOAL_ID)));
                history.setNamaPaket(cursor.getString(cursor.getColumnIndexOrThrow(COL_HISTORI_NAMA_PAKET)));
                history.setSkor(cursor.getString(cursor.getColumnIndexOrThrow(COL_HISTORI_SKOR)));
                history.setWaktu(cursor.getString(cursor.getColumnIndexOrThrow(COL_HISTORI_WAKTU)));

                listHistory.add(history);
            }while (cursor.moveToNext());

        }

        return listHistory;
    }*/

    public long insertHistory(String namaPaket, String skor, String waktu) {
        final SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        long retval = 0;
        try {
            initialValues.put("nama_paket_soal", namaPaket);
            initialValues.put("skor", skor);
            initialValues.put("waktu", waktu);
            retval = db.insert(TABLE_HISTORY, null, initialValues);
        } catch (Exception e) {
            Log.e(TAG_INSERT, "insertRow exception", e);
        } finally {
            db.close();
        }
        return retval;
    }


}
