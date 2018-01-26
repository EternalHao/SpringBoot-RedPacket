package com.shangfu.springbootfilghting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class ProbabilityController {
    @RequestMapping("/pro")
    public String redPacketPro(){
        ModelAndView model = new ModelAndView();
        Random rand = new Random();
        System.out.println(rand.nextInt(100));
        List<Integer> list_data = new ArrayList<>();
        List<Integer> list_people = new ArrayList<>();
        for(int i=0 ; i<100 ; i++){
            list_data.add(i);
        }
        for(int i=0 ; i<5 ; i++){
            list_people.add(list_data.get(rand.nextInt(100)));
        }
        model.addObject("list_1", list_people);
        return "finish";
    }
    public static void main(String[] args) {
        Random rand = new Random();//定义随机数产生器

        List<Integer> list_One = new ArrayList<Integer>();//定义一等奖数字集合
        List<Integer> list = null;

        //用list时

  /*list = new ArrayList<Integer>();//定义100个随机数 集合

  for(int i = 0; i < 100;){//产生100个3位数 的随机不重复数字
   if(check(rand.nextInt(899) + 100,list)){
    i++;
   }
  }*/

        //用set时
        Set<Integer> set = new HashSet<Integer>();
        while(set.size() < 100){
            set.add(rand.nextInt(899) + 100);
        }
        list = new ArrayList<Integer>(set);

        for(int k = 0; k < 5;){//产生5个随机一等奖
            if(check(rand.nextInt(100),list,list_One)){
                k++;
            }
        }
        for(int a = 0; a < list_One.size();a++){
            System.out.println(list_One.get(a));
        }
    }

    /**
     * 验证该数字是否在集合中
     * @param i
     * @param list
     * @return
     */
    public static boolean check(int i,List<Integer> list){
        if(list.size() == 0){
            list.add(i);
        }else{
            for(int j = 0; j < list.size(); j++){
                if(i == list.get(j)){
                    return false;
                }
            }
            list.add(i);
        }
        return true;
    }

    /**
     * 抽取随机数作为中奖的下标(重载)
     * @param i
     * @param list
     * @param list_One
     * @return
     */
    public static boolean check(int i,List<Integer> list,List<Integer> list_One){
        if(list.size() == 0){
            list_One.add(list.get(i));
        }else{
            for(int j = 0; j < list.size(); j++){
                if(i == list.get(j)){
                    return false;
                }
            }
            list_One.add(list.get(i));
        }
        return true;
    }
}
