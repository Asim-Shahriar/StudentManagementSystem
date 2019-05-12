package com.example.user.studentmanagementsystem;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name,roll_no,marks;
     Button addbtn,deletebtn,modifybtn,viewbtn,viewallbtn,showbtn;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name= (EditText) findViewById(R.id.name);
        roll_no= (EditText) findViewById(R.id.roll_no);
        marks= (EditText) findViewById(R.id.marks);

        addbtn= (Button) findViewById(R.id.addbtn);
        deletebtn= (Button) findViewById(R.id.deletebtn);
        modifybtn= (Button) findViewById(R.id.modifybtn);
        viewbtn= (Button) findViewById(R.id.viewbtn);
        viewallbtn= (Button) findViewById(R.id.viewallbtn);
        showbtn= (Button) findViewById(R.id.showbtn);

        addbtn.setOnClickListener(this);
        deletebtn.setOnClickListener(this);
        modifybtn.setOnClickListener(this);
        viewbtn.setOnClickListener(this);
        viewallbtn.setOnClickListener(this);
        showbtn.setOnClickListener(this);

        db=openOrCreateDatabase("studentsdb", Context.MODE_PRIVATE,null);

         db.execSQL("CREATE TABLE IF NOT EXISTS students(name VARCHAR,rollno INTEGER,marks INTEGER);");



    }

    @Override
    public void onClick(View v) {

        if (v == addbtn) {

            if (name.getText().toString().trim().length() == 0 ||
                    roll_no.getText().toString().trim().length() == 0 ||
                    marks.getText().toString().trim().length() == 0) {

                showMessage("Error", "please provide all the record");
                return;
            }
            db.execSQL("INSERT INTO STUDENTS VALUES('" + name.getText() + "','" + roll_no.getText() + "','" + marks.getText() + "');");
            showMessage("Success", "record entered successfully");
            ClearText();
        }

        if (v == deletebtn) {
            if (roll_no.getText().toString().trim().length() == 0) {
                showMessage("Error", "invalid roll no");
                return;
            }

            Cursor c = db.rawQuery("SELECT * FROM students WHERE rollno='"+roll_no.getText() + "'",null);
            if (c.moveToFirst()) {
                db.execSQL("DELETE FROM students WHERE rollno='"+roll_no.getText() + "'");
                showMessage("Success", "Record Deleted");
            } else {
                showMessage("Error", "Invalid Roll No");
            }
            ClearText();
        }
        if (v == modifybtn) {
            if (roll_no.getText().toString().trim().length() == 0) {
                showMessage("Error", "invalid roll no");
                return;
            }

            Cursor c = db.rawQuery("SELECT * FROM students WHERE rollno='" + roll_no.getText() + "'", null);
            if (c.moveToFirst()) {
                db.execSQL("UPDATE students SET name='" + name.getText() + "',marks='" + marks.getText() + "'WHERE rollno='" + roll_no.getText() + "'");
                showMessage("Success", "Record Modified");
            } else {
                showMessage("Error", "Invalid Roll No");
            }
            ClearText();
        }
        if (v == viewbtn) {
            if (roll_no.getText().toString().trim().length() == 0) {
                showMessage("Error", "please provide roll no");
                return;
            }

            Cursor c = db.rawQuery("SELECT * FROM students WHERE rollno='" + roll_no.getText() + "'", null);
            if (c.moveToFirst()) {
                name.setText(c.getString(0));
                marks.setText(c.getString(2));

            } else {
                showMessage("Error", "Invalid Roll No");
                ClearText();
            }

        }
        if (v == viewallbtn) {
            Cursor c = db.rawQuery("SELECT * FROM students", null);
            if (c.getCount() == 0) {
                showMessage("Error", "no record found");
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("Roll no: " + c.getString(1) + "\n");
                buffer.append("Name: " + c.getString(0) + "\n");
                buffer.append("Marks: " + c.getString(2) + "\n");
            }
            showMessage("Students Detail: ", buffer.toString());
        }
        if (v == showbtn) {
            showMessage("Student Management System", "powered by asim");
        }
    }

    private void ClearText() {
        name.setText("");
        roll_no.setText("");
        marks.setText("");
        roll_no.requestFocus();


    }


    private void showMessage(String title, String message) {

       AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

       
    }

}