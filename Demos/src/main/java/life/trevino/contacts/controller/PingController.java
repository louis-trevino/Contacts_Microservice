package life.trevino.contacts.controller;

import life.trevino.contacts.dom.AuthenticationRequest;
import life.trevino.contacts.dom.AuthenticationResponse;
import life.trevino.contacts.dom.Contact;
import life.trevino.contacts.service.JwtUtil;
import life.trevino.contacts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class PingController {

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    UserService userDetailsService;

    @RequestMapping(value="/ping", method= RequestMethod.GET)
    public ResponseEntity<String> ping() {
        ResponseEntity<String> re = new ResponseEntity<>("OK", HttpStatus.OK);
        return re;
    }

    @RequestMapping(value="/auth", method= RequestMethod.POST)
    public ResponseEntity authPost(@RequestBody AuthenticationRequest authenticationRequest)
        throws Exception
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    @RequestMapping(value="/auth", method= RequestMethod.GET)
    public ResponseEntity authGet(HttpServletRequest request)
            throws Exception
    {
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, pwd)
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
