package ma.enset.digitalbanking.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @Column(length = 200)
    private String id;
    private String nom;
    private String email;
    private String imageUrl;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    List<BankAccount> bankAccountList = new ArrayList<>();
}
