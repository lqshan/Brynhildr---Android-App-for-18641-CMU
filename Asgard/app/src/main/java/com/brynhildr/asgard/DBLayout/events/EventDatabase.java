package com.brynhildr.asgard.DBLayout.events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.local.EventWithID;

import java.util.ArrayList;

/**
 * Created by lqshan on 11/13/15.
 */
public class EventDatabase {

    static private EventDatabaseHelper eventDatabaseHelper = null;

    public EventDatabase(Context context) {
        eventDatabaseHelper = new EventDatabaseHelper(context);
    }

    public void deleteTable() {
        SQLiteDatabase db = eventDatabaseHelper.getWritableDatabase();
        eventDatabaseHelper.deleteTable(db);
    }

    public long insertRow(EventWithID event) {
        SQLiteDatabase db = eventDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID, event.getEventID());
        values.put(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME, event.getCOLUMN_NAME_EVENT_NAME());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME, event.getCOLUMN_NAME_DATEANDTIME());
        values.put(EventSchema.EventEntry.COLUMN_NAME_VENUE, event.getCOLUMN_NAME_VENUE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE, event.getCOLUMN_NAME_DRESS_CODE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE, event.getCOLUMN_NAME_TARGET());
        values.put(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE, event.getCOLUMN_NAME_MAX_PEOPLE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION, event.getCOLUMN_NAME_DESCRIPTION());
        values.put(EventSchema.EventEntry.COLUMN_NAME_POSTER, event.getCOLUMN_NAME_POSTER());
        values.put(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID, event.getCOLUMN_NAME_LAUNCHER_ID());
        values.put(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP, event.getCOLUMN_NAME_TIMESTAMP());
        long newRowId;
        newRowId = db.insert(
                EventSchema.EventEntry.TABLE_NAME,
                EventSchema.EventEntry.COLUMN_NAME_NULLABLE,
                values);
        System.out.println("newRowId---->" + newRowId);
        return newRowId;
    }

//    public long insertRow(ArrayList<String> eventDetails) {
//        SQLiteDatabase db = eventDatabaseHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME, eventDetails.get(0));
//        values.put(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME, eventDetails.get(1));
//        values.put(EventSchema.EventEntry.COLUMN_NAME_VENUE, eventDetails.get(2));
//        values.put(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE, eventDetails.get(3));
//        values.put(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE, eventDetails.get(4));
//        values.put(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE, eventDetails.get(5));
//        values.put(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION, eventDetails.get(6));
//        values.put(EventSchema.EventEntry.COLUMN_NAME_POSTER, eventDetails.get(7));
//        values.put(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID, eventDetails.get(8));
//        values.put(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP, eventDetails.get(9));
//        long newRowId;
//        newRowId = db.insert(
//                EventSchema.EventEntry.TABLE_NAME,
//                EventSchema.EventEntry.COLUMN_NAME_NULLABLE,
//                values);
//        System.out.println("newRowId---->" + newRowId);
//        return newRowId;
//    }


    /**
     * Delete the entry if remote database doesn't have it.
     * @param event the event to be deleted.
     * @return the number of rows affected.
     */
    public long deleteRow(EventWithID event) {
        SQLiteDatabase db = eventDatabaseHelper.getWritableDatabase();
        String whereClause = EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID + "= ?";
        String[] whereArgs = {
                event.getEventID()
        };

        return db.delete(EventSchema.EventEntry.TABLE_NAME, whereClause, whereArgs);

    }


    /**
     * Update the row with an event ID
     * @param event the event to be updated.
     * @return the number of rows affected.
     */
    public long updateRow(EventWithID event) {
        SQLiteDatabase db = eventDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID, event.getEventID());
        values.put(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME, event.getCOLUMN_NAME_EVENT_NAME());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME, event.getCOLUMN_NAME_DATEANDTIME());
        values.put(EventSchema.EventEntry.COLUMN_NAME_VENUE, event.getCOLUMN_NAME_VENUE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE, event.getCOLUMN_NAME_DRESS_CODE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE, event.getCOLUMN_NAME_TARGET());
        values.put(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE, event.getCOLUMN_NAME_MAX_PEOPLE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION, event.getCOLUMN_NAME_DESCRIPTION());
        values.put(EventSchema.EventEntry.COLUMN_NAME_POSTER, event.getCOLUMN_NAME_POSTER());
        values.put(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID, event.getCOLUMN_NAME_LAUNCHER_ID());
        values.put(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP, event.getCOLUMN_NAME_TIMESTAMP());

        String whereClause = EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID + "= ?";
        String[] whereArgs = {
                event.getEventID()
        };

        return db.update(EventSchema.EventEntry.TABLE_NAME, values, whereClause, whereArgs);

    }

    public ArrayList<EventWithID> readRowWithID() {
        SQLiteDatabase db = eventDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query(EventSchema.EventEntry.TABLE_NAME,
                new String[]{
                        EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID,
                        EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME,
                        EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME,
                        EventSchema.EventEntry.COLUMN_NAME_VENUE,
                        EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE,
                        EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE,
                        EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE,
                        EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION,
                        EventSchema.EventEntry.COLUMN_NAME_POSTER,
                        EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID,
                        EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP
                },
                null, null, null, null, null);
        ArrayList<EventWithID> events = new ArrayList<EventWithID>();
        while (cursor.moveToNext()) {
            // Create each event object.
            EventWithID event = new EventWithID();
            event.setID(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID)));
            event.setCOLUMN_NAME_EVENT_NAME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME)));
            event.setCOLUMN_NAME_VENUE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_VENUE)));
            event.setCOLUMN_NAME_DATEANDTIME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME)));
            event.setCOLUMN_NAME_DESCRIPTION(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION)));
            event.setCOLUMN_NAME_DRESS_CODE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE)));
            event.setCOLUMN_NAME_POSTER(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_POSTER)));
            event.setCOLUMN_NAME_TARGET(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE)));
            event.setCOLUMN_NAME_MAX_PEOPLE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE)));
            event.setCOLUMN_NAME_LAUNCHER_ID(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID)));
            event.setCOLUMN_NAME_TIMESTAMP(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP)));

            events.add(event);
        }

        return events;
    }

    public ArrayList<Event> readRow() {

        SQLiteDatabase db = eventDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query(EventSchema.EventEntry.TABLE_NAME,
                new String[]{EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME,
                        EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME,
                        EventSchema.EventEntry.COLUMN_NAME_VENUE,
                        EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE,
                        EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE,
                        EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE,
                        EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION,
                        EventSchema.EventEntry.COLUMN_NAME_POSTER,
                        EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID,
                        EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP},
                null, null, null, null, null);
        ArrayList<Event> events = new ArrayList<Event>();
        while (cursor.moveToNext()) {
            // Create each event object.
            Event event = new Event();
            event.setCOLUMN_NAME_EVENT_NAME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME)));
            event.setCOLUMN_NAME_VENUE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_VENUE)));
            event.setCOLUMN_NAME_DATEANDTIME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME)));
            event.setCOLUMN_NAME_DESCRIPTION(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION)));
            event.setCOLUMN_NAME_DRESS_CODE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE)));
            event.setCOLUMN_NAME_POSTER(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_POSTER)));
            event.setCOLUMN_NAME_TARGET(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE)));
            event.setCOLUMN_NAME_MAX_PEOPLE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE)));
            event.setCOLUMN_NAME_LAUNCHER_ID(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID)));
            event.setCOLUMN_NAME_TIMESTAMP(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP)));

            events.add(event);
        }

        return events;

    }

