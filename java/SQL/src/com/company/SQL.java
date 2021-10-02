package com.company;
import java.util.*;

public class SQL {

    private ArrayList<String> listString = new ArrayList();
    private ArrayList<Integer> listInt = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    public SQL(){

    }

    public SQL(ArrayList<String> listString,ArrayList<Integer> listInt,ArrayList<String> list){
        this.listString = listString;
        this.listInt = listInt;
        this.list = list;
    }

    public ArrayList sortAscending() {

        Collections.sort(this.listString);

        return this.listString;
    }

    public void addString(String str){
        listString.add(str);
        list.add(str);
    }
    public void addInt(int i){
        listInt.add(i);
    }

    public ArrayList<Integer> getListInt() {
        return listInt;
    }

    public ArrayList getListString() {
        return listString;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public boolean hasString(String str){
        return listString.contains(str);
    }
    public int getPlace(String str){
        if(hasString(str)){
            return listString.indexOf(str);
        }else {
            return -1;
        }
    }

    public void setListInt(ArrayList<Integer> listInt) {
        this.listInt = listInt;
    }



    public void setListString(ArrayList listString) {
        this.listString = listString;
    }
}
