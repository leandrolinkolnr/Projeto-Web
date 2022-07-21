package com.progweb.DiarioEscolar.services;

import java.util.Optional;

import com.progweb.DiarioEscolar.domain.Pessoa;
import com.progweb.DiarioEscolar.domain.User;
import com.progweb.DiarioEscolar.repositories.RepositoryPessoa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

@Autowired
private RepositoryPessoa pessoaRepository;

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Pessoa> user = pessoaRepository.findByNome(username);
    if(user.isPresent()){
        return new User(user.get().getId(), user.get().getNome(), user.get().getSenha(), user.get().getAuthority());
    }
    throw new UsernameNotFoundException(username);  
    }
}

