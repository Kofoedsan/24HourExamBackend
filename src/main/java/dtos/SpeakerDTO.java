package dtos;

import entities.Speaker;

import java.util.Objects;

public class SpeakerDTO {
    private Integer dto_id;
    private String dto_name;
    private String dto_proffession;
    private String dto_gender;


    public SpeakerDTO(Integer dto_id, String dto_name, String dto_proffession, String dto_gender) {
        this.dto_id = dto_id;
        this.dto_name = dto_name;
        this.dto_proffession = dto_proffession;
        this.dto_gender = dto_gender;
    }

    public SpeakerDTO(String dto_name, String dto_proffession, String dto_gender) {
        this.dto_name = dto_name;
        this.dto_proffession = dto_proffession;
        this.dto_gender = dto_gender;
    }

    public SpeakerDTO(Speaker s) {
        this.dto_id = s.getId();
        this.dto_name = s.getName();
        this.dto_proffession = s.getProffession();
        this.dto_gender = s.getGender();

    }

    public Integer getDto_id() {
        return dto_id;
    }

    public void setDto_id(Integer dto_id) {
        this.dto_id = dto_id;
    }

    public String getDto_name() {
        return dto_name;
    }

    public void setDto_name(String dto_name) {
        this.dto_name = dto_name;
    }

    public String getDto_proffession() {
        return dto_proffession;
    }

    public void setDto_proffession(String dto_proffession) {
        this.dto_proffession = dto_proffession;
    }

    public String getDto_gender() {
        return dto_gender;
    }

    public void setDto_gender(String dto_gender) {
        this.dto_gender = dto_gender;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpeakerDTO)) return false;
        SpeakerDTO that = (SpeakerDTO) o;
        return Objects.equals(getDto_id(), that.getDto_id()) && Objects.equals(getDto_name(), that.getDto_name()) && Objects.equals(getDto_proffession(), that.getDto_proffession()) && Objects.equals(getDto_gender(), that.getDto_gender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDto_id(), getDto_name(), getDto_proffession(), getDto_gender());
    }
}
