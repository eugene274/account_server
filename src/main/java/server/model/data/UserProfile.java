package server.model.data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

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

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.INSERT)
    private Date registrationDate = new Date();


    @Column
    private String name;

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
}
