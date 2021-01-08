package com.railways.ecsoket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.railways.ecsoket.data.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class EcsocketDatabase extends SQLiteOpenHelper {

    public String Vpa = "VPA";

    // Database Name
    private final static String DATABASE_NAME = "EcSockettestdatabase";

    // Tables
    private final String TABLE_SECTION_INCHARGE = "table_section";
    private final String TABLE_SECTION = "table_section_data";
    private final String TABLE_BLOCK = "table_block";
    private final String TABLE_SOCKET = "table_socket";
    private final String TABLE_SOCKET_NEW = "table_socket_new";
    private final String TABLE_SOCKET_ADD = "table_socket_add";

    private final String TABLE_FAILURE = "table_failure";



    // All Static variables
    private final String SECTION_INC_ID = "section_inc_id";
    private final String SECTION_INC_NAME = "section_inc_name";

    private final String SECTION_DATA_ID = "section_data_id";
    private final String SECTION_NAME = "section_name_data";


    private final String BLOCK_ID = "block_id";
    private final String BLOCK_NAME = "block_name";

    private final String SOCKET_ID = "socket_id";
    private final String SOCKET_NAME = "socket_name";


    private final String SOCKET_DATA = "socket_data";
    private final String SOCKET_STATUS = "socket_status";
    private final String SOCKET_STATUS_ID = "socket_status_id";


    private final String NEW_SOCKET_DATA = "new_socket_data";
    private final String NEW_SOCKET_STATUS = "new_socket_status";
    private final String NEW_SOCKET_STATUS_ID = "new_socket_status_id";


    private final String NEW_FAIL_DATA = "new_fail_data";
    private final String NEW_FAIL_STATUS = "new_fail_status";
    private final String NEW_FAIL_STATUS_ID = "new_fail_status_id";





    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static EcsocketDatabase ecsocketDatabase = null;

    @Inject
    public EcsocketDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Inject
    PreferenceManager mPref;

    public static EcsocketDatabase getDatabaseManagerInstance(Context context) {
        if (ecsocketDatabase == null) {
            ecsocketDatabase = new EcsocketDatabase(context);
        }
        return ecsocketDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String create_TABLE_SECTION_INC = "create table if not exists " + TABLE_SECTION_INCHARGE + " ("
                + SECTION_INC_ID + " text,"
                + SECTION_INC_NAME + " text"
                +" )";
        db.execSQL(create_TABLE_SECTION_INC);


        /*String create_TABLE_SECTION = "create table if not exists " + TABLE_SECTION + " ("
                + SECTION_INC_ID + " text,"
                + SECTION_DATA_ID + " text,"
                + SECTION_NAME + "text"
                +" )";
        db.execSQL(create_TABLE_SECTION);*/


        String create_TABLE_CONTACT_DETAILS = "create table if not exists " + TABLE_SECTION + " ("
                + SECTION_INC_ID + " text,"
                + SECTION_DATA_ID + " text,"
                + SECTION_NAME + " text"
                + " )";
        db.execSQL(create_TABLE_CONTACT_DETAILS);


        String create_TABLE_BLOCK_DETAILS = "create table if not exists " + TABLE_BLOCK + " ("
                + SECTION_DATA_ID + " text,"
                + BLOCK_ID + " text,"
                + BLOCK_NAME+ " text"

                + " )";
        db.execSQL(create_TABLE_BLOCK_DETAILS);


        String create_TABLE_SOCKET_DETAILS = "create table if not exists " + TABLE_SOCKET + " ("
                + BLOCK_ID + " text,"
                + SOCKET_ID + " text,"
                + SOCKET_NAME+ " text"
                + " )";
        db.execSQL(create_TABLE_SOCKET_DETAILS);


        String create_TABLE_SOCKET_NEW= "create table if not exists " + TABLE_SOCKET_NEW + " ("
                + SOCKET_STATUS_ID + " text,"
                + SOCKET_DATA + " text,"
                + SOCKET_STATUS + " text"
                + " )";
        db.execSQL(create_TABLE_SOCKET_NEW);


        String create_TABLE_SOCKET_ADD= "create table if not exists " + TABLE_SOCKET_ADD + " ("
                + NEW_SOCKET_STATUS_ID + " text,"
                + NEW_SOCKET_DATA + " text,"
                + NEW_SOCKET_STATUS + " text"
                + " )";
        db.execSQL(create_TABLE_SOCKET_ADD);

        String create_TABLE_FAILURE= "create table if not exists " + TABLE_FAILURE + " ("
                + NEW_FAIL_STATUS_ID + " text,"
                + NEW_FAIL_DATA + " text,"
                + NEW_FAIL_STATUS + " text"
                + " )";
        db.execSQL(create_TABLE_FAILURE);





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Timber.d("onUpgrade() from " + oldVersion + " to " + newVersion);
    }

    public boolean isSectionIdInDatabase(String sectionId) {
        Cursor cursor = getReadableDatabase().query(TABLE_SECTION_INCHARGE, new String[] { SECTION_INC_ID }, SECTION_INC_ID + "=?", new String[] { sectionId }, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }




    public void saveSectionData(String sectionId, String data) {
        if (isSectionIdInDatabase(sectionId)) {
            updateSectionData(sectionId,data);
        }else{
            insertSectionData(sectionId, data);
        }

    }

    private void updateSectionData(String sectionId, String name) {

        Cursor cursor = getReadableDatabase().query(TABLE_SECTION_INCHARGE, new String[] { SECTION_INC_ID }, SECTION_INC_ID + "=?", new String[] { sectionId }, null, null, null);
        if (cursor.moveToFirst()) {
            try {
                ContentValues values = new ContentValues();
                values.put(SECTION_INC_ID, sectionId);
                values.put(SECTION_INC_NAME, name);
                getWritableDatabase().update(TABLE_SECTION_INCHARGE, values, SECTION_INC_ID + "=?", new String[] { SECTION_INC_ID });
            } catch (NumberFormatException e) {
                Timber.e("ERROR EXCEPTION %s", e.getMessage());
            }
        }
        cursor.close();
    }


    private void insertSectionData(String subjectId, String data) {
        ContentValues values = new ContentValues();

        values.put(SECTION_INC_ID, subjectId);
        values.put(SECTION_INC_NAME, data);

        getWritableDatabase().insert(TABLE_SECTION_INCHARGE, null, values);

    }



    public String getSectionDetails(String id){
        Cursor cursor = getReadableDatabase().query(TABLE_SECTION_INCHARGE, new String[] { SECTION_INC_ID }, SECTION_INC_NAME + "=?", new String[] { id }, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return "";
    }

    public ArrayList<String> getSectionList() {
        Cursor cursor = getReadableDatabase().query(TABLE_SECTION_INCHARGE, new String[] { SECTION_INC_NAME }, null, null, null, null, null);
        return getList(cursor);
    }



    public long  getCounts(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_SECTION_INCHARGE);
        db.close();
        return count;

    }


    /*--SECTION DATA---*/


    public boolean isSectionDataIdInDatabase(String sectionDataId) {
        Cursor cursor = getReadableDatabase().query(TABLE_SECTION, new String[] { SECTION_DATA_ID }, SECTION_DATA_ID + "=?", new String[] { sectionDataId }, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }




    public void saveSectionDetails(String sectionDataId, String data,String SectionId) {
        if (isSectionDataIdInDatabase(sectionDataId)) {
            updateSectionDetails(sectionDataId,data,SectionId);
        }else{
            insertSectionDetails(sectionDataId, data,SectionId);
        }

    }

    private void updateSectionDetails(String sectionDataId, String name,String SectionId) {

        Cursor cursor = getReadableDatabase().query(TABLE_SECTION, new String[] { SECTION_DATA_ID }, SECTION_DATA_ID + "=?", new String[] { sectionDataId }, null, null, null);
        if (cursor.moveToFirst()) {
            try {
                ContentValues values = new ContentValues();
                values.put(SECTION_INC_ID, SectionId);
                values.put(SECTION_DATA_ID, sectionDataId);
                values.put(SECTION_NAME, name);
                getWritableDatabase().update(TABLE_SECTION, values, SECTION_DATA_ID + "=?", new String[] { SECTION_DATA_ID });
            } catch (NumberFormatException e) {
                Timber.e("ERROR EXCEPTION %s", e.getMessage());
            }
        }
        cursor.close();
    }


    private void insertSectionDetails(String subjectId,String data,String SectionId) {
        ContentValues values = new ContentValues();
        values.put(SECTION_INC_ID, SectionId);
        values.put(SECTION_DATA_ID, subjectId);
        values.put(SECTION_NAME, data);
        getWritableDatabase().insert(TABLE_SECTION, null, values);

    }



    public String getSectionDataDetails(String id){
        Cursor cursor = getReadableDatabase().query(TABLE_SECTION, new String[] { SECTION_DATA_ID }, SECTION_NAME + "=?", new String[] { id }, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return "";
    }

    public ArrayList<String> getSectionDataList(String id) {
        Cursor cursor = getReadableDatabase().query(TABLE_SECTION, new String[] { SECTION_NAME }, SECTION_INC_ID + "=?", new String[] { id }, null, null, null);
        return getList(cursor);
    }



/*--- BLOCK DATA*/



    public boolean isblockIdInDatabase(String blockId) {
        Cursor cursor = getReadableDatabase().query(TABLE_BLOCK, new String[] { BLOCK_ID }, BLOCK_ID + "=?", new String[] { blockId }, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }




    public void saveBlockDetails(String blockId, String data,String SectionId) {
        if (isblockIdInDatabase(blockId)) {
            updateBlockDetails(blockId,data,SectionId);
        }else{
            insertBlockDetails(blockId, data,SectionId);
        }

    }

    private void updateBlockDetails(String blockId, String name,String SectionId) {

        Cursor cursor = getReadableDatabase().query(TABLE_BLOCK, new String[] { BLOCK_ID }, BLOCK_ID + "=?", new String[] { blockId }, null, null, null);
        if (cursor.moveToFirst()) {
            try {
                ContentValues values = new ContentValues();
                values.put(SECTION_DATA_ID, SectionId);
                values.put(BLOCK_ID, blockId);
                values.put(BLOCK_NAME, name);
                getWritableDatabase().update(TABLE_BLOCK, values, BLOCK_ID + "=?", new String[] { BLOCK_ID });
            } catch (NumberFormatException e) {
                Timber.e("ERROR EXCEPTION %s", e.getMessage());
            }
        }
        cursor.close();
    }


    private void insertBlockDetails(String blockId,String data,String SectionId) {
        ContentValues values = new ContentValues();
        values.put(SECTION_DATA_ID, SectionId);
        values.put(BLOCK_ID, blockId);
        values.put(BLOCK_NAME, data);
        getWritableDatabase().insert(TABLE_BLOCK, null, values);

    }



    public String getBlockDetails(String id){
        Cursor cursor = getReadableDatabase().query(TABLE_BLOCK, new String[] { BLOCK_ID }, BLOCK_NAME + "=?", new String[] { id }, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return "";
    }

    public ArrayList<String> getBlockList(String id) {
        Cursor cursor = getReadableDatabase().query(TABLE_BLOCK, new String[] { BLOCK_NAME }, SECTION_DATA_ID + "=?", new String[] { id }, null, null, null);
        return getList(cursor);
    }



  /*--- Sockets*/

    public boolean issocketIdInDatabase(String socketId) {
        Cursor cursor = getReadableDatabase().query(TABLE_SOCKET, new String[] { SOCKET_ID }, SOCKET_ID + "=?", new String[] { socketId }, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }




    public void saveSocketDetails(String socketId, String data,String blockId) {
        if (issocketIdInDatabase(socketId)) {
            updateSocketDetails(socketId,data,blockId);
        }else{
            insertSocketDetails(socketId, data,blockId);
        }

    }

    private void updateSocketDetails(String socketId, String name,String blockId) {

        Cursor cursor = getReadableDatabase().query(TABLE_SOCKET, new String[] { SOCKET_ID }, SOCKET_ID + "=?", new String[] { blockId }, null, null, null);
        if (cursor.moveToFirst()) {
            try {
                ContentValues values = new ContentValues();
                values.put(BLOCK_ID, blockId);
                values.put(SOCKET_ID, socketId);
                values.put(SOCKET_NAME, name);
                getWritableDatabase().update(TABLE_SOCKET, values, SOCKET_ID + "=?", new String[] { SOCKET_ID });
            } catch (NumberFormatException e) {
                Timber.e("ERROR EXCEPTION %s", e.getMessage());
            }
        }
        cursor.close();
    }


    private void insertSocketDetails(String socketId,String data,String blockId) {
        ContentValues values = new ContentValues();
        values.put(BLOCK_ID, blockId);
        values.put(SOCKET_ID, socketId);
        values.put(SOCKET_NAME, data);
        getWritableDatabase().insert(TABLE_SOCKET, null, values);

    }





    public String getSocketDetails(String id){
        Cursor cursor = getReadableDatabase().query(TABLE_SOCKET, new String[] { SOCKET_ID }, SOCKET_NAME + "=?", new String[] { id }, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return "";
    }

    public ArrayList<String> getSocketList(String id) {
        Cursor cursor = getReadableDatabase().query(TABLE_SOCKET, new String[] { SOCKET_NAME }, BLOCK_ID + "=?", new String[] { id }, null, null, null);
        return getList(cursor);
    }


    public void insertSocketId(String data,String status,String id) {
        ContentValues values = new ContentValues();
        values.put(SOCKET_STATUS_ID, id);
        values.put(SOCKET_DATA, data);
        values.put(SOCKET_STATUS, status);
        getWritableDatabase().insert(TABLE_SOCKET_NEW, null, values);

    }


    public String getSocketStatus(String id) {
        Cursor cursor = getReadableDatabase().query(TABLE_SOCKET_NEW, new String[] { SOCKET_DATA }, SOCKET_STATUS + "=?", new String[] { id }, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return "";
    }


    public void deleteMessageIdTable(String Id) {
        if(Id.isEmpty())
            return;

        Cursor cursor = getReadableDatabase().query(TABLE_SOCKET_NEW, new String[] { SOCKET_DATA }, SOCKET_STATUS_ID + "=?", new String[] { Id }, null, null, null);

        if (cursor.moveToFirst()) {
            try {
                // ContentValues values = new ContentValues();
                // values.put(CHAT_MSG,message);
                getWritableDatabase().delete(TABLE_SOCKET_NEW, SOCKET_STATUS_ID + "=?", new String[] { Id });
            } catch (NumberFormatException e) {
                Timber.e("ERROR EXCEPTION %s", e.getMessage());
            }
        }
        cursor.close();
    }



    public long  getStatusCounts(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_SOCKET_NEW);
        db.close();
        return count;
    }




    public void insertNewSocketId(String data,String status,String id) {
        ContentValues values = new ContentValues();
        values.put(NEW_SOCKET_STATUS_ID, id);
        values.put(NEW_SOCKET_DATA, data);
        values.put(NEW_SOCKET_STATUS, status);
        getWritableDatabase().insert(TABLE_SOCKET_ADD, null, values);

    }


    public String getNewSocketStatus(String id) {
        Cursor cursor = getReadableDatabase().query(TABLE_SOCKET_ADD, new String[] { NEW_SOCKET_DATA }, NEW_SOCKET_STATUS + "=?", new String[] { id }, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return "";
    }


    public void deleteNewMessageIdTable(String Id) {
        if(Id.isEmpty())
            return;

        Cursor cursor = getReadableDatabase().query(TABLE_SOCKET_ADD, new String[] { NEW_SOCKET_DATA }, NEW_SOCKET_STATUS_ID + "=?", new String[] { Id }, null, null, null);

        if (cursor.moveToFirst()) {
            try {
                // ContentValues values = new ContentValues();
                // values.put(CHAT_MSG,message);
                getWritableDatabase().delete(TABLE_SOCKET_ADD, NEW_SOCKET_STATUS_ID + "=?", new String[] { Id });
            } catch (NumberFormatException e) {
                Timber.e("ERROR EXCEPTION %s", e.getMessage());
            }
        }
        cursor.close();
    }



    public long  getNewStatusCounts(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_SOCKET_ADD);
        db.close();
        return count;
    }






    public void insertFailId(String data,String status,String id) {
        ContentValues values = new ContentValues();
        values.put(NEW_FAIL_STATUS_ID, id);
        values.put(NEW_FAIL_DATA, data);
        values.put(NEW_FAIL_STATUS, status);
        getWritableDatabase().insert(TABLE_FAILURE, null, values);

    }


    public String getFailStatus(String id) {
        Cursor cursor = getReadableDatabase().query(TABLE_FAILURE, new String[] { NEW_FAIL_DATA }, NEW_FAIL_STATUS + "=?", new String[] { id }, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return "";
    }


    public void deleteFailIdTable(String Id) {
        if(Id.isEmpty())
            return;

        Cursor cursor = getReadableDatabase().query(TABLE_FAILURE, new String[] { NEW_FAIL_DATA }, NEW_FAIL_STATUS_ID + "=?", new String[] { Id }, null, null, null);

        if (cursor.moveToFirst()) {
            try {
                // ContentValues values = new ContentValues();
                // values.put(CHAT_MSG,message);
                getWritableDatabase().delete(TABLE_FAILURE, NEW_FAIL_STATUS_ID + "=?", new String[] { Id });
            } catch (NumberFormatException e) {
                Timber.e("ERROR EXCEPTION %s", e.getMessage());
            }
        }
        cursor.close();
    }



    public long  getFailCounts(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_FAILURE);
        db.close();
        return count;
    }



    private ArrayList<String> getList(Cursor cursor) {
        ArrayList<String> messagesList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                messagesList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return messagesList;
    }

}