package ma.enset.digitalbanking.Security.Repository;

import ma.enset.digitalbanking.Security.Entity.AppRole;
import ma.enset.digitalbanking.Security.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findAppRoleByRoleName(String rolename);
    List<AppRole> findAll();

}
