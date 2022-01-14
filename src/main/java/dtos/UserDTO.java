package dtos;

import entities.Role;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDTO {

    private int dto_id;
    private String dto_userName;
    private String dto_Name;
    private int dto_phone;
    private String dto_email;
    private String dto_userPass;
    private List<Role> dto_roleList = new ArrayList<>();
    private List<String> dto_roleListAsString = new ArrayList<>();

    public UserDTO(User u) {
        this.dto_id = u.getId();
        this.dto_userName = u.getUserName();
        this.dto_Name = u.getName();
        this.dto_phone = u.getPhone();
    }

    public UserDTO(int dto_id, String dto_userName, String dto_Name, int dto_phone, List<String> dto_roleListAsString) {
        this.dto_id = dto_id;
        this.dto_userName = dto_userName;
        this.dto_Name = dto_Name;
        this.dto_phone = dto_phone;
        this.dto_roleListAsString = dto_roleListAsString;
    }

    //For testing only
    public UserDTO(String dto_userName, String dto_Name, int dto_phone) {
        this.dto_userName = dto_userName;
        this.dto_Name = dto_Name;
        this.dto_phone = dto_phone;
    }

    public String getDto_userName() {
        return dto_userName;
    }

    public void setDto_userName(String dto_userName) {
        this.dto_userName = dto_userName;
    }

    public String getDto_userPass() {
        return dto_userPass;
    }

    public void setDto_userPass(String dto_userPass) {
        this.dto_userPass = dto_userPass;
    }

    public List<Role> getDto_roleList() {
        return dto_roleList;
    }

    public void setDto_roleList(List<Role> dto_roleList) {
        this.dto_roleList = dto_roleList;
    }

    public List<String> getDto_roleListAsString() {
        return dto_roleListAsString;
    }

    public void setDto_roleListAsString(List<String> dto_roleListAsString) {
        this.dto_roleListAsString = dto_roleListAsString;
    }

    public int getDto_id() {
        return dto_id;
    }

    public void setDto_id(int dto_id) {
        this.dto_id = dto_id;
    }

    public String getDto_Name() {
        return dto_Name;
    }

    public void setDto_Name(String dto_Name) {
        this.dto_Name = dto_Name;
    }

    public int getDto_phone() {
        return dto_phone;
    }

    public void setDto_phone(int dto_phone) {
        this.dto_phone = dto_phone;
    }

    public String getDto_email() {
        return dto_email;
    }

    public void setDto_email(String dto_email) {
        this.dto_email = dto_email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return getDto_id() == userDTO.getDto_id() && getDto_phone() == userDTO.getDto_phone() && Objects.equals(getDto_userName(), userDTO.getDto_userName()) && Objects.equals(getDto_Name(), userDTO.getDto_Name()) && Objects.equals(getDto_userPass(), userDTO.getDto_userPass()) && Objects.equals(getDto_roleList(), userDTO.getDto_roleList()) && Objects.equals(getDto_roleListAsString(), userDTO.getDto_roleListAsString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDto_id(), getDto_userName(), getDto_Name(), getDto_phone(), getDto_userPass(), getDto_roleList(), getDto_roleListAsString());
    }
}
