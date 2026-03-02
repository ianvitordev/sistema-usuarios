package sistema_gestao_agro.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sistema_gestao_agro.dto.UserDTO;
import sistema_gestao_agro.dto.UserResponseDTO;
import sistema_gestao_agro.model.User;
import sistema_gestao_agro.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService service;
    
    //construtor para injetar o UserService
    public UserController(UserService service) {
        this.service = service;
    }
    
     // 🔐 Criar usuário
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponseDTO> criar(@Valid @RequestBody UserDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criar(dto));
    }

    // 🔐 Listar usuários
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    // 🔐 Buscar por ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // 🔐 Atualizar usuário
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<User> atualizar(@PathVariable Long id,
                                          @Valid @RequestBody UserDTO dto) {

        User user = service.buscarEntidadePorId(id);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        return ResponseEntity.ok(service.atualizar(user));
    }

    // 🔐 Deletar usuário
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok("Usuário deletado");
    }

}