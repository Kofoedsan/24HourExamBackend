package entities;

import dtos.TalkDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name = "talk.deleteAllRows", query = "DELETE from Talk ")
@Table(name = "talk")
public class Talk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String topic;
    private String duration;


    @JoinColumn
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Conference Conference;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PropList> props;


    public Talk(String topic, String duration) {
        this.topic = topic;
        this.duration = duration;
    }

    public Talk() {
    }

    public Talk(TalkDTO talkDTO) {
        this.topic = talkDTO.getDto_topic();
        this.duration = talkDTO.getDto_duration();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getduration() {
        return duration;
    }

    public void setduration(String duration) {
        this.duration = duration;
    }

    public entities.Conference getConference() {
        return Conference;
    }

    public void setConference(entities.Conference conference) {
        Conference = conference;
    }

    public List<PropList> getProps() {
        return props;
    }

    public void setProps(List<PropList> props) {
        this.props = props;
    }
}