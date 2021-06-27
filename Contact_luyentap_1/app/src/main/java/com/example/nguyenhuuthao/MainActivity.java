package com.example.nguyenhuuthao;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Khai báo biến
    public static int REQUEST_CODE = 1;
    private EditText searchView;
    private ListView listView;
    private NguyenHuuThao_Adapter adapter;
    private List<Contact_NguyenHuuThao> data;
    private FloatingActionButton btn;
    private NguyenHuuThao_SQLite db;            //  khai báo Lớp truy xuất SQL
 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ánh xạ view
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.listview);
        btn = findViewById(R.id.btnadd);

        //TODO Câu 4
        btn.setOnClickListener(v -> openSomeActivityForResult());


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
        db = NguyenHuuThao_SQLite.getInstance(this);

        //Đọc dữ liệu từ SQL
        GetData();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listview) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Contact_NguyenHuuThao obj = (Contact_NguyenHuuThao) lv.getItemAtPosition(acmi.position);

            //Thêm Menu Title
            menu.add("Sửa");
            menu.add("Xóa");
        }
    }
    // TODO Câu 3
    private void GetData() {
        data = db.getALL();
        adapter = null;
        // 3.3 Sắp xếp danh sách
        Collections.sort(data);
        // Nếu sắp xếp tăng dần tên
        // Collections.reverse(data);
        adapter = new NguyenHuuThao_Adapter(data,this);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Lấy vị trí item
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        //Nếu Title là Xóa
        if(item.getTitle().equals("Xóa"))
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Are you sure to delete this item ?");
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

        }
        return super.onContextItemSelected(item);
    }

    // TODO Câu 4
    // 4.1 hàm Call back giống Hàm OnActivityResult
    ActivityResultLauncher<Intent> activityLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Do chỉ có 1 màn hình nên có thể k cần thiết kiểm tra if requestcode , if result code
                    GetData();
                }
            });
    // 4.2 Mở màn hình Mới
    public void openSomeActivityForResult() {
        // Khởi tạo intent
        Intent intent = new Intent(this, Add.class);
        // Mở màn hình Mới
        activityLaucher.launch(intent);
    }
}