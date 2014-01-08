package de.tu.berlin.model;


import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * User: Armin
 * Date: 22.11.13
 * Time: 13:26
 */
@Entity@NamedQueries({
        @NamedQuery(name="Data.Match", query="SELECT d FROM Data d where " +
                "d.action=:action and d.material=:material and d.time=:time and d.user=:user"),
        @NamedQuery(name="Data.urls", query = "Select d from Data d where d.url = :url")
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
    @Column
    private String material;
    @Column
    private String category;
    @Column
    private String lecture;


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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    @Override
    public String toString() {
        DateFormat dateInstance = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.GERMAN);
        DateFormat timeInstance = DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.GERMAN);
        return "Data{" +
                "id=" + id +
                ", user=" + user +
                ", time=" + dateInstance.format(time.getTime()) + " " + timeInstance.format(time.getTime()) +
                ", action='" + action + '\'' +
                ", url='" + url + '\'' +
                ", material=" + material +
                '}';
    }
}
