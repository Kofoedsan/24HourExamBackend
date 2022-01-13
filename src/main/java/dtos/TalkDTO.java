package dtos;

import entities.Speaker;
import entities.Talk;

public class TalkDTO {
    private Integer dto_id;
    private String dto_topic;
    private String dto_duration;
    private String dto_location;
    private Integer dto_capacity;
    private String dto_date;
    private String dto_time;

    public TalkDTO(Integer dto_id, String dto_topic, String dto_duration) {
        this.dto_id = dto_id;
        this.dto_topic = dto_topic;
        this.dto_duration = dto_duration;
    }

    public TalkDTO(Talk t) {
        this.dto_id = t.getId();
        this.dto_topic = t.getTopic();
        this.dto_duration = t.getduration();
        if (t.getConference() != null) {
            this.dto_location = t.getConference().getlocation();
            this.dto_capacity = t.getConference().getcapacity();
            this.dto_date = t.getConference().getDate();
            this.dto_time = t.getConference().getTime();
        }
    }

    public Integer getDto_id() {
        return dto_id;
    }

    public void setDto_id(Integer dto_id) {
        this.dto_id = dto_id;
    }

    public String getDto_topic() {
        return dto_topic;
    }

    public void setDto_topic(String dto_topic) {
        this.dto_topic = dto_topic;
    }

    public String getDto_duration() {
        return dto_duration;
    }

    public void setDto_duration(String dto_duration) {
        this.dto_duration = dto_duration;
    }

    public String getDto_location() {
        return dto_location;
    }

    public void setDto_location(String dto_location) {
        this.dto_location = dto_location;
    }

    public Integer getDto_capacity() {
        return dto_capacity;
    }

    public void setDto_capacity(Integer dto_capacity) {
        this.dto_capacity = dto_capacity;
    }

    public String getDto_date() {
        return dto_date;
    }

    public void setDto_date(String dto_date) {
        this.dto_date = dto_date;
    }

    public String getDto_time() {
        return dto_time;
    }

    public void setDto_time(String dto_time) {
        this.dto_time = dto_time;
    }
}
