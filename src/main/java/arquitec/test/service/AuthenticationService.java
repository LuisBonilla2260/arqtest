package arquitec.test.service;

import arquitec.test.dto.LoginRequestDto;
import arquitec.test.dto.LoginResponseDto;
import arquitec.test.mapper.UsuarioMapper;
import arquitec.test.model.entity.Usuario;
import arquitec.test.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioMapper usuarioMapper;
    
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        log.info("Iniciando login para usuario: {}", loginRequest.getUsername());
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        
        Usuario usuario = (Usuario) authentication.getPrincipal();
        log.info("Usuario autenticado: {} con ID: {}", usuario.getUsername(), usuario.getId());
        
        String token = jwtService.generateToken(usuario);
        log.info("Token JWT generado para usuario: {}", usuario.getUsername());
        
        LoginResponseDto response = usuarioMapper.toLoginResponseDto(usuario);
        response.setToken(token);
        response.setMensaje("Login exitoso");
        
        log.info("Response del login - UsuarioId: {}, Rol: {}, Token: {}", 
                response.getUsuarioId(), response.getRol(), response.getToken() != null ? "Generado" : "No generado");
        
        return response;
    }
}
