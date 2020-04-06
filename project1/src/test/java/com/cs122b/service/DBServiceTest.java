package com.cs122b.service;


import jdk.nashorn.internal.objects.annotations.Property;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/applicationConf.xml"})
//@TestPropertySource(locations = "classpath:application.properties")
public class DBServiceTest {


//    private DBService dbService;
//
//    @Value("${database.username}")
//    private String name;
//
//    @Before
//    public void before(){
//
//    }
//
//    @Test
//    public void testConnection(){
//        System.out.println(name);
//    }

}