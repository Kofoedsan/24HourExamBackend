package entities;

import dtos.PropsDTO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQuery(name = "prop_list.deleteAllRows", query = "DELETE from PropList")
@Table(name = "prop_list")
public class PropList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String item;


    public PropList(Integer id, String item) {
        this.id = id;
        this.item = item;
    }

    public PropList(String item) {
        this.item = item;
    }

    public PropList() {
    }

    public PropList(PropsDTO prop) {
        this.id=prop.getDto_id();
        this.item= prop.getDto_item();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropList)) return false;
        PropList propList = (PropList) o;
        return Objects.equals(getId(), propList.getId()) && Objects.equals(getItem(), propList.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getItem());
    }
}