package com.example.shubham.realmproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

        private final static String TAG = MainActivity.class.getSimpleName();
        private Realm realm;
        private ListView listView;
        private PersonAdapter mPersonAdapter;
        private Button mCreateButton;
        private EditText mNameText;
        private EditText mAgeText;
        private EditText mSearchText;
        private RealmResults<Person> persons;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            realm = Realm.getInstance(this);
            persons = realm.where(Person.class).findAll();
            setupAdapter(persons);
            listView = (ListView) findViewById(R.id.listView);
            mAgeText = (EditText) findViewById(R.id.editText2);
            mNameText = (EditText) findViewById(R.id.editText);
            mSearchText = (EditText) findViewById(R.id.search);
            mCreateButton = (Button) findViewById(R.id.create);
            mCreateButton.setOnClickListener(this);
            mSearchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    RealmResults<Person> persons = realm.where(Person.class).contains("name", charSequence.toString()).findAll();
                    Log.d(TAG,charSequence.toString());
                    setupAdapter(persons);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            }

        private void setupAdapter(RealmResults<Person> persons) {
            mPersonAdapter = new PersonAdapter(this, R.layout.activity_main, persons, true);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(mPersonAdapter);
            listView.setOnItemClickListener(this);
        }


        @Override
        protected void onResume() {
            super.onResume();

        }


        @Override
        protected void onPause() {
            super.onPause();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.create:
                    createEntry();
                    break;
            }
        }

        private void createEntry() {
            Person person = new Person();
            person.setName(mNameText.getText().toString());
            person.setAge(mAgeText.getText().toString());
            realm.beginTransaction();
            realm.copyToRealm(person);
            realm.commitTransaction();
        }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_view);
        Button update = (Button) dialog.findViewById(R.id.button);
        final EditText nameText = (EditText) dialog.findViewById(R.id.update_name);
        final EditText ageText = (EditText) dialog.findViewById(R.id.update_age);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                Person person = persons.get(i);
                person.setName(nameText.getText().toString());
                person.setAge(ageText.getText().toString());
                realm.commitTransaction();
                dialog.dismiss();
            }
        });
     dialog.show();
    }
}


