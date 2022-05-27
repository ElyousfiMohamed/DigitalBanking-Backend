package ma.enset.digitalbanking.Security.Service;


import ma.enset.digitalbanking.Security.Entity.AppRole;
import ma.enset.digitalbanking.Security.Entity.AppUser;

public interface IServiceSecurity {
    AppUser saveNewUser(String username, String password, String verifyPassword);
    AppRole saveNewRole(String rolename, String Desciption);

    void addRoleToUser(String username,String rolename);
    void delRoleFromUser(String username,String rolename);

    AppUser loadByUserName(String username);
    AppRole loadByRoleName(String rolename);
}
