package me.xucan.drive.util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	public final static String RECORD_CREATE = "me.xucan.drive.mapping.DriveRecordMapper.createRecord";
	public final static String RECORD_UPDATE = "me.xucan.drive.mapping.DriveRecordMapper.updateRecord";
	
	public static SqlSession getSession(){
		//mybatis的配置文件
	    String resource = "conf.xml";
	    //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
	    InputStream is = MyBatisUtil.class.getClassLoader().getResourceAsStream(resource);
	    //构建sqlSession的工厂
	    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
	    //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
	    //Reader reader = Resources.getResourceAsReader(resource); 
	    //构建sqlSession的工厂
	    //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
	    //创建能执行映射文件中sql的sqlSession
	    return sessionFactory.openSession();
	}
}
