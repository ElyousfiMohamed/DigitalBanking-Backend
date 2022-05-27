package ma.enset.digitalbanking.Security.Service;


import ma.enset.digitalbanking.Security.Entity.AppRole;
import ma.enset.digitalbanking.Security.Entity.AppUser;

import java.util.List;

public interface IServiceSecurity {
    AppUser saveNewUser(String username, String password);

    AppUser saveNewUser(String username, String password, String repassword);

    AppRole saveNewRole(String rolename, String Desciption);

    void addRoleToUser(String username,String rolename);

    List<AppUser> getAllUsers();

    List<AppRole> getAllRoles();

    void delRoleFromUser(String username, String rolename);

    AppUser loadByUserName(String username);
    AppRole loadByRoleName(String rolename);
}
