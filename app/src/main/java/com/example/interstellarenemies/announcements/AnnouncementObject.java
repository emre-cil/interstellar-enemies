package com.example.interstellarenemies.announcements;

class AnnouncementObject {
    public String header;
    public String content;
    public String date;
    public String id;

    public AnnouncementObject(String id, String header, String content, String date) {
        this.id = id;
        this.header = header;
        this.content = content;
        this.date = date;
    }
}


