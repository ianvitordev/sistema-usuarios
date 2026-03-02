package sistema_gestao_agro.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import sistema_gestao_agro.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
//classe para carregar os detalhes do usuário a partir do banco de dados usando o email como identificador
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    // método para carregar um usuário pelo email e retornar um UserDetails com as informações do usuário e suas autoridades
    public UserDetails loadUserByUsername(String email) {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // garante que role nunca será null e nunca terá ROLE_ duplicado
        String role = user.getRole() != null ? user.getRole().name() : "USER";
        
        // Cria um UserDetails com as informações do usuário e a role formatada como ROLE_{ROLE}
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getActive(), // ativo
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }
}
