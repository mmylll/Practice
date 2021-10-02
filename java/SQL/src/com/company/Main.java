package com.company;

import java.util.*;

public class Main {



    public static void main(String[] args) {



	    SQL fName = new SQL();
	    SQL lName = new SQL();
	    SQL country = new SQL();

	    while(true){
	        System.out.println("请插入数据:");
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            String fname = scanner.nextLine();
            java.util.Scanner scanner1 = new java.util.Scanner(System.in);
            String lname = scanner1.nextLine();
            java.util.Scanner scanner2 = new java.util.Scanner(System.in);
            String country1 = scanner.nextLine();
            System.out.println("---------------------------------------------");
            System.out.println("firstname:");
            insert(fName,fname);
            System.out.println("lastname:");
            insert(lName,lname);
            System.out.println("country:");
            insert(country,country1);
            System.out.println("---------------------------------------------");
            System.out.println();
        }
    }


    public static void insert(SQL sql,String str){
        ArrayList temp = new ArrayList();
        if(!sql.hasString(str)){
            for(int i = 0; i < sql.getListInt().size();i++) {
                temp.add(sql.getListString().get(sql.getListInt().get(i)));
            }
            sql.addString(str);
            sql.setListString(sql.sortAscending());
            sql.getListInt().clear();
            for(int i = 0; i < temp.size();i++) {
               sql.getListInt().add(sql.getListString().indexOf(temp.get(i)));
            }
            sql.getListInt().add(sql.getListString().indexOf(str));
        }else {
            sql.getListInt().add(sql.getPlace(str));
            sql.getList().add(str);
        }
        System.out.println("原数据："+sql.getList());
        System.out.println("排序字典："+sql.getListString());
        System.out.println("属性向量"+sql.getListInt());
    }
}
