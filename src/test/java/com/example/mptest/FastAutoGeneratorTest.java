package com.example.mptest;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootTest
class FastAutoGeneratorTest {

    @Test
    void contextLoads() {
        String url="jdbc:mysql://192.168.1.252:3306/huanbao_guotu_zzxz?useSSL=false&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
        String username="hbtest";
        String password="654321";

        String outDir="C:\\idea-project\\test\\mp-test\\src\\main\\java";
        String mapperXmlOutDir="C:\\idea-project\\test\\mp-test\\src\\main\\resources\\mapper";
        //1、数据库配置
        FastAutoGenerator.create(url, username, password)
                //2、全局配置
                .globalConfig(builder -> {
                    builder.author("ls")
                            .outputDir(outDir)   //设置输出路径
                            .commentDate("yyyy-MM-dd HH:mm:ss")   //设置日期格式
                            .dateType(DateType.ONLY_DATE)  //生成的实体类中日期的类型
                            .fileOverride()   //覆盖之前的文件
                            .enableSwagger()  //开启swagger模式
                            .disableOpenDir();   //禁止打开输出目录，默认打开
                })
                //3、包配置
                .packageConfig(builder -> {
                    builder.parent("com")
                            .moduleName("ls")
                            .entity("pojo")
                            .service("service")
                            .serviceImpl("serviceImpl")
                            .mapper("mapper")
                            .controller("controller")
                            .other("util")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperXmlOutDir));    //配置 mapper.xml 路径信息：项目的 resources 目录下*/
                })
                //4、策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("user")//设置需要生成的数据表名
                            /*.addTablePrefix("t_", "c_")   //设置过滤表前缀*/
                            //4.1、Mapper策略配置
                            .mapperBuilder()
                            .superClass(BaseMapper.class)   //设置父类
                            .formatMapperFileName("%sMapper")   //格式化mapper文件
                            .enableMapperAnnotation()   //开启@Mapper注解
                            .formatXmlFileName("%sXml")

                            //4.2、service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")

                            //4.3、实体类策略配置
                            .entityBuilder()
                            .enableLombok()   //开启lombok
                            .disableSerialVersionUID()   //不实现Serializable接口   不生产SerialVersionUID
                            .logicDeleteColumnName("deleted")  //逻辑删除
                            .naming(NamingStrategy.underline_to_camel)   //数据库表映射到实体的命名策略   下划线转驼峰命名
                            .columnNaming(NamingStrategy.underline_to_camel)   //数据库表的字段映射到实体的命名策略   下划线转驼峰命名
                            .addTableFills(
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE)
                            )    //自动填充
                            .enableTableFieldAnnotation()   //开启生成实体类时生成字段注解

                            //Controller策略配置
                            .controllerBuilder()
                            .formatFileName("%sController")
                            .enableRestStyle();   //开启生成@RestController注解
                })
                //5、模板引擎
                .templateEngine(new VelocityTemplateEngine())   //默认
                //6、执行
                .execute();
    }
}