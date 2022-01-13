package dtos;

import entities.Conference;

public class ConferenceDTO {
    private Integer dto_id;
    private String dto_location;
    private Integer dto_capacity;
    private String dto_date;
    private String dto_time;


    public ConferenceDTO(Integer dto_id, String dtolocation, Integer dtocapacity, String dto_date, String dto_time) {
        this.dto_id = dto_id;
        this.dto_location = dtolocation;
        this.dto_capacity = dtocapacity;
        this.dto_date = dto_date;
        this.dto_time = dto_time;
    }

    public ConferenceDTO(String dtolocation, Integer dtocapacity, String dto_date, String dto_time) {
        this.dto_location = dtolocation;
        this.dto_capacity = dtocapacity;
        this.dto_date = dto_date;
        this.dto_time = dto_time;
    }


    public ConferenceDTO(Conference c) {
        this.dto_id = c.getId();
        this.dto_location = c.getlocation();
        this.dto_capacity = c.getcapacity();
        this.dto_date = c.getDate();
        this.dto_time = c.getTime();
    }


    public Integer getDto_id() {
        return dto_id;
    }

    public void setDto_id(Integer dto_id) {
        this.dto_id = dto_id;
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
