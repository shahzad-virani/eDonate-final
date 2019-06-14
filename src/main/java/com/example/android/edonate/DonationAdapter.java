package com.example.android.edonate;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DonationAdapter extends ArrayAdapter<Donation> {
    public DonationAdapter(Context context, ArrayList<Donation> donations) {
        super(context, 0, donations);
    }

    ArrayList<Donation> dono = new ArrayList<>();
    @Override
    public int getCount() {
        return dono.size();
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        Donation donation = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_text_view);
        TextView details = (TextView) convertView.findViewById(R.id.details_text_view);
        TextView quantity = (TextView) convertView.findViewById(R.id.quantity_text_view);
        TextView type = (TextView) convertView.findViewById(R.id.type_text_view);

        name.setText(donation.getName());
        details.setText(donation.getDetails());
        quantity.setText(donation.getQuantity());
        type.setText(donation.getType());



        return convertView;
    }

    void setDonations(ArrayList<Donation> dono){
        this.dono = dono;
        notifyDataSetChanged();
    }
}
