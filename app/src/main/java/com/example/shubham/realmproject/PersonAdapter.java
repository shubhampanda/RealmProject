package com.example.shubham.realmproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Shubham on 2/11/2016.
 */
public class PersonAdapter extends RealmBaseAdapter<Person> implements ListAdapter {

    private Realm realm;
    private  static  class  PersonViewHolder
    {
        private TextView name;
        private TextView age;
        private Button delete;
    }

    public PersonAdapter(Context context,int resId,RealmResults<Person> realmResults,boolean automaticUpdate)
    {
        super(context,realmResults,automaticUpdate);
        realm = Realm.getInstance(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PersonViewHolder holder;
        if(view!= null)
        {
            holder = (PersonViewHolder) view.getTag();

        }else{
            view  = inflater.inflate(R.layout.item_view,viewGroup,false);
            holder = new PersonViewHolder();
            holder.name = (TextView) view.findViewById(R.id.item_Name);
            holder.age = (TextView) view.findViewById(R.id.item_Age);
            holder.delete = (Button) view.findViewById(R.id.delete);
            view.setTag(holder);
        }
        final Person item = realmResults.get(i);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                item.removeFromRealm();
                realm.commitTransaction();
            }
        });
        holder.name.setText(item.getName());
        holder.age.setText(item.getAge());
        return view;
    }

}

