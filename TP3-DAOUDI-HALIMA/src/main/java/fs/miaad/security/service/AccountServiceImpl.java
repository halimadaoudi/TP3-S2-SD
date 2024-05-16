package fs.miaad.security.service;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import fs.miaad.security.entities.AppRole;
import fs.miaad.security.entities.AppUser;
import fs.miaad.security.repo.AppRoleRepository;
import fs.miaad.security.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service @Transactional @AllArgsConstructor
public class AccountServiceImpl implements AccountService
{
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword)
    {
        AppUser appUser=appUserRepository.findByUsername(username);
        if(appUser!=null) throw new RuntimeException("ce utilisateur existe déja");
        if(!password.equals(confirmPassword)) throw new RuntimeException("mot de passe incorrecte");
        appUser=AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username (username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(String role)
    {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if(appRole!=null) throw new RuntimeException("Ce role existe déja");
        appRole = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role)
    {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().add(appRole);
        //appUserRepository.save(appUser);
    }

    @Override
    public void removeRoleFromUser(String username, String role)
    {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);
    }
    @Override
    public AppUser loadUserByUsername(String username)
    {      return appUserRepository.findByUsername(username);  }
}
