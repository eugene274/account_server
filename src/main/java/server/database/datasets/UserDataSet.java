package server.database.datasets;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by eugene on 10/10/16.
 */

@SuppressWarnings("DefaultFileTemplate")
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserDataSet
        implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @NaturalId
    @Column(name = "name")
    protected String login;

    @Column(name = "pass")
    protected String password;

    public UserDataSet(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserDataSet() {
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
}
