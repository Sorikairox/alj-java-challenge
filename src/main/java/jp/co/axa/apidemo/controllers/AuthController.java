package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.dto.AuthenticationRequest;
import jp.co.axa.apidemo.dto.AuthenticationResponse;
import jp.co.axa.apidemo.services.HardCodedUserService;
import jp.co.axa.apidemo.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private HardCodedUserService hardCodedUserService;
    private JwtService jwtService;

    public AuthController(@Autowired AuthenticationManager manager,
                          @Autowired HardCodedUserService userService,
                          @Autowired JwtService jwtService) {
        this.authenticationManager = manager;
        this.hardCodedUserService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            final UserDetails userDetails = this.hardCodedUserService.loadUserByUsername(authRequest.getUsername());
            final String jwt = this.jwtService.generateTokenFromUserDetails(userDetails);
            return new AuthenticationResponse(jwt);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad credentials");
        }
    }
}
