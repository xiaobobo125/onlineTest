package com.bolife.online.test;

import com.bolife.online.entity.Question;
import com.bolife.online.util.ExcelUtil;
import com.bolife.online.util.FinalDefine;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/10 17:05
 * @Description:
 */
public class FileUploadTest {

    @Test
    public void demo1() throws Exception {
        InputStream in = new FileInputStream(new File("D:/test.xls"));
        List<List<Object>> bankListByExcel = ExcelUtil.getBankListByExcel(in, "D:/test.xls");
        List<Question> questions = new ArrayList<>();
        for (List<Object> objects : bankListByExcel) {
            Question qu = new Question();
            List<Object> list = objects;
            String flag = String.valueOf(list.get(0));
            qu.setTitle(String.valueOf(list.get(1)));
            qu.setContent(String.valueOf(list.get(1)));
            if(flag.equals("xz")){
                qu.setOptionA(String.valueOf(list.get(2)));
                qu.setOptionB(String.valueOf(list.get(3)));
                qu.setOptionC(String.valueOf(list.get(4)));
                qu.setOptionD(String.valueOf(list.get(5)));
                qu.setAnswer(String.valueOf(list.get(6)));
            }else  if(flag.equals("tk")){
                String answer = "";
                for (int i = 2 ; i < list.size();i++){
                    answer += String.valueOf(list.get(i));
                    answer+= FinalDefine.SPLIT_CHAR;
                }
                qu.setAnswer(answer);
            }else if(flag.equals("pd")){
                qu.setOptionA(String.valueOf(list.get(2)));
                qu.setOptionB(String.valueOf(list.get(3)));
                qu.setAnswer(String.valueOf(list.get(4)));

            }else if (flag.equals("hd")){
                qu.setAnswer(String.valueOf(list.get(2)));
            }
            questions.add(qu);
            System.out.println(qu);
        }
    }
}
