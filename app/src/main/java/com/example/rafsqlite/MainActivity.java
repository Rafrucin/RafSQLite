package com.example.rafsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    Switch sw_activeCustomer;
    ListView lv_customerList;
    ArrayAdapter customerArrayAdapter;
    DbBaseHelper dbBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_age = (EditText) findViewById(R.id.et_age);
        et_name = (EditText) findViewById(R.id.et_customer_name);
        sw_activeCustomer = findViewById(R.id.sw_active);
        lv_customerList = findViewById(R.id.lv_customerList);

        dbBaseHelper = new DbBaseHelper(MainActivity.this);

        ShowCustomersOnListView(dbBaseHelper);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomerModel customerModel;
                try {
                    customerModel = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt( et_age.getText().toString()), sw_activeCustomer.isChecked());

                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Try Catch", Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1, "error", -1, false);
                }

                DbBaseHelper dbBaseHelper = new DbBaseHelper(MainActivity.this);

                boolean success = dbBaseHelper.addOne(customerModel);

                Toast.makeText(MainActivity.this, "Success: " + success, Toast.LENGTH_SHORT).show();

                ShowCustomersOnListView(dbBaseHelper);

                et_age.setText("");
                et_name.setText("");

            }
        });

        btn_viewAll.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowCustomersOnListView(dbBaseHelper);

                //Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();
            }
        }));

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomerModel clickedCustomer = (CustomerModel) adapterView.getItemAtPosition(i);
                dbBaseHelper.deleteOne(clickedCustomer);
                ShowCustomersOnListView(dbBaseHelper);
                Toast.makeText(MainActivity.this, "deleted " + clickedCustomer , Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void ShowCustomersOnListView(DbBaseHelper dbhelper) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                dbhelper.getCustomers());
        lv_customerList.setAdapter(customerArrayAdapter);
    }
}