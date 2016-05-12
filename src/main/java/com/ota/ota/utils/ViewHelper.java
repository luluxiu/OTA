package com.ota.ota.utils;

import com.domingosuarez.boot.autoconfigure.jade4j.JadeHelper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by freedom on 2016/3/21.
 */
@Service
@JadeHelper("viewHelper")
public class ViewHelper {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private long startTime;

    private String path;

    public long getResponseTime(){
        return System.currentTimeMillis() - startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getFormattedDate(Date date) {
        return date == null ? "" : DATE_FORMAT.format(date);
    }

    public String getFormattedDate(Long timestamp) {
        return DATE_FORMAT.format(new Date(timestamp));
    }

    public String getFormattedSize(Long length) {
        DecimalFormat df = new DecimalFormat("#.00");
        String size = "";

        if(length < 1024) {
            size = df.format((double) length) + " B";
        }
        else if (length < 1024 * 1024) {
            size = df.format((double) length / 1024) + " KB";
        }
        else if (length < 1024 * 1024 * 1024) {
            size = df.format((double) length / 1024 / 1024) + " MB";
        }
        else {
            size = df.format((double) length / 1024 / 1024 /1024) + " GB";
        }

        return size;
    }

    public String getAppPath() {
        return path;
    }

    public void setAppPath(String path) {
        this.path = path;
    }

}
