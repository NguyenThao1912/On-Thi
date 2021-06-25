package com.example.onthi_luyentap_2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int REQUEST_CODE = 1;
    private EditText searchView;
    private ListView listView;
    private NguyenHuuLuan_Adapter adapter;
    private List<Contact_NguyenHuuLuan> data;
    private FloatingActionButton btn;
    private NguyenHuuLuan_SQLite db;        // Lớp truy xuất SQL


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ánh xạ view
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.listview);
        btn = findViewById(R.id.btnadd);





        // sự kiện nhập vào thanh tìm kiếm
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });

        //Thêm context Menu vào List View
        registerForContextMenu(listView);

        //Khai báo lớp sử dụng SQLITE
        db = NguyenHuuLuan_SQLite.getInstance(this);

        setdata();
        //Đọc dữ liệu từ SQL
        GetData();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listview) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Contact_NguyenHuuLuan obj = (Contact_NguyenHuuLuan) lv.getItemAtPosition(acmi.position);

            //Thêm Menu Title
            menu.add("NguyễnHữuLuân_Edit");
            menu.add("NguyễnHữuLuân_Delete");
        }
    }

    private void GetData() {
        data = db.getALL();
        adapter = null;
        Collections.sort(data);
        //Nếu muốn xếp tên theo giảm dần dãy thì:
        /*Collections.reverse(data);*/
        adapter = new NguyenHuuLuan_Adapter(data,this);
        listView.setAdapter(adapter);
    }

    //Khởi tạo dữ liệu cho SQL LITE
    private void setdata() {
        Contact_NguyenHuuLuan object1 = new Contact_NguyenHuuLuan(1,"Luân Nguyễn Hữu","0123456778");
        Contact_NguyenHuuLuan object2 = new Contact_NguyenHuuLuan(2,"Hữu Luân","01452456778");
        Contact_NguyenHuuLuan object3 = new Contact_NguyenHuuLuan(3,"Luân Nguyễn","012342346778");
        Contact_NguyenHuuLuan object4 = new Contact_NguyenHuuLuan(4,"Nguyễn Luân","012345378");
        Contact_NguyenHuuLuan object5 = new Contact_NguyenHuuLuan(5,"Nguyễn Hữu Luân","0123356778");
        Contact_NguyenHuuLuan object6 = new Contact_NguyenHuuLuan(6,"Khôi Ngô","0123456778");

        db.insert(object1);
        db.insert(object2);
        db.insert(object3);
        db.insert(object4);
        db.insert(object5);
        db.insert(object6);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Lấy vị trí item
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        //Nếu Title là Xóa
        if(item.getTitle().equals("NguyễnHữuLuân_Delete"))
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Nguyễn Hữu Luân want to delete ?");
            alertDialogBuilder.setMessage("Confirm to delete");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Xóa Khỏi CSDL
                            db.delete(String.valueOf(data.get(position).getId()));
                            GetData();
                            adapter.filterlist = data;

                        }
                    });
            alertDialogBuilder.setNegativeButton("CANCEL",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        //Ngược lại nếu là Sửa
        else {
            Intent intent = new Intent(this, Edit.class);
            intent.putExtra("object",data.get(position));
            activityLaucher.launch(intent);
        }
        return super.onContextItemSelected(item);
    }
    // CALL BACK Start activity for result
    ActivityResultLauncher<Intent> activityLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    GetData();
                }
            });

    public void openSomeActivityForResult() {
        Intent intent = new Intent(this, Edit.class);
        activityLaucher.launch(intent);
    }
}