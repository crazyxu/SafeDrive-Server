package me.xucan.drive.util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	public final static String RECORD_CREATE = "me.xucan.drive.mapping.DriveRecordMapper.createRecord";
	public final static String RECORD_UPDATE = "me.xucan.drive.mapping.DriveRecordMapper.updateRecord";
	public final static String RECORD_SELECT = "me.xucan.drive.mapping.DriveRecordMapper.selectRecord";
	public final static String RECORD_GET_ONE = "me.xucan.drive.mapping.DriveRecordMapper.getRecord";
	public final static String RECORD_UPDATE_SAFETYINDEX = "me.xucan.drive.mapping.DriveRecordMapper.updateSafetyIndex";

	
	public final static String EVENT_INSERT = "me.xucan.drive.mapping.DriveEventMapper.insertEvent";
	public final static String EVENT_SELECT = "me.xucan.drive.mapping.DriveEventMapper.selectEvent";
	
	public final static String USER_INSERT = "me.xucan.drive.mapping.UserMapper.createUser";
	public final static String USER_UPDATE_TOKEN = "me.xucan.drive.mapping.UserMapper.updateUserToken";
	public final static String USER_SELECT = "me.xucan.drive.mapping.UserMapper.selectUser";
	public final static String USER_UPDATE_USER = "me.xucan.drive.mapping.UserMapper.updateUser";

	public static SqlSession getSession(){
	    String resource = "conf.xml";
	    InputStream is = MyBatisUtil.class.getClassLoader().getResourceAsStream(resource);
	    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
	    return sessionFactory.openSession(true);
	}
}
