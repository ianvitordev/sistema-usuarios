package sistema_gestao_agro.service;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sistema_gestao_agro.dto.UserDTO;
import sistema_gestao_agro.dto.UserResponseDTO;
import sistema_gestao_agro.mapper.UserMapper;
import sistema_gestao_agro.model.User;
import sistema_gestao_agro.repository.UserRepository;

@Service
public class UserService {
     //injeção de dependência do UserRepository
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    
    //construtor para injetar o UserRepository e o PasswordEncoder
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    }

public UserResponseDTO criar(UserDTO dto) {

    User user = new User();
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());

    if (dto.getRole() == null) {
        user.setRole(User.Role.USER);
    } else {
        user.setRole(dto.getRole());
    }

    user.setActive(true);
    user.setPassword(passwordEncoder.encode(dto.getPassword()));

    User savedUser = repository.save(user);

    return UserMapper.toResponseDTO(savedUser);
}
    //método para atualizar um usuário existente
    public User atualizar(User user) {
        return repository.save(user);
    }



    //método para listar todos os usuários
    public List<User> listar(){
    return repository.findAll();
    }
    
    //método para buscar um usuário por ID e retornar um UserResponseDTO
    public UserResponseDTO buscarPorId(Long id) {
    User user = repository.findById(id)
    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    return UserMapper.toResponseDTO(user);
        }
    
    //método para buscar um usuário por ID e retornar a entidade User
    public User buscarEntidadePorId(Long id) {
    return repository.findById(id)
    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


    
}

    public void deletar(Long id) {
    User user = repository.findById(id)
    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    repository.delete(user);
}

//método para alternar o status ativo/inativo de um usuário
    public void toggleUser(Long id) {
    User user = repository.findById(id)
    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    user.setActive(!user.getActive());
    repository.save(user);
}
  
}
