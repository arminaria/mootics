package de.tu.berlin.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Material {

    @Id
    private String name;

    @OneToMany
    private List<Data> datas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return name;
    }
}
