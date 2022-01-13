package entities;

import javax.persistence.*;

@Entity
@NamedQuery(name = "prop_list.deleteAllRows", query = "DELETE from PropList")
@Table(name = "prop_list")
public class PropList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String item;

    public PropList(String item) {
        this.item = item;
    }

    public PropList() {
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
}