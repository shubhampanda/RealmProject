package com.example.shubham.realmproject;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Shubham on 2/11/2016.
 */
public class Person  extends RealmObject{
@Required
private String name ;
    private String age;


    public void setAge(String age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
