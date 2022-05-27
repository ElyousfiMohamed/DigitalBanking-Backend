package ma.enset.digitalbanking.Security.Repository;

import ma.enset.digitalbanking.Security.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findAppUserByUserName(String username);
}
