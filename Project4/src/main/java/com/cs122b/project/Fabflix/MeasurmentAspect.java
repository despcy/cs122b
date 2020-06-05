package com.cs122b.project.Fabflix;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

@Aspect
@Component
public class MeasurmentAspect {

    public static Long TS=0l;
    public static Long TJ=0l;
    public static BufferedWriter filewriter;
    public static String curfile;

    @Value("${performancePath}")
    private String logpath;

    @Before("execution(* com.cs122b.project.Fabflix.controller.MovieController.Search(..)) && args(..,  headers)")
    public void beforemeasure(Map<String, String> headers){

        try {
            if(!headers.containsKey("logfile")){
                curfile=null;
                return;
            }
            System.out.println(headers.get("logfile"));
            //init file handler
            if (filewriter == null || !curfile.equals(headers.get("logfile"))) {
                curfile = headers.get("logfile");
                if (filewriter != null) filewriter.close();
                String file=logpath+curfile+".log";
                File f=new File(file);
                f.createNewFile();

                System.out.println("New log file created"+file);
                filewriter = new BufferedWriter(new FileWriter(file));


            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Around("execution(* com.cs122b.project.Fabflix.controller.MovieController.Search(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object proceed = joinPoint.proceed();
        long executionTime = System.nanoTime() - start;

        TS=executionTime;
        return proceed;
    }
    @After("execution(* com.cs122b.project.Fabflix.controller.MovieController.Search(..))")
    public void  aftermeasure(JoinPoint joinPoint){
        if(curfile==null)return;
        String out="{\"EndTime\":"+String.valueOf(System.nanoTime())+","+"\"TS\":"+String.valueOf(TS) + ","+"\"TJ\":"+String.valueOf(TJ)+"}\n";
        System.out.println(out);
        try {
            filewriter.write(out);
            filewriter.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

        TS=0l;
        TJ=0l;
    }
    @Around("execution(* com.cs122b.project.Fabflix.Service.DBQueryExec.*(..))")
    public Object measureJDBCExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.nanoTime();
        Object proceed = joinPoint.proceed();
        long executionTime = System.nanoTime() - start;
        TJ+=executionTime;
        return proceed;
    }

}