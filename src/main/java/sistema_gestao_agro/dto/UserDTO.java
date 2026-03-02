package sistema_gestao_agro.dto;
import sistema_gestao_agro.model.User;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {

    @NotBlank
    private String name;
    @Email
    private String email;
    private User.Role role;
    private Boolean active;
    private String password;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public User.Role getRole() {
        return role;
    }
    public void setRole(User.Role role) {
        this.role = role;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

   
    
    
}
