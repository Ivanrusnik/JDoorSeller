package com.jds;

import com.jds.model.DoorPart;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@EnableAutoConfiguration(exclude = { //
        HibernateJpaAutoConfiguration.class })
@Configuration
@ComponentScan

public class Application {

    @Autowired
    private Environment env;

    public static void main(String[] args) throws Throwable {

        SpringApplication.run(Application.class, args);

    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
        Properties properties = new Properties();

        // See: application.properties
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
        properties.put("current_session_context_class", //
                env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));

        // Fix Postgres JPA Error:
        // Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.
        properties.put("hibernate.temp.use_jdbc_metadata_defaults",false);

        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        // Package contain entity classes
        factoryBean.setPackagesToScan(new String[] { "" });
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet();
        //
        SessionFactory sf = factoryBean.getObject();
        System.out.println("## getSessionFactory: " + sf);
        return sf;
    }


    //only for test delete please
    public static List<Sheet> testDelete(){
        List<DoorPart> doorPartList = new ArrayList<>();
        doorPartList.add(new DoorPart("1",1249,200,1));
        doorPartList.add(new DoorPart("1",600,150,1));
        doorPartList.add(new DoorPart("1",400,300,1));
        doorPartList.add(new DoorPart("1",400,300,1));
        doorPartList.add(new DoorPart("1",400,300,1));
        doorPartList.add(new DoorPart("1",400,300,1));
        doorPartList.add(new DoorPart("1",400,300,1));
        doorPartList.add(new DoorPart("1",400,300,1));
        doorPartList.add(new DoorPart("1",400,300,1));
        doorPartList.add(new DoorPart("1",400,300,1));
        doorPartList.add(new DoorPart("1",400,300,1));
        doorPartList.add(new DoorPart("1",400,300,1));
        doorPartList.add(new DoorPart("1",400,300,1));
        doorPartList.add(new DoorPart("1",400,300,1));

        Sheet sheet = new Sheet(2500,1250);

        SheetCutting sheetCutting = new SheetCutting(doorPartList,sheet);
        sheetCutting.CompleteCutting();
        return sheetCutting.getSheets();
    }

}