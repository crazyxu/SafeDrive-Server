package me.xucan.drive.bean;

public class DriveEvent {
	//�¼�����
    private int type;
    //һ�γ���DriveRecord Id
    private int recordId;
    //����ʱ��
    private long time;
    //extra
    private String extra;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
