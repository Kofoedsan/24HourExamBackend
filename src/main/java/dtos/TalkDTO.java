package dtos;

import entities.PropList;
import entities.Talk;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TalkDTO {
    private Integer dto_id;
    private String dto_topic;
    private String dto_duration;
    private String dto_location;
    private Integer dto_capacity;
    private String dto_date;
    private String dto_time;
    private Integer dto_props_id;
    private String dto_props_item;
    private List<PropsDTO> dto_propsDTOList;


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

        if (t.getProps().size() > 0) {
            for (int i = 0; i < t.getProps().size(); i++) {
                if (t.getProps().get(i) != null) {
                    List<PropsDTO> propsDTOS = new ArrayList<>();
                    PropsDTO propsDTO = new PropsDTO(t.getProps().get(i));
                    propsDTOS.add(propsDTO);
                    this.dto_propsDTOList = propsDTOS;
                }
            }
        }
    }

    public TalkDTO(PropList propList) {
        this.dto_props_id = propList.getId();
        this.dto_props_item = propList.getItem();

    }


    public List<PropsDTO> getDto_propsDTOList() {
        return dto_propsDTOList;
    }

    public void setDto_propsDTOList(List<PropsDTO> dto_propsDTOList) {
        this.dto_propsDTOList = dto_propsDTOList;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TalkDTO)) return false;
        TalkDTO talkDTO = (TalkDTO) o;
        return Objects.equals(getDto_id(), talkDTO.getDto_id()) && Objects.equals(getDto_topic(), talkDTO.getDto_topic()) && Objects.equals(getDto_duration(), talkDTO.getDto_duration()) && Objects.equals(getDto_location(), talkDTO.getDto_location()) && Objects.equals(getDto_capacity(), talkDTO.getDto_capacity()) && Objects.equals(getDto_date(), talkDTO.getDto_date()) && Objects.equals(getDto_time(), talkDTO.getDto_time()) && Objects.equals(dto_props_id, talkDTO.dto_props_id) && Objects.equals(dto_props_item, talkDTO.dto_props_item) && Objects.equals(getDto_propsDTOList(), talkDTO.getDto_propsDTOList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDto_id(), getDto_topic(), getDto_duration(), getDto_location(), getDto_capacity(), getDto_date(), getDto_time(), dto_props_id, dto_props_item, getDto_propsDTOList());
    }
}
