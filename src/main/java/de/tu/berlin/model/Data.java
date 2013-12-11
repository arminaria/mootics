package de.tu.berlin.model;


import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * User: Armin
 * Date: 22.11.13
 * Time: 13:26
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "DATA.ALL",
                query = "SELECT d FROM Data d")
})
public class Data implements Serializable {
    @Id
    private Long id;
    @Column
    private Calendar time;
    @Column
    private String userId;
    @Column
    private Long course;
    @Column
    private String module;
    @ManyToOne
    private Content cmid;

    @Column
    private String action;
    @Column
    private String url;
    @Column
    private String info;
    @Column
    private Calendar created;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCourse() {
        return course;
    }

    public void setCourse(Long course) {
        this.course = course;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Content getCmid() {
        return cmid;
    }

    public void setCmid(Content cmid) {
        this.cmid = cmid;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    @Override
    public String toString() {
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        return "Data{" +
                "id=" + id +
                ", time=" + format1.format(time.getTime()) +
                ", userId=" + userId +
                ", course=" + course +
                ", module='" + module + '\'' +
                ", cmid=" + cmid +
                ", action='" + action + '\'' +
                ", url='" + url + '\'' +
                ", info='" + info + '\'' +
                ", created=" + format1.format(created.getTime()) +
                '}';
    }
}
