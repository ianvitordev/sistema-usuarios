package sistema_gestao_agro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data// Anotação do Lombok para gerar getters, setters, toString, equals e hashCode automaticamente
public class LoginDTO {
    
    @Email
    @NotBlank// Anotações de validação para garantir que o email seja válido e não esteja em branco
    private String email;

    @NotBlank// Anotação de validação para garantir que a senha não esteja em branco
    private String password;
}
