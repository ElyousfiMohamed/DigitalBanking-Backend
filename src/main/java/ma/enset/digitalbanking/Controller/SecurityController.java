package ma.enset.digitalbanking.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import ma.enset.digitalbanking.Security.Entity.AppRole;
import ma.enset.digitalbanking.Security.Entity.AppUser;
import ma.enset.digitalbanking.Security.Entity.JWTUtil;
import ma.enset.digitalbanking.Security.Service.IServiceSecurity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class SecurityController {
    private IServiceSecurity serviceSecurity;

    @GetMapping("/users")
    public List<AppUser> appUsers() {
        return serviceSecurity.getAllUsers();
    }

    @PostMapping("/users")
    public AppUser addUser(@RequestBody AppUser appUser) {
        return serviceSecurity.saveNewUser(appUser.getUserName(), appUser.getPassword());
    }

    @PostMapping("/roles")
    public AppRole addRole(@RequestBody AppRole approle) {
        return serviceSecurity.saveNewRole(approle.getRoleName(),approle.getDescription());
    }

    @PostMapping("/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        System.out.println(roleUserForm.getUserName()+" "+roleUserForm.getRoleName());
        serviceSecurity.addRoleToUser(roleUserForm.getUserName(),roleUserForm.getRoleName());
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authToken = request.getHeader(JWTUtil.AUTH_HEADER);
        if(authToken != null && authToken.startsWith(JWTUtil.TOKEN_PREFIX)) {
            try {
                String jwt = authToken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(jwt);
                String username = decodedJWT.getSubject();
                AppUser appUser = serviceSecurity.loadByUserName(username);
                String accessToken = JWT.create()
                        .withSubject(appUser.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_ACCESS))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", appUser.getAppRoles().stream().map(s -> s.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> idToken = new HashMap<>();
                idToken.put("refreshToken",jwt);
                idToken.put("accessToken",accessToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),idToken);
            }catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    @PostMapping("/register")
    public AppUser register(@RequestBody  UserForm userForm){
        return serviceSecurity.saveNewUser(userForm.getUsername(),userForm.getPassword(),userForm.getConfirmedPassword());
    }

    @Data
    class UserForm{
        private String username;
        private String password;
        private String confirmedPassword;
    }

    @Data
    public static class RoleUserForm {
        private String userName;
        private String roleName;
    }

}
