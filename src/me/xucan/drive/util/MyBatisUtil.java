package me.xucan.drive.util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	public final static String RECORD_CREATE = "me.xucan.drive.mapping.DriveRecordMapper.createRecord";
	public final static String RECORD_UPDATE = "me.xucan.drive.mapping.DriveRecordMapper.updateRecord";
	
	public static SqlSession getSession(){
		//mybatis�������ļ�
	    String resource = "conf.xml";
	    //ʹ�������������mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
	    InputStream is = MyBatisUtil.class.getClassLoader().getResourceAsStream(resource);
	    //����sqlSession�Ĺ���
	    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
	    //ʹ��MyBatis�ṩ��Resources�����mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
	    //Reader reader = Resources.getResourceAsReader(resource); 
	    //����sqlSession�Ĺ���
	    //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
	    //������ִ��ӳ���ļ���sql��sqlSession
	    return sessionFactory.openSession();
	}
}
