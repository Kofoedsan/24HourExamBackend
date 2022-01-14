package dtos;

import entities.PropList;

import java.util.Objects;

public class PropsDTO {
    private Integer dto_id;
    private String dto_item;

    public PropsDTO(Integer dto_id, String dto_item) {
        this.dto_id = dto_id;
        this.dto_item = dto_item;
    }

    public PropsDTO(PropList propList) {
        this.dto_id=propList.getId();
        this.dto_item=propList.getItem();
    }

    public Integer getDto_id() {
        return dto_id;
    }

    public void setDto_id(Integer dto_id) {
        this.dto_id = dto_id;
    }

    public String getDto_item() {
        return dto_item;
    }

    public void setDto_item(String dto_item) {
        this.dto_item = dto_item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropsDTO)) return false;
        PropsDTO propsDTO = (PropsDTO) o;
        return Objects.equals(getDto_id(), propsDTO.getDto_id()) && Objects.equals(getDto_item(), propsDTO.getDto_item());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDto_id(), getDto_item());
    }
}
