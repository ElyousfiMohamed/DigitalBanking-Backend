package ma.enset.digitalbanking.Security.Repository;

import ma.enset.digitalbanking.Security.Entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findAppRoleByRoleName(String rolename);
}
