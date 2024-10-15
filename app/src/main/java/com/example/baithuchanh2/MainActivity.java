package com.example.baithuchanh2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtHoten, edtSDT;
    Button btnThem, btnXoa, btnSelect;
    ListView lvContact;
    ImageView imageViewProfile;
    List<Contact> contactList;
    ContactAdapter adapter;
    Uri selectedImgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitWidget();

        contactList = new ArrayList<>();
        adapter = new ContactAdapter(this, contactList);
        lvContact.setAdapter(adapter);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtHoten.getText().toString().trim();
                String phoneNumber = edtSDT.getText().toString().trim();
                if(validateInput(name, phoneNumber)){
                    int id = contactList.size() + 1; //tạo id tự động
                    Contact contact = new Contact(id, name, phoneNumber, false, selectedImgUri != null ? selectedImgUri.toString() : null);
                    contactList.add(contact);
                    adapter.notifyDataSetChanged();
                    clearInputs();
                }
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImg();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            selectedImgUri = data.getData();
            imageViewProfile.setImageURI(selectedImgUri);
            imageViewProfile.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateInput(String name, String phoneNumber){
        if(name.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(phoneNumber.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearInputs(){
        edtHoten.setText("");
        edtSDT.setText("c");
        imageViewProfile.setVisibility(View.GONE);
        selectedImgUri = null;
    }
    private void confirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa liên hệ")
                .setMessage("Bạn có chắc chắn muốn xóa liên hệ đã chọn không?")
                .setPositiveButton("Có", ((dialog, which) -> deleteSelected()))
                .setNegativeButton("Không", null)
                .show();
    }

    private void deleteSelected(){
        for(int i = contactList.size() - 1; i>=0; i--){
            Contact contact = contactList.get(i);
            if(contact.getStatus()){
                contactList.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void SelectImg(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    private void InitWidget() {
        edtHoten = findViewById(R.id.edtHoten);
        edtSDT = findViewById(R.id.edtSDT);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        lvContact = findViewById(R.id.lview);
        btnSelect = findViewById(R.id.btnSelect);
        imageViewProfile = findViewById(R.id.imgProfile);
    }
}