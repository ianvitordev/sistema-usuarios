package sistema_gestao_agro.mapper;

import sistema_gestao_agro.dto.UserResponseDTO;
import sistema_gestao_agro.model.User;

public class UserMapper {
    
    //método para converter User para UserResponseDTO
    public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole().name()
            
        );
    }
    
    //método para converter UserResponseDTO para User
    public static User toEntity(UserResponseDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(User.Role.valueOf(dto.getRole().toUpperCase()));
        user.setActive(true); // Ativa por padrão
        return user;
    }
}
