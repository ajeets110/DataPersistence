package com.example.databasedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter {

    List<Employee> employees;
    Context context;
    int layoutRes;
//    SQLiteDatabase mDatabase;
    DatabaseHelper mDatabase;


    public EmployeeAdapter(@NonNull Context context, int layoutRes,List<Employee> employees ,DatabaseHelper mDatabase) {
        super(context, layoutRes, employees);
        this.employees = employees;
        this.context = context;
        this.layoutRes = layoutRes;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(layoutRes,null);
        TextView tvName=v.findViewById(R.id.tvName);
        TextView tvSalary=v.findViewById(R.id.tvSalary);
        TextView tvdepartment=v.findViewById(R.id.tvDepartment);
        TextView tvJoininig=v.findViewById(R.id.tvJoiningDtae);

        final Employee employee=employees.get(position);
        tvName.setText(employee.getName());
        tvJoininig.setText(employee.getJoiningDate());
        tvdepartment.setText(employee.getDept());
        tvSalary.setText(String.valueOf(employee.getSalary()));




        v.findViewById(R.id.btn_edi_employee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateemployee(employee);

            }
        });

        v.findViewById(R.id.btn_delete_employee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee(employee);

            }
        });
return v;

    }

    private void deleteEmployee(final Employee employee) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Are You Sure");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                String sql="DELETE FROM employees WHERE id=?";
                mDatabase.execSQL(sql,new Integer [] {employee.getId()});
                loadEmployees();

                 */
                mDatabase.deleteRow(employee.getId());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }



    private void updateemployee(final Employee employee) {
        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.dialog_layout_update_employee,null);
        alert.setView(v);
        final AlertDialog alertDialog=alert.create();
        alertDialog.show();

        final EditText eName = v.findViewById(R.id.editTextName);
        final EditText editSalary = v.findViewById(R.id.editTextSalary);
        final Spinner spinnerDept = v.findViewById(R.id.spinnerDepartment);

        String[] departmentsArray = context.getResources().getStringArray(R.array.departments);
        int position = Arrays.asList(departmentsArray).indexOf(employee.getDept());


        eName.setText(employee.getName());
        editSalary.setText(String.valueOf(employee.getSalary()));
        v.findViewById(R.id.btn_update_employee).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String name = eName.getText().toString().trim();
                String salary = editSalary.getText().toString().trim();
                String dept = spinnerDept.getSelectedItem().toString();


                if (name.isEmpty()) {
                    eName.setError("name field is mandatory");
                    eName.requestFocus();
                    return;
                }

                if (salary.isEmpty()) {
                    editSalary.setError("salary field cannot be empty");
                    editSalary.requestFocus();
                    return;
                }
                /*
                String sql="UPDATE employees SET name=? , department = ?, salary=? WHERE id=?";
                mDatabase.execSQL(sql,new String [] {name,dept,salary,String.valueOf(employee.getId())});
                Toast.makeText(context, "employee updated", Toast.LENGTH_SHORT).show();
                loadEmployees();
                if (mDatabase.up)

                 */
                mDatabase.updateEmployee(employee.id, name, dept, Double.parseDouble(salary));

                alertDialog.dismiss();
            }
        });}

        private void loadEmployees() {

//        String sql="SELECT * FROM employees";
        Cursor cursor=mDatabase.getAllEMployee();

            if (cursor.moveToFirst()) {
                employees.clear();
            }
                do {
                    employees.add(new Employee(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getDouble(4)
                    ));
                } while (cursor.moveToNext());
                cursor.close();
                notifyDataSetChanged();
        }

}
