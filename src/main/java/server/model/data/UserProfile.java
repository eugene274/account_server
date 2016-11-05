package server.model.data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.NaturalId;
import server.model.UserMutable;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by eugene on 10/9/16.
 */

@Entity(name = "Profiles")
@Table(name = "users")
public class UserProfile {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @UserMutable
    @Column(name = "login")
    @NaturalId
    private String email;

    @Column(name = "registration_date", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date registrationDate = new Date();


    @UserMutable
    @Column
    private String name;

    @UserMutable
    @Column(nullable = false)
    private String password;

    public UserProfile() {
    }

    public UserProfile(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean checkCredentials(String email, String pass){
        return getEmail().equals(email) && getPassword().equals(pass);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
