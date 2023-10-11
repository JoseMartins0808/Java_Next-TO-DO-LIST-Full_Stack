package br.com.josemartins.todolist.filters;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.josemartins.todolist.users.IUserRepository;
import br.com.josemartins.todolist.users.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    private IUserRepository userRepository;

    public FilterTaskAuth (IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorization = request.getHeader("Authorization");

        final String authEncoded = authorization.substring(6);

        final byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

        final String authString = new String(authDecoded);

        final String[] credentials = authString.split(":");

        final String username = credentials[0];

        final String password = credentials[1];

        final UserModel userFound = this.userRepository.findByUsername(username);
        
        if (userFound == null) {
            response.sendError(401, "User not found");

        } else {
            final boolean verifyPass = BCrypt.verifyer().verify(password.toCharArray(), userFound.getPassword()).verified;
            
            if(!verifyPass) {
                response.sendError(401, "User not found");
            }else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
