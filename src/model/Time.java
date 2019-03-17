package model;

public class Time {
	
	private int startTime;
	private int endTime;
	
	public Time(String timeString) {
	    int hour = Integer.parseInt(timeString.substring(0,2));
	    int minute = Integer.parseInt(timeString.substring(3,5));
	    int second = Integer.parseInt(timeString.substring(6,8));
	    int mili = Integer.parseInt(timeString.substring(9,12));
	    startTime = (hour * 3600000) + (minute * 60000) + (second * 1000) + mili;
	    hour = Integer.parseInt(timeString.substring(17,19));
	    minute = Integer.parseInt(timeString.substring(20,22));
	    second = Integer.parseInt(timeString.substring(23,25));
	    mili = Integer.parseInt(timeString.substring(26,29));
	    endTime = (hour * 3600000) + (minute * 60000) + (second * 1000) + mili;
	}
	
	public void adjustTime (int offset) {
	    startTime += offset;
	    endTime += offset;
	    if (startTime < 0) startTime = 0;
	    if (endTime < 0) endTime = 0;
	}
	
	public String getFormattedTime() {
	    int sTime = startTime;
	    int eTime = endTime;
	    int sHour = sTime / 3600000;
	    sTime -= (sHour * 3600000);
	    int sMinute = sTime / 60000;
	    sTime -= (sMinute * 60000);
	    int sSecond = sTime / 1000;
	    sTime -= (sSecond * 1000);
	    int sMili = sTime;
	    int eHour = eTime / 3600000;
	    eTime -= (eHour * 3600000);
	    int eMinute = eTime / 60000;
	    eTime -= (eMinute * 60000);
	    int eSecond = eTime / 1000;
	    eTime -= (eSecond * 1000);
	    int eMili = eTime;
	    String time = String.format("%02d:%02d:%02d,%03d --> %02d:%02d:%02d,%03d", sHour, sMinute, sSecond, sMili, eHour, eMinute, eSecond, eMili);
	    return time;
	}
}