//    public ArrayList<Event> readRow() {
    //        SQLiteDatabase db = eventDatabaseHelper.getReadableDatabase();
//        Cursor cursor = db.query(EventSchema.EventEntry.TABLE_NAME,
//                new String[]{EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME,
//                        EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME,
//                        EventSchema.EventEntry.COLUMN_NAME_VENUE,
//                        EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE,
//                        EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE,
//                        EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE,
//                        EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION,
//                        EventSchema.EventEntry.COLUMN_NAME_POSTER,
//                        EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID,
//                        EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP},
//                null, null, null, null, null);
//        ArrayList<String> dateAndTime = new ArrayList<String>();
//        ArrayList<String> name = new ArrayList<String>();
//        ArrayList<String> address = new ArrayList<String>();
//        ArrayList<String> dressCode = new ArrayList<String>();
//        ArrayList<String> target = new ArrayList<String>();
//        ArrayList<String> max = new ArrayList<String>();
//        ArrayList<String> description = new ArrayList<String>();
//        ArrayList<String> poster = new ArrayList<String>();
//        ArrayList<String> launcher = new ArrayList<String>();
//        ArrayList<String> timestamp = new ArrayList<String>();
//        System.out.println("getColumnCount" + cursor.getColumnCount());
//        System.out.println("getCount" + cursor.getCount());
//        while (cursor.moveToNext()) {
//            // create a new TableRow
//            dateAndTime.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME)));
//            name.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME)));
//            address.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_VENUE)));
//            dressCode.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE)));
//            target.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE)));
//            max.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE)));
//            description.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION)));
//            poster.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_POSTER)));
//            launcher.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID)));
//            timestamp.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP)));
//        }
//
//        ArrayList<Event> event = new ArrayList<Event>();
//        for (int i = 0; i < name.size(); i++) {
//            event.add(new Event(name.get(i), address.get(i), dateAndTime.get(i), description.get(i),
//                    dressCode.get(i), poster.get(i), target.get(i), max.get(i), launcher.get(i)));
//        }
//        return event;
//    }



    public boolean needUpdate(String eventID) {
        SQLiteDatabase db = eventDatabaseHelper.getReadableDatabase();
        String table = EventSchema.EventEntry.TABLE_NAME;
        String[] columns = new String[]{EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP};
        String selection = EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID + "= ?";
        String[] selectionArgs = {
                eventID
        };

        Cursor c = db.query(table, columns, selection, selectionArgs,
                null, null, null, null);

        return false;
    }

    public void close() {
        eventDatabaseHelper.close();
    }
}