package sistema_gestao_agro;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import sistema_gestao_agro.dto.UserDTO;
import sistema_gestao_agro.dto.UserResponseDTO;
import sistema_gestao_agro.model.User;
import sistema_gestao_agro.repository.UserRepository;
import sistema_gestao_agro.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    @Test
    void deveCriarUsuarioComSucesso() {

        UserDTO dto = new UserDTO();
        dto.setName("Ian");
        dto.setEmail("ian@email.com");
        dto.setPassword("123");
        dto.setRole(User.Role.USER);

        User userSalvo = new User();
        userSalvo.setName("Ian");
        userSalvo.setEmail("ian@email.com");
        userSalvo.setRole(User.Role.USER);
        userSalvo.setActive(true);
        userSalvo.setPassword("senhaCriptografada");

        when(passwordEncoder.encode(any())).thenReturn("senhaCriptografada");
        when(repository.save(any(User.class))).thenReturn(userSalvo);

        UserResponseDTO resultado = service.criar(dto);

        assertNotNull(resultado);
        verify(repository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("123");
    }

    @Test
    void deveListarUsuariosComSucesso() {
        service.listar();
        verify(repository, times(1)).findAll();
}

    @Test
    void deveDeletarUsuarioComSucesso() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(repository.findById(userId)).thenReturn(java.util.Optional.of(user));

        service.deletar(userId);

        verify(repository, times(1)).findById(userId);
        verify(repository, times(1)).delete(user);

}


}
