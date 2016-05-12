package me.xucan.drive.analyse;

import me.xucan.drive.bean.DriveEvent;

/**
 * Created by xcytz on 2016/4/23.
 * 行车事件类型
 */
public class EventType {
    //急刹
    public final static int EVENT_BRAKES = 0x00;

    //疲劳
    public final static int EVENT_FATIGUE = 0x01;

    //偏移
    public final static int EVENT_SKEWING = 0x02;

    //加速
    public final static int EVENT_ACCELERATION = 0x03;

    //减速
    public final static int EVENT_DECELERATION = 0x04;

    //交通良好
    public final static int ENVIR_NORMAL = 0x05;

    //交通拥堵
    public final static int ENVIR_JAM = 0x06;

    //交通雨天
    public final static int ENVIR_RAIN = 0x07;

    //交通大雾
    public final static int ENVIR_FOG= 0x08;


    //超速警告
    public final static int WARN_OVER_SPEED = 0x10;

    //追尾警告
    public final static int WARN_CRASH = 0x20;

    //擦蹭警告
    public final static int WARN_FRACTION = 0x30;

}
