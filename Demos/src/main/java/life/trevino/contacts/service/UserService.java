package life.trevino.contacts.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<? extends GrantedAuthority> authorities = new ArrayList<>();
        String pseudoPwd = String.format("%s%s", username, "two");
        User user = new User(username, pseudoPwd, authorities);
        return user;
    }
}
