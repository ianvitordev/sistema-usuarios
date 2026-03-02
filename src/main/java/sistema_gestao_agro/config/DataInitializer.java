package sistema_gestao_agro.config;

import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import sistema_gestao_agro.model.User;
import sistema_gestao_agro.repository.UserRepository;

@Configuration// Esta classe pode ser usada para inicializar dados no banco de dados, como criar um usuário admin padrão
@RequiredArgsConstructor// @RequiredArgsConstructor é uma anotação do Lombok que gera um construtor com argumentos para todos os campos finais (final) da classe
public class DataInitializer {
    

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    
    @Bean
public CommandLineRunner initAdmin() {
    return args -> {

        if (userRepository.findByEmail("admin@email.com").isEmpty()) {

            User admin = new User();
            admin.setName("Administrador");
            admin.setEmail("admin@email.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(User.Role.ADMIN);   
            admin.setActive(true);            

            userRepository.save(admin);
        }
    };
}
}
