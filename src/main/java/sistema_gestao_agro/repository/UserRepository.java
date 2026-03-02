package sistema_gestao_agro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sistema_gestao_agro.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
}
