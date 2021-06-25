package com.example.nguyenhuuthao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Add extends AppCompatActivity implements View.OnClickListener {
    public static int RESULT_CODE = 1;
    private EditText contact_id, contact_name, contact_phonenumber;
    private TextView credits;
    private Button btnAdd, btnCancel;
    private NguyenHuuThao_SQLite db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        setWidget();
        credits.setText("Nguyễn Hữu Thảo");
        db = new NguyenHuuThao_SQLite(this);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void setWidget()
    {
        contact_id          = findViewById(R.id.add_ID);
        contact_name        = findViewById(R.id.add_name);
        contact_phonenumber = findViewById(R.id.add_phonenumber);
        credits             = findViewById(R.id.credits);
        btnAdd              = findViewById(R.id.btnAdd);
        btnCancel           = findViewById(R.id.btnCancel);
    }
    //TODO Xử lý sự kiện
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //Xử lý sự kiện nút ADD
            case R.id.btnAdd:{
                String id           = contact_id.getText().toString();
                String name         = contact_name.getText().toString();
                String phonenumber  = contact_phonenumber.getText().toString();

                Contact_NguyenHuuThao user =  validate(id,name,phonenumber);
                if (user != null) {
                    Toast.makeText(this, "Thêm Dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    db.insert(user);
                    finish();
                }
                break;
            }
            // Xử lý sự kiện nút Cancel
            case R.id.btnCancel:{
                finish();
            }
        }
    }
    // TODO Hàm Kiểm Tra
    private Contact_NguyenHuuThao validate(String id,String name,String phonenumber)
    {
        if (id.matches("[a-zA-Z]"))
        {
            Toast.makeText(this, "Mã không hợp lệ", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (name.isEmpty())
        {
            Toast.makeText(this, "Tên không hợp lệ", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (phonenumber.isEmpty() )
        {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return null;
        }
        int ID = Integer.parseInt(id);
        return new Contact_NguyenHuuThao(ID,name,phonenumber);
    }
}