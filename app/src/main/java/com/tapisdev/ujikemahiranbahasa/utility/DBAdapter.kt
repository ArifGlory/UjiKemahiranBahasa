package com.tapisdev.ujikemahiranbahasa.utility

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import com.tapisdev.ujikemahiranbahasa.utility.DBAdapter
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.tapisdev.ujikemahiranbahasa.model.SeksiMembaca
import com.tapisdev.ujikemahiranbahasa.model.SeksiMendengarkan
import com.tapisdev.ujikemahiranbahasa.model.SeksiMeresponsKaidah
import java.lang.Exception
import java.util.ArrayList

/**
 * Created by Glory on 28/09/2016.
 */
class DBAdapter
/**
 * private Constructor, untuk menggunakan kelas ini gunakan getInstance()
 *
 * @param context
 */
private constructor(context: Context) : SQLiteAssetHelper(context, DB_NAME, null, DB_VER) {
    var listSoal : ArrayList<SeksiMendengarkan> = ArrayList<SeksiMendengarkan>()
    var listSoalKaidah : ArrayList<SeksiMeresponsKaidah> = ArrayList<SeksiMeresponsKaidah>()
    var listSoalMembaca : ArrayList<SeksiMembaca> = ArrayList<SeksiMembaca>()
    var namaLevel = arrayOf(
        "Paket Soal 1", "Paket Soal 2", "Paket Soal 3", "Paket Soal 4", "Paket Soal 5",
        "Paket Soal 6", "Paket Soal 7", "Paket Soal 8", "Paket Soal 9", "Paket Soal 10"
    )

    fun ambilDB(): SQLiteDatabase? {
        db = this.writableDatabase
        return db
    }

    /* public DBAdapter getInstance(Context context){

        if (dbInstance == null){

            dbInstance = new DBAdapter(context);
            db = dbInstance.getReadableDatabase();

        }

        return  dbInstance;
    }*/
    @Synchronized
    override fun close() {
        super.close()
        if (dbInstance != null) {
            dbInstance!!.close()
        }
    }

    //method untuk mengambil semua data soal seksi mendengarkan
    fun getSoalMendengarkan(): ArrayList<SeksiMendengarkan> {
        //var listSoal : ArrayList<SeksiMendengarkan> = ArrayList<SeksiMendengarkan>()
        var tabel_soal: String? = "seksi_mendengarkan"
        listSoal.clear()

        val cursor = db!!.query(
            tabel_soal, arrayOf(
                "id_soal",
                "dialog",
                "soal",
                "gambar",
                "jawaban_a",
                "jawaban_b",
                "jawaban_c",
                "jawaban_d",
                "jawaban_benar",
                "id_paket"
            ), null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                var quiz  : SeksiMendengarkan = SeksiMendengarkan(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_soal")),
                    cursor.getString(cursor.getColumnIndexOrThrow("dialog")),
                    cursor.getString(cursor.getColumnIndexOrThrow("soal")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_a")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_b")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_c")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_d")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_benar")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_paket"))
                )
               listSoal.add(quiz)
            } while (cursor.moveToNext())
        }
        return listSoal
    }

    //method untuk mengambil semua data soal seksi merespons kaidah
    fun getSoalMeresponsKaidah(): ArrayList<SeksiMeresponsKaidah> {
        //var listSoal : ArrayList<SeksiMendengarkan> = ArrayList<SeksiMendengarkan>()
        var tabel_soal: String? = "seksi_merespons_kaidah"
        listSoalKaidah.clear()

        val cursor = db!!.query(
            tabel_soal, arrayOf(
                "id_soal",
                "tipe_soal",
                "dialog_1",
                "dialog_2",
                "monolog",
                "jawaban_a",
                "jawaban_b",
                "jawaban_c",
                "jawaban_d",
                "jawaban_benar",
                "id_paket"
            ), null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                var quiz  : SeksiMeresponsKaidah = SeksiMeresponsKaidah(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_soal")),
                    cursor.getString(cursor.getColumnIndexOrThrow("tipe_soal")),
                    cursor.getString(cursor.getColumnIndexOrThrow("dialog_1")),
                    cursor.getString(cursor.getColumnIndexOrThrow("dialog_2")),
                    cursor.getString(cursor.getColumnIndexOrThrow("monolog")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_a")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_b")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_c")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_d")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_benar")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_paket"))
                )
                listSoalKaidah.add(quiz)
            } while (cursor.moveToNext())
        }
        return listSoalKaidah
    }

    //method untuk mengambil semua data soal seksi membaca
    fun getSoalMembaca(): ArrayList<SeksiMembaca> {
        //var listSoal : ArrayList<SeksiMendengarkan> = ArrayList<SeksiMendengarkan>()
        var tabel_soal: String? = "seksi_membaca"
        listSoalMembaca.clear()

        var crsr = db!!.rawQuery("SELECT seksi_membaca.*,table_bacaan.bacaan,table_bacaan.gambar FROM seksi_membaca INNER JOIN table_bacaan ON seksi_membaca.id_bacaan=table_bacaan.id_bacaan", null);

        /*val cursor = db!!.query(
            tabel_soal, arrayOf(
                "id_soal",
                "tipe_soal",
                "dialog_1",
                "dialog_2",
                "monolog",
                "jawaban_a",
                "jawaban_b",
                "jawaban_c",
                "jawaban_d",
                "jawaban_benar",
                "id_paket"
            ), null, null, null, null, null
        )*/

        if (crsr.moveToFirst()) {
            do {
                var quiz  : SeksiMembaca = SeksiMembaca(
                    crsr.getInt(crsr.getColumnIndexOrThrow("id_soal")),
                    crsr.getInt(crsr.getColumnIndexOrThrow("id_bacaan")),
                    crsr.getString(crsr.getColumnIndexOrThrow("soal")),
                    crsr.getString(crsr.getColumnIndexOrThrow("bacaan")),
                    crsr.getString(crsr.getColumnIndexOrThrow("gambar")),
                    crsr.getString(crsr.getColumnIndexOrThrow("jawaban_a")),
                    crsr.getString(crsr.getColumnIndexOrThrow("jawaban_b")),
                    crsr.getString(crsr.getColumnIndexOrThrow("jawaban_c")),
                    crsr.getString(crsr.getColumnIndexOrThrow("jawaban_d")),
                    crsr.getString(crsr.getColumnIndexOrThrow("jawaban_benar")),
                    crsr.getInt(crsr.getColumnIndexOrThrow("id_paket"))
                )
                listSoalMembaca.add(quiz)
            } while (crsr.moveToNext())
        }
        return listSoalMembaca
    }


    /*
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
    fun insertHistory(namaPaket: String?, skor: String?, waktu: String?): Long {
        val db = this.writableDatabase
        val initialValues = ContentValues()
        var retval: Long = 0
        try {
            initialValues.put("nama_paket_soal", namaPaket)
            initialValues.put("skor", skor)
            initialValues.put("waktu", waktu)
            retval = db.insert(TABLE_HISTORY, null, initialValues)
        } catch (e: Exception) {
            Log.e(TAG_INSERT, "insertRow exception", e)
        } finally {
            db.close()
        }
        return retval
    }

    companion object {
        private const val DB_NAME = "db_uji_kemahiran_3"
        private const val DB_VER = 1
        const val TABLE_MENDENGARKAN = "seksi_mendengarkan"
        const val TABLE_MEMBACA = "seksi_membaca"
        const val TABLE_KAIDAH = "seksi_merespons_kaidah"
        const val TABLE_HISTORY = "tb_history"
        const val COL_MENDENGARKAN_ID = "id_soal"
        const val COL_MENDENGARKAN_DIALOG = "dialog"
        const val COL_MENDENGARKAN_GAMBAR = "gambar"
        const val COL_MENDENGARKAN_SOAL = "soal"
        const val COL_MENDENGARKAN_JAWABAN_A = "jawaban_a"
        const val COL_MENDENGARKAN_JAWABAN_B = "jawaban_b"
        const val COL_MENDENGARKAN_JAWABAN_C = "jawaban_c"
        const val COL_MENDENGARKAN_JAWABAN_D = "jawaban_d"
        const val COL_MENDENGARKAN_JAWABAN_BENAR = "jawaban_benar"
        const val TAG_INSERT = "insertDB"
        var dbInstance: DBAdapter? = null
        var db: SQLiteDatabase? = null
        @Synchronized
        fun getInstance(context: Context): DBAdapter? {
            if (dbInstance == null) {
                dbInstance = DBAdapter(context.applicationContext)
                db = dbInstance!!.readableDatabase
            }
            return dbInstance
        }
    }
}