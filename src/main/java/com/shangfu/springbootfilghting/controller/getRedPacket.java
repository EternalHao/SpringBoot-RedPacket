package com.shangfu.springbootfilghting.controller;

import jxl.write.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import jxl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//随机抽取人员
@Controller
@RequestMapping("/RedPacket")
public class getRedPacket {

   // public static List<String> PEOPLE = null;
    public static int COUNT = 1; //定义计数器，第一次读文件，其余直接抽取
    @RequestMapping("/getRedPacket")
    @ResponseBody
    public List<String> getRedPacket(@RequestParam("count") String num, HttpServletResponse response, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String message = "ok";
        Sheet sheet;
        Workbook workbook;
        Cell cell;
        int i = 1;
        List<String> people = new ArrayList<String>();
        // jdc jdc=new jdc();
        String path = "./src/main/resources/up/";
        File file = new File(path);
        File[] array = file.listFiles();
        String filename = array[0].getName();
        //利用jxl读取excel中的人名
        try {
            workbook = Workbook.getWorkbook(new File(path + filename));
            //获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
            sheet = workbook.getSheet(0);

            while (true) {
                cell = sheet.getCell(0, i);
                if (cell.getContents().equals("") == true) {
                    break;
                }
                i++;
                people.add(cell.getContents());
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute("people",people);
        //new File(path+filename).delete();
        //int sum=UpController.First+UpController.Second+UpController.Third;
        int sum = Integer.parseInt(num);
        List<String> list = chouqu(sum, people);
        // List<String> first = chouqu(UpController.First,list);
        // List<String> second = chouqu(UpController.Second,list);
        // List<String> third = chouqu(UpController.Third,list);

        //生成excel文件
        // createExcel(first,second,third);

        return list;
    }

    @RequestMapping("/get")
    @ResponseBody
    public List<String> get(@RequestParam("count") String num,HttpServletRequest request){
        HttpSession session = request.getSession();
        List<String> people = (List<String>) session.getAttribute("people");
        if(people.size()==0){
            List<String> error = new ArrayList<>();
            error.add("抽奖人数已达总人数");
            return error;
        }else {
            int sum = Integer.parseInt(num);
            List<String> list = chouqu(sum, people);
            return list;
        }
    }
    public List<String> chouqu(int number1,List<String> list){
        List<String> list1=new ArrayList<String>();
        Random random=new Random();
        for(int c=0;c<number1;c++){
            int number=random.nextInt(list.size());
            String name=list.get(number);
            list1.add(name);
            list.remove(number);
        }
        return list1;
    }

    public void createExcel(List<String> first,List<String> second,List<String> third){
        File xlsFile = new File("./src/main/resources/load/result.xls");
        WritableWorkbook workbook = null;
        WritableSheet sheet = null;
        try {
            workbook = Workbook.createWorkbook(xlsFile);
            //添加第一个工作表
            sheet = workbook.createSheet("sheetName", 0);
            //添加表头Label(列，行，内容)
            sheet.addCell(new Label(0,0,"一等奖"));
            sheet.addCell(new Label(1,0,"二等奖"));
            sheet.addCell(new Label(2,0,"三等奖"));

            for(int a=1;a<first.size()+1;a++){
                sheet.addCell(new Label(0,a,first.get(a-1).toString()));
            }
            for(int b=1;b<second.size()+1;b++){
                sheet.addCell(new Label(1,b,second.get(b-1).toString()));
            }
            for(int c=1;c<third.size()+1;c++){
                sheet.addCell(new Label(2,c,third.get(c-1).toString()));
            }

            workbook.write();
            workbook.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @RequestMapping("/download")
    public String downLoad(HttpServletResponse response){
        String filename="result.xls";
        String filePath = "./src/main/resources/load/" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

}
