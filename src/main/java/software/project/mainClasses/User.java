package software.project.mainClasses;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message="first name is required")  
    private String firstName;

    @NotBlank(message="first name is required")  
    private String lastName;

    @NotBlank(message="first name is required")
      
    private String username;

    @NotBlank(message="first name is required")  
    private String email;

    @NotBlank(message="first name is required")  
    private String phone;

    @NotBlank(message="first name is required")  
    private String password;

    @Column(nullable = true)
    @Enumerated()
    private Role role;


    @Column(nullable = true, length = 64)
    private String photos;
    // @Column(nullable = true)
    // private Gender gender;



    @Column(nullable = true)
    private Location location;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Technician technicianProfile;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customerProfile;

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;
         
        return "/user-photos/" + id + "/" + photos;
    }

    public static enum Role {
        CUSTOMER,
        TECHNICIAN,
        ADMIN;

        private Role() {
        
        }
    }

    public static enum Location {
        ARADA,
        KIRKOS,
        KOFLEkARANIO,
        ADDISKETEMA,
        GULELE;

        private Location() {

        }
    }

  

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}