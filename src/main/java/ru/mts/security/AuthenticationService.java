package ru.mts.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mts.model.MyUser;
import ru.mts.repository.MyUserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        MyUser user = new MyUser(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );

        myUserRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        MyUser user = myUserRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }
}
