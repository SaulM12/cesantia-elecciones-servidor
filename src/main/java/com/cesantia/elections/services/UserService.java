package com.cesantia.elections.services;

import com.cesantia.elections.entities.User;
import com.cesantia.elections.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String ci) throws UsernameNotFoundException {
        User user = userRepository.findByCi(ci)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().toString());

        return new org.springframework.security.core.userdetails.User(
                user.getCi(),
                user.getPassword(),
                Collections.singleton(authority)
        );
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public boolean existsByCi(String ci) {
        return userRepository.existsByCi(ci);
    }

    public Optional<User> findByCi(String ci) {
        return userRepository.findByCi(ci);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getAuthenticatedUserInfo() {
        // Obtener el username del usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar al usuario en la base de datos
        return findByCi(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Construir la respuesta

    }
}
