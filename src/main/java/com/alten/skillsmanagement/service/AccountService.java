package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.dto.AppUserDto;
import com.alten.skillsmanagement.dto.AppUserUpdateDto;
import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.AppRole;
import com.alten.skillsmanagement.model.AppRoleName;
import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.model.Position;
import com.alten.skillsmanagement.repository.AppRoleRepository;
import com.alten.skillsmanagement.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.alten.skillsmanagement.model.AppRoleName.CONSULTANT;
import static com.alten.skillsmanagement.model.AppRoleName.MANAGER;

@Service
@Transactional
public class AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PositionService positionService;

    @Autowired
    public AccountService(PasswordEncoder passwordEncoder,
                          AppUserRepository appUserRepository,
                          AppRoleRepository appRoleRepository,
                          PositionService positionService) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.positionService = positionService;
    }

    public AppUser createUser(AppUserDto appUserDto) {
        AppUser appUser = validateUser(appUserDto);
        saveUser(appUser);
        addRoleToUser(appUser.getUsername(), CONSULTANT);
        return appUser;
    }

    public AppUser saveUser(AppUser appUser) {
        String hashedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(hashedPassword);
        return appUserRepository.save(appUser);
    }

    public AppUser updateUser(Long id, AppUserUpdateDto appUserUpdateDto) {
        AppUser appUser = getUser(id);
        appUser.setFirstName(appUserUpdateDto.getFirstName());
        appUser.setLastName(appUserUpdateDto.getLastName());
        appUser.setAddress(appUserUpdateDto.getAddress());
        appUser.setCin(appUserUpdateDto.getCin());
        appUser.setEmail(appUserUpdateDto.getEmail());
        appUser.setMobile(appUserUpdateDto.getMobile());
        appUser.setGender(appUserUpdateDto.getGender());
        appUser.setDiploma(appUserUpdateDto.getDiploma());
        appUser.setQuote(appUserUpdateDto.getQuote());

        return appUserRepository.save(appUser);
    }

    public void deleteUser(Long id) {
        AppUser appUser = getUser(id);
        appUserRepository.delete(appUser);
    }


    private AppUser validateUser(AppUserDto appUserDto) {

        String email = appUserDto.getEmail();
        Boolean existsByEmail = appUserRepository.existsByEmail(email);
        if (Boolean.TRUE.equals(existsByEmail)) throw new RuntimeException(String.format("Email %s already exists.", email));

        String password = appUserDto.getPassword();
        String confirmPassword = appUserDto.getConfirmPassword();
        if (!password.equals(confirmPassword)) throw new RuntimeException("You should confirm your password!");

        String firstName = appUserDto.getFirstName();
        String lastName = appUserDto.getLastName();
        String username = String.format("%c%s", firstName.charAt(0), lastName);

        Boolean usernameTaken = usernameTaken(username);
        if (Boolean.TRUE.equals(usernameTaken)) {
            int i = 1;
            do {
                username = String.format("%c%s%d", firstName.charAt(0), lastName, i);
                usernameTaken = usernameTaken(username);
                i++;
            } while (Boolean.TRUE.equals(usernameTaken));
        }
        String address = appUserDto.getAddress();
        String mobile = appUserDto.getMobile();
        String cin = appUserDto.getCin();
        String gender = appUserDto.getGender();
        String diploma = appUserDto.getDiploma();
        String quote = appUserDto.getQuote();

        //mapping
        AppUser appUser = new AppUser();
        appUser.setFirstName(firstName);
        appUser.setLastName(lastName);
        appUser.setEmail(email);
        appUser.setUsername(username);
        appUser.setPassword(password);
        appUser.setCin(cin);
        appUser.setAddress(address);
        appUser.setMobile(mobile);
        appUser.setGender(gender);
        appUser.setDiploma(diploma);
        appUser.setQuote(quote);

        return appUser;
    }


    public AppUser findUserByUsername(String username) {
        return appUserRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username: %s not found.", username)));
    }

    public AppUser getUser(Long appUserId) {
        return appUserRepository.findById(appUserId)
                .orElseThrow(() -> new ResourceNotFoundException("AppUser", "id", appUserId));
    }

    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }



    public Boolean usernameTaken(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public void addRoleToUser(String username, AppRoleName roleName) {
        AppUser appUser = findUserByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getRoles().add(appRole);
    }

    public void affectPositionToUser(Integer positionId, Long appUserId) {
        Position position = positionService.getPosition(positionId);
        AppUser appUser = getUser(appUserId);
        appUser.setPosition(position);
    }

    public AppUser makeManager(Long userId) {
        AppUser appUser = getUser(userId);
        appUser.getRoles().clear();
        AppRole managerRole = appRoleRepository.findByRoleName(MANAGER);
        appUser.getRoles().add(managerRole);
        return appUserRepository.save(appUser);
    }

    public String getUsernameOfAuthenticatedUser(@AuthenticationPrincipal Object principal) {
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        if (username==null || username.isEmpty()) {
            throw new RuntimeException("username is null or empty");
        }
        return username;
    }


}
