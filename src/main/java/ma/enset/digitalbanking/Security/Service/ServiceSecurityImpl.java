package ma.enset.digitalbanking.Security.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.digitalbanking.Security.Entity.AppRole;
import ma.enset.digitalbanking.Security.Entity.AppUser;
import ma.enset.digitalbanking.Security.Repository.AppRoleRepository;
import ma.enset.digitalbanking.Security.Repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Slf4j
@AllArgsConstructor // Injection par constructeur
@Transactional
public class ServiceSecurityImpl implements IServiceSecurity {
    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String verifyPassword) {
        if (!password.equals(verifyPassword)) {
            throw new RuntimeException("password does not match");
        }

        String passwordHashed = passwordEncoder.encode(password);

        AppUser appUser = new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUserName(username);
        appUser.setPassword(passwordHashed);
        appUser.setActive(true);
        appUserRepository.save(appUser);

        return appUser;
    }

    @Override
    public AppRole saveNewRole(String rolename, String desciption) {
        AppRole appRole = appRoleRepository.findAppRoleByRoleName(rolename);

        if (appRole == null) {
            appRole = new AppRole();
            appRole.setRoleName(rolename);
            appRole.setDescription(desciption);
            appRoleRepository.save(appRole);
        } else {
            throw new RuntimeException("Role " + rolename + " Already exist !!!");
        }

        return appRole;
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        AppUser appUser = appUserRepository.findAppUserByUserName(username);
        AppRole appRole = appRoleRepository.findAppRoleByRoleName(rolename);
        if (appRole == null)
            throw new RuntimeException("role not found !!!");

        if (appUser == null)
            throw new RuntimeException("user not found !!!");

        appUser.getAppRoles().add(appRole);

    }

    @Override
    public void delRoleFromUser(String username, String rolename) {
        AppUser appUser = appUserRepository.findAppUserByUserName(username);
        AppRole appRole = appRoleRepository.findAppRoleByRoleName(rolename);

        if (appRole == null)
            throw new RuntimeException("role not found !!!");

        if (appUser == null)
            throw new RuntimeException("user not found !!!");

        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public AppUser loadByUserName(String username) {
        return appUserRepository.findAppUserByUserName(username);
    }

    @Override
    public AppRole loadByRoleName(String rolename) {
        return appRoleRepository.findAppRoleByRoleName(rolename);
    }
}

