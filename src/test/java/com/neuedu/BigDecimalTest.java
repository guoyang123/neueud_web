package com.neuedu;

import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalTest {

    @Test
    public  void  test(){

        System.out.println(0.05+0.01);//float double -->工程计算、科学计算
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
    }

    @Test
    public  void  test2(){
        BigDecimal b1=new BigDecimal("123.5");
        BigDecimal b2=new BigDecimal("100");


      // System.out.println(b1.add(b2));
        System.out.println(b1.divide(b2,2,BigDecimal.ROUND_HALF_UP));
       /* System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);*/
    }

}
