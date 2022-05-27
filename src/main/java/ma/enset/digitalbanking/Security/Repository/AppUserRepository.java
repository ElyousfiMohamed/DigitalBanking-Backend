package ma.enset.digitalbanking.Security.Repository;

import ma.enset.digitalbanking.Security.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findAppUserByUserName(String username);
    List<AppUser> findAll();
}
