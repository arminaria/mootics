package de.tu.berlin.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Armin
 * Date: 23.12.13
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
public class Grades {
    @Id
    @OneToOne
    private User user;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private String lecture;

    @Column
    private long grade;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getGrade() {
        return grade;
    }

    public void setGrade(long grade) {
        this.grade = grade;
    }
}
