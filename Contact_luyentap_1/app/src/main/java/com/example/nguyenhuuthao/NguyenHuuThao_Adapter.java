package com.example.nguyenhuuthao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NguyenHuuThao_Adapter extends BaseAdapter implements Filterable {
    private List<Contact_NguyenHuuThao> data;
    public  List<Contact_NguyenHuuThao> filterlist;
    private Context context;
    private LayoutInflater inflater;

    public NguyenHuuThao_Adapter(List<Contact_NguyenHuuThao> data, Context context) {
        this.data       = data;
        this.filterlist = data;
        this.context    = context;
        inflater        = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Ánh xạ view
        View v = convertView;

        v = inflater.inflate(R.layout.listview_row,null,false); // lấy ra view của 1 hàng
        TextView txt_ID          = v.findViewById(R.id.contact_id);
        TextView txt_Name        = v.findViewById(R.id.contact_name);
        TextView txt_PhoneNumber = v.findViewById(R.id.contact_phonenumber);

        // Gán dữ liệu
        txt_ID.setText(String.valueOf(data.get(position).getId()));
        txt_Name.setText(data.get(position).getName());
        txt_PhoneNumber.setText(data.get(position).getPhonenumber());

        return v;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            // thực hiện filter
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                List<Contact_NguyenHuuThao> filter = new ArrayList<>();
                if (constraint.equals(null) || constraint.equals("")) {
                    results.values = filterlist;
                    return results;
                }
                else
                {
                    for (Contact_NguyenHuuThao r:data) {
                        if (r.getName().toLowerCase().contains(constraint)) {
                            filter.add(r);
                        }
                    }
                }
                results.values = filter;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<Contact_NguyenHuuThao> Contact_NguyenHuuThaos = (List<Contact_NguyenHuuThao>) results.values;
                data = Contact_NguyenHuuThaos;
                notifyDataSetChanged();
            }
        };
    }
}
