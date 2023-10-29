package br.com.lucasalves.app.services;

import br.com.lucasalves.app.controllers.PersonController;
import br.com.lucasalves.app.data.vo.PersonVO;
import br.com.lucasalves.app.exceptions.ResourceNotFoundException;
import br.com.lucasalves.app.mapper.DozerMapper;
import br.com.lucasalves.app.model.Person;
import br.com.lucasalves.app.repositories.PersonRespository;
import br.com.lucasalves.app.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService implements UserDetailsService {
    private Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name: " + username);
        var user = repository.findByUsername(username);

        if(user == null) {
            logger.info("User not found");
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        return user;
    }




}