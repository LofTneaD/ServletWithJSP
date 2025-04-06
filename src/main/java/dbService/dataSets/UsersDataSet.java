package dbService.dataSets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersDataSet {
    private String login;
    private String password;
    private String email;

    @JsonCreator
    public UsersDataSet( @JsonProperty("login") String login,
                         @JsonProperty("password") String password,
                         @JsonProperty("email") String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
