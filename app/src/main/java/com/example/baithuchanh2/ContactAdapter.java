package com.example.baithuchanh2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private List<Contact> contacts;
    private Context context;
    public ContactAdapter(@NonNull Context context, @NonNull List<Contact> contacts) {
        super(context, 0, contacts);
        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }
        Contact contact = contacts.get(position);
        TextView tvName = convertView.findViewById(R.id.txtHoten);
        TextView tvSDT = convertView.findViewById(R.id.txtSDT);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        ImageView imageView = convertView.findViewById(R.id.imgView);

        tvName.setText(contact.getName());
        tvSDT.setText(contact.getPhoneNumber());
        checkBox.setChecked(contact.status);

        //hiển thị đường dẫn hình ảnh
        if(contact.getImgPath() != null){
            imageView.setImageURI(Uri.parse(contact.getImgPath())); //hiển thị hình ảnh
        }else{
            imageView.setVisibility(View.GONE);
        }

        checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> contact.setStatus(isChecked)));
        return convertView;
    }
}
