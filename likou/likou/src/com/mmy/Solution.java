package com.mmy;

import java.util.*;

class Solution {
    public List<String> subdomainVisits(String[] cpdomains) {
        List<String> stringList = new ArrayList<>();
        Map<String,Integer> map = new HashMap();
        for(int i = 0;i < cpdomains.length;i++){
            String[] strs = cpdomains[i].split("\\.");
            String[] strs1 = strs[0].split(" ");
            int num = Integer.parseInt(strs1[0]);
            for(int j = 0;j < strs.length;j++){
                if(map.containsKey(strs[j])){
                    map.put(strs[j],map.get(strs[j])+num);
                }else{
                    map.put(strs[j],num);
                }
            }
            if(map.containsKey(strs1[1])){
                map.put(strs1[1],map.get(strs1[1])+num);
            }else{
                map.put(strs1[1],num);
            }
            System.out.println(Arrays.toString(strs));
            System.out.println(strs[0]);
            System.out.println(map);
        }
        return stringList;
    }
}
