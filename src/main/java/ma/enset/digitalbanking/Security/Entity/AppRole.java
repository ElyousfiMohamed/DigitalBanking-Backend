package ma.enset.digitalbanking.Security.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 100)
    private Long id;
    private String description;
    @Column(unique = true, length = 20)
    private  String roleName;

    @ManyToMany(mappedBy = "appRoles",fetch = FetchType.EAGER)
    private List<AppUser> appUsers = new ArrayList<>();
}
