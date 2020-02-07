package com.example.databasedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter {

    List<Employee> employees;
    Context context;
    int layoutRes;
    SQLiteDatabase mDatabase;


    public EmployeeAdapter(@NonNull Context context, int resource, int textViewResourceId, List<Employee> employees, Context context1, int layoutRes, SQLiteDatabase mDatabase) {
        super(context, resource, textViewResourceId);
        this.employees = employees;
        this.context = context1;
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
                String sql="DELETE FROM employees WHERE id=?";
                mDatabase.execSQL(sql,new Integer [] {employee.getId()});
                loadEmployees();
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

    private void loadEmployees() {
    }

    private void updateemployee(Employee employee) {
    }
}
