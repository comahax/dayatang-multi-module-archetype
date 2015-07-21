#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.User;

/**
 * User: zjzhai
 * Date: 13-3-13
 * Time: 下午2:16
 */
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String mobile;

    private String phoneNumber;

    private boolean accountExpired = false;

    private boolean accountLocked = false;

    private boolean credentialsExpired = false;

    private boolean disabled = false;


    public UserDto(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        mobile = user.getMobile();
        phoneNumber = user.getPhoneNumber();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
