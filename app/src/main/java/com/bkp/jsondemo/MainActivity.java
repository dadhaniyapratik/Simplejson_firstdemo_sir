package com.bkp.jsondemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<contacts> contactList;
    static CustomAdapter adapter;

    ListView listView;


    ProgressDialog pDialog;

    private static String url = "http://api.androidhive.info/contacts/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = new ArrayList<contacts>();

        listView = (ListView) findViewById(R.id.listview);


        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    JSONArray contacts = jsonObject.getJSONArray("contacts");

                    for (int i = 0; i < contacts.length(); i++) {

                        JSONObject c = contacts.getJSONObject(i);

                        contacts contacts1 = new contacts();

                        String id = c.getString("id");
                        contacts1.setId(id);


                        String name = c.getString("name");
                        contacts1.setName(name);

                        String email = c.getString("email");
                        contacts1.setEmail(email);

                        String address = c.getString("address");
                        contacts1.setAddress(address);

                        String gender = c.getString("gender");
                        contacts1.setGender(gender);


                        JSONObject phone = c.getJSONObject("phone");

                        Phone phone1 = new Phone();

                        String mobile = phone.getString("mobile");
                        phone1.setMobile(mobile);

                        String home = phone.getString("home");
                        phone1.setHome(home);

                        String office = phone.getString("office");
                        phone1.setOffice(office);

                        ArrayList<Phone> mPhoneList = new ArrayList<>();
                        mPhoneList.add(phone1);


                        contacts1.setmPhonelist(mPhoneList);
                        contactList.add(contacts1);


                    }


                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
//                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            adapter = new CustomAdapter(MainActivity.this, contactList);
            listView.setAdapter(adapter);


        }
    }

}
