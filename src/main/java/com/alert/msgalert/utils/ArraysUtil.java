package com.alert.msgalert.utils;

import java.util.ArrayList;
import java.util.List;

public class ArraysUtil {
    
    
    public static void main(String[] args) {  
        int[] aa = { 0, 6, 4};  
        int[] bb = { 4, 5,};  
        int[] cc = { 4 };  
        int[] dd = getListString(aa, bb, cc);  
//        List<int[]> ee = new ArrayList<int[]>();  
//        ee = getListIntArray(dd, 8);  
//        int[] gg = (int[]) ee.get(0);  
//        for (int aaaa : gg) {  
//            System.out.println("eee个数为=" + aaaa);  
//        }  
        for (int i : dd) {
			System.out.print(i + " ");
		}
    }  
  
    /** 
     * @param dd  要拆分的数组 
     * @param b   每一个拆分的数组的大小 
     * @return 
     */  
    private static List<int[]> getListIntArray(int[] dd, int b) {  
        List<int[]> aa = new ArrayList<int[]>();  
        // 取整代表可以拆分的数组个数  
        int f = dd.length / b;  
        for (int i = 0; i < f; i++) {  
            int[] bbb = new int[b];  
            for (int j = 0; j < b; j++) {  
                bbb[j] = dd[j + i * b];  
            }  
            aa.add(bbb);  
        }  
        return aa;  
    }  
  
    //  多个数组合并  
    private static int[] getListString(int[] aa, int[] bb, int[] cc) {  
        List collect = new ArrayList();  
        collect.add(aa);  
        collect.add(bb);  
        collect.add(cc);  
        int[] aa0 = null;  
        //  每次都是两个数组合并 所以合并的次数为 collect.size() ，第一个是虚拟的数组  
        for (int i = 0; i < collect.size(); i++) {  
            int[] aa1 = (int[]) collect.get(i);  
            int[] newInt = onArrayTogater(aa0, aa1);  
            aa0 = newInt;  
        }  
        return aa0;  
    }  
  
    private static int[] onArrayTogater(int[] aa, int[] bb) {  
        if (aa == null) {  
            return bb;  
        }  
        int[] collectionInt = new int[aa.length + bb.length];  
        for (int i = 0; i < aa.length; i++) {  
            collectionInt[i] = aa[i];  
        }  
        for (int i = aa.length; i < aa.length + bb.length; i++) {  
            collectionInt[i] = aa[i - aa.length];  
        }  
        return collectionInt;  
  
    }  
  
}
