package com.example.tickethub.Model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "SeatModel")
public class SeatModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "rowId")
    private int rowId;
    @ColumnInfo(name = "columnId")
    private int columnId;
    @ColumnInfo(name = "booked")
    private boolean booked;


    @Ignore
    public SeatModel(int id, int rowId, int columnId) {
        this.id = id;
        this.rowId = rowId;
        this.columnId = columnId;
    }


    public SeatModel(int id,boolean booked) {
        this.id = id;
        this.booked = booked;
    }
    @Ignore
    public SeatModel(List<SeatModel> seatModel) {

    }

    public SeatModel(int id) {
    }

    public boolean isBooked() {
        return booked;
    }

    public int getId() {
        return id;
    }

    public int getRowId() {
        return rowId;
    }

    public int getColumnId() {
        return columnId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}
