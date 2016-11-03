package server.model.data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by eugene on 10/9/16.
 */

@Entity(name = "userProfile")
@Table(name = "users")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NaturalId
    private String email;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Calendar registrationDate;


    @Column
    private String name;

    @Column(nullable = false)
    private String password;

    public UserProfile() {
    }

    public UserProfile(String login, String password) {
        this.email = login;
        this.password = password;
    }

    public boolean checkCredentials(String login, String pass){
        return getEmail().equals(login) && getPassword().equals(pass);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getRegistrationDate() {
        return registrationDate;
    }

    private void setRegistrationDate(Calendar registrationDate) {
        this.registrationDate = registrationDate;
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
}
