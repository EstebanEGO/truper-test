package com.truper.test.service.auth;

import com.truper.test.dto.request.auth.AuthRequest;
import com.truper.test.dto.response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface IReadAuthService {
    AuthResponse authenticate(AuthRequest request);
}
