package entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import dtos.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@NamedQuery(name = "users.deleteAllRows", query = "DELETE from User")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;

    private String name;
    private String email;
    private int phone;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;
//    @JoinTable(name = "user_roles", joinColumns = {
//            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
//            @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();



//    public User(UserDTO udto) {
//        this.userName = udto.getDto_userName();
//        this.userPass = BCrypt.hashpw(udto.getDto_userPass(), BCrypt.gensalt());
//    }

        public User(UserDTO udto) {
        this.userName = udto.getDto_userName();
        this.userPass = BCrypt.hashpw(udto.getDto_userPass(), BCrypt.gensalt());
        this.name = udto.getDto_name();
        this.email = udto.getDto_email();
        this.phone = udto.getDto_phone();
    }

    public User(String userName, String name, String email, int phone, String userPass) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean verifyPassword(String pw) {
        return (BCrypt.checkpw(pw, userPass));
    }

    public User(String userName, String userPass) {
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return this.userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
