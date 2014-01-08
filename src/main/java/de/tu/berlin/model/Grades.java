package de.tu.berlin.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Armin
 * Date: 23.12.13
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
@Entity@NamedQueries({
        @NamedQuery(name="Grade.Name", query="SELECT g FROM Grades g where g.name=:name")
})
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

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
