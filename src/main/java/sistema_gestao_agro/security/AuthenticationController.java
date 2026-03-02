package sistema_gestao_agro.security;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import sistema_gestao_agro.dto.LoginDTO;
import sistema_gestao_agro.dto.UserDTO;
import sistema_gestao_agro.dto.UserResponseDTO;
import sistema_gestao_agro.model.User;
import sistema_gestao_agro.repository.UserRepository;
import sistema_gestao_agro.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService service;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {

        System.out.println("Entrou no login");
        System.out.println(dto.getEmail());

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        User user = userRepository.findByEmail(authentication.getName()).get();
        String token = jwtService.generateToken(
        user.getEmail(),
        user.getRole().name()
);

        return ResponseEntity.ok(token);
    }

@PreAuthorize("hasRole('ADMIN')")
@PostMapping
public ResponseEntity<UserResponseDTO> criar(@RequestBody UserDTO dto) {

    UserResponseDTO response = service.criar(dto);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
}


@PreAuthorize("hasRole('ADMIN')")
@PatchMapping("/users/{id}/active")
public ResponseEntity<String> toggleUser(@PathVariable Long id) {

    service.toggleUser(id);

    return ResponseEntity.ok("Status alterado com sucesso");
}



@PreAuthorize("hasRole('ADMIN')")
@PatchMapping("/users/{id}/deactivate")
public ResponseEntity<String> deactivate(@PathVariable Long id) {

    User user = userRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    user.setActive(false);
    userRepository.save(user);

    return ResponseEntity.ok("Usuário desativado");
}


}