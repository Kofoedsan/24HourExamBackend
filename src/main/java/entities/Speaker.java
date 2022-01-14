package entities;

import dtos.SpeakerDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "speaker.deleteAllRows", query = "DELETE from Speaker ")
@Table(name = "speaker")
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String proffession;
    private String gender;


    @JoinTable(name = "speaker_talkList",
            joinColumns = @JoinColumn(name = "Speaker_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "talkList_id", referencedColumnName = "id"))
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Talk> talkList = new ArrayList<>();

    public Speaker() {
    }

    public Speaker(String name, String proffession, String gender) {
        this.name = name;
        this.proffession = proffession;
        this.gender = gender;
    }

    public Speaker(SpeakerDTO speakerDTO) {
        this.name = speakerDTO.getDto_name();
        this.proffession = speakerDTO.getDto_proffession();
        this.gender = speakerDTO.getDto_gender();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProffession() {
        return proffession;
    }

    public void setProffession(String proffession) {
        this.proffession = proffession;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Talk> getTalkList() {
        return talkList;
    }

    public void setTalkList(List<Talk> talkList) {
        this.talkList = talkList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speaker)) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(getId(), speaker.getId()) && Objects.equals(getName(), speaker.getName()) && Objects.equals(getProffession(), speaker.getProffession()) && Objects.equals(getGender(), speaker.getGender()) && Objects.equals(getTalkList(), speaker.getTalkList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getProffession(), getGender(), getTalkList());
    }
}