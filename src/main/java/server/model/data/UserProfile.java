package server.model.data;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

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
    private String login;

    @Column(nullable = false)
    private String password;

    public UserProfile() {
    }

    public UserProfile(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public boolean checkCredentials(String login, String pass){
        return getLogin().equals(login) && getPassword().equals(pass);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
}
