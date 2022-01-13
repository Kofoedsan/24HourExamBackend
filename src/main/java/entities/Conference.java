package entities;

import dtos.ConferenceDTO;
import dtos.TalkDTO;

import javax.persistence.*;

@Entity
@NamedQuery(name = "conference.deleteAllRows", query = "DELETE from Conference")
@Table(name = "conference")
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String location;
    private Integer capacity;
    private String date;
    private String time;


    public Conference(String location, Integer capacity, String date, String time) {
        this.location = location;
        this.capacity = capacity;
        this.date = date;
        this.time = time;
    }

    public Conference(Integer id, String location, Integer capacity, String date, String time) {
        this.id = id;
        this.location = location;
        this.capacity = capacity;
        this.date = date;
        this.time = time;
    }

    public Conference() {
    }

    public Conference(ConferenceDTO conferenceDTO) {
        this.location = conferenceDTO.getDto_location();
        this.capacity = conferenceDTO.getDto_capacity();
        this.date = conferenceDTO.getDto_date();
        this.time = conferenceDTO.getDto_time();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }

    public Integer getcapacity() {
        return capacity;
    }

    public void setcapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}