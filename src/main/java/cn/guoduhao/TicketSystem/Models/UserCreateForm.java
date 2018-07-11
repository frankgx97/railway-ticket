package cn.guoduhao.TicketSystem.Models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserCreateForm {

    @NotEmpty(message = "邮箱不能为空")
    private String email;

    @NotEmpty(message = "名字不能为空")
    private String name;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "重复密码不能为空")
    private String passwordRepeated;

    @NotNull
    private Role role = Role.USER;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "email: " +  this.getEmail()
                + " name: " + this.getName()
                + " password: " + this.getPassword()
                + " passwordRepeated: " + this.getPasswordRepeated();
    }
}
