package com.truper.test.service.auth;

import com.truper.test.common.exception.ItemNotFoundException;
import com.truper.test.dto.request.auth.AuthRequest;
import com.truper.test.dto.response.AuthResponse;
import com.truper.test.model.entity.UserEntity;
import com.truper.test.repository.IUserRepository;
import com.truper.test.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IReadAuthService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        String email = request.email();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Usuario no encontrado con el email: " + email));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.password()
                )
        );
        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(
                jwtToken,
                jwtService.getExpirationTime()
        );
    }
}
