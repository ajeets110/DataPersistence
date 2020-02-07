package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // in order to use database you should give a name to  the database
    public static final String DTABASE_NAME="myDatabase";
    SQLiteDatabase mDatabase;
    EditText editTextName,editTextSalary;
    Spinner spinnerDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextSalary=findViewById(R.id.editTextSalary);
        editTextName=findViewById(R.id.editTextName);
        spinnerDept=findViewById(R.id.spinnerDepartment);
        findViewById(R.id.buttonAddEmployee).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.tvViewEmployee).setOnClickListener((View.OnClickListener) this);

        mDatabase=openOrCreateDatabase(DTABASE_NAME,MODE_PRIVATE,null);
        createTable();




    }

    private void createTable() {
        String sql="CREATE TABLE IF NOT EXISTS emloyees(" + "id INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT,"+"name VARCHAR(200) NOT NULL,"+
                "department VARCHAR(200)NOT NULL,"+
                "joiningDate VARCHAR(200)NOT NULL,"+
                "salary DOUBLE NOT NULL);";
        mDatabase.execSQL(sql);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAddEmployee:
                addEmployee();
                break;
            case R.id.tvViewEmployee:
                //start acitivity to see the list of empoyees
                break;
        }
    }

    private void addEmployee() {
        String name=editTextName.getText().toString().trim();
        String salary=editTextSalary.getText().toString().trim();
        String dept=spinnerDept.getSelectedItem().toString();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate=sdf.format(calendar.getTime());


        if(name.isEmpty()){
            editTextName.setError("name is mandatory");
            editTextName.requestFocus();
            return;
        }

        if(salary.isEmpty()){
            editTextSalary.setError("salary is mandatory");
            editTextSalary.requestFocus();
            return;
        }

        String sql="INSERT INTO employees(name,department,joiningDate,salary)" + "VALUES(?,?,?,?)";
        mDatabase.execSQL(sql,new String[] {name,dept,joiningDate,salary});
        Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();

    }


}
