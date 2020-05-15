package com.alten.skillsmanagement;

import com.alten.skillsmanagement.model.*;
import com.alten.skillsmanagement.repository.AppRoleRepository;
import com.alten.skillsmanagement.repository.AppUserRepository;
import com.alten.skillsmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SkillsManagementApplication implements CommandLineRunner {

	private final AppRoleRepository appRoleRepository;
	private final AppUserRepository appUserRepository;
	private final AccountService accountService;

	@Autowired
	public SkillsManagementApplication(AppRoleRepository appRoleRepository,
									   AppUserRepository appUserRepository,
									   AccountService accountService) {
		this.appRoleRepository = appRoleRepository;
		this.appUserRepository = appUserRepository;
		this.accountService = accountService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SkillsManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//AppUser appUser = new AppUser();

		/*appUser.setFirstName("Leo");
		appUser.setLastName("Messi");
		appUser.setUsername("lmessi");
		appUser.setPassword("1234");
		appUser.setAddress("the address");
		appUser.setCin("IGUGHJ");
		appUser.setEmail("email@gmail.com");
		//AppRole appRole = new AppRole();
		//appRole.setRoleName(AppRoleName.CONSULTANT);
		//appRoleRepository.save(appRole);
		accountService.saveUser(appUser);
		accountService.addRoleToUser("lmessi", AppRoleName.MANAGER);

		//AppPermission appPermission = new AppPermission();
		//appPermission.setAppPermissionName(AppPermissionName.SKILL_READ);

		//appUser.setRoles(new HashSet<>((Collection<? extends AppRole>) appRole));
		//appUserRepository.save(appUser);*/
	}
}
