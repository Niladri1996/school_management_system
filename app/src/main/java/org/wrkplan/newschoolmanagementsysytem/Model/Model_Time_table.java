package org.wrkplan.newschoolmanagementsysytem.Model;

public class Model_Time_table {
    String day;
    String time_to,time_from,subject,teacher,break_yn;

    public String getBreak_yn() {
        return break_yn;
    }

    public void setBreak_yn(String break_yn) {
        this.break_yn = break_yn;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
