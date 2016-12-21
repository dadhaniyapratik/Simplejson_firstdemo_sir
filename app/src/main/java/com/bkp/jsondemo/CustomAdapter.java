package com.bkp.jsondemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by agile on 12-Oct-16.
 */

public class CustomAdapter extends BaseAdapter {
    /***********
     * Declare Used Variables
     *********/
    private Activity activity;

    private ArrayList<contacts> data;

    private static LayoutInflater inflater = null;


    /*************
     * CustomAdapter Constructor
     *****************/
    public CustomAdapter(Activity a, ArrayList<contacts> d) {

        /********** Take passed values **********/
        activity = a;
        data = d;


        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {

        public TextView txtId;
        public TextView txtName;
        public TextView txtEmail;
        public TextView txtAddress;
        public TextView txtGender;
        public TextView txtMobile;
        public TextView txtHome;
        public TextView txtOffice;


    }


    /********
     * What is the size of Passed Arraylist Size
     ************/
    public int getCount() {

        return data.size();
    }

    public contacts getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /******
     * Depends upon data size called for each row , Create each ListView row
     *****/
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.list_raw, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.txtId = (TextView) vi.findViewById(R.id.txtID);
            holder.txtName = (TextView) vi.findViewById(R.id.txtName);
            holder.txtEmail = (TextView) vi.findViewById(R.id.txtEmail);
            holder.txtAddress = (TextView) vi.findViewById(R.id.txtAddress);
            holder.txtGender = (TextView) vi.findViewById(R.id.txtGender);
            holder.txtMobile = (TextView) vi.findViewById(R.id.txtMobile);
            holder.txtHome = (TextView) vi.findViewById(R.id.txtHome);
            holder.txtOffice = (TextView) vi.findViewById(R.id.txtOffice);


            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();


            /************  Set Model values in Holder elements ***********/

            holder.txtId.setText(data.get(position).getId());
            holder.txtName.setText(data.get(position).getName());
            holder.txtEmail.setText(data.get(position).getEmail());
            holder.txtAddress.setText(data.get(position).getAddress());
            holder.txtGender.setText(data.get(position).getGender());


            for (int i = 0; i < data.get(position).getmPhonelist().size(); i++) {
                holder.txtMobile.setText(data.get(position).getmPhonelist().get(i).getMobile());
                holder.txtHome.setText(data.get(position).getmPhonelist().get(i).getHome());
                holder.txtOffice.setText(data.get(position).getmPhonelist().get(i).getOffice());

            }

        }
        return vi;

    }
}

