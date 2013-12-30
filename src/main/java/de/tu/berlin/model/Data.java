package de.tu.berlin.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * User: Armin
 * Date: 22.11.13
 * Time: 13:26
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "DATA.ALL",
                query = "d FROM Data d")
})
public class Data implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne
    private User user;
    @Column
    private Calendar time;
    @Column
    private String action;
    @Column
    private String url;
    @ManyToOne
    private Material material;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", user=" + user +
                ", time=" + time +
                ", action='" + action + '\'' +
                ", url='" + url + '\'' +
                ", material=" + material +
                '}';
    }
}
