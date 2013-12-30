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
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @OneToOne
    private User user;

    @ManyToOne
    private Category category;

    @Column
    private String name;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGrade() {
        return grade;
    }

    public void setGrade(long grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Grades{" +
                "id=" + id +
                ", user=" + user +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }
}
