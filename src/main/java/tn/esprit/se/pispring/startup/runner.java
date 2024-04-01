package tn.esprit.se.pispring.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.se.pispring.Repository.RoleRepo;
import tn.esprit.se.pispring.entities.ERole;
import tn.esprit.se.pispring.entities.Role;

import java.util.List;

@Component
@Slf4j
public class runner implements CommandLineRunner {


    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {

        List<Role> roles = roleRepo.findAll();
        if (roles.isEmpty()) {
          Role roleAdmin = new Role();
          roleAdmin.setRoleName(ERole.ROLE_ADMIN);
          roleRepo.save(roleAdmin);

            Role roleUser = new Role();
            roleUser.setRoleName(ERole.ROLE_USER);
            roleRepo.save(roleUser);

            Role roleHR = new Role();
            roleHR.setRoleName(ERole.ROLE_HRE);
            roleRepo.save(roleHR);

            Role roleHRAdmin = new Role();
            roleHRAdmin.setRoleName(ERole.ROLE_HR_ADMIN);
            roleRepo.save(roleHRAdmin);

            Role roleCRM = new Role();
            roleCRM.setRoleName(ERole.ROLE_CRME);
            roleRepo.save(roleCRM);

            Role roleCRMAdmin = new Role();
            roleCRMAdmin.setRoleName(ERole.ROLE_CRM_ADMIN);
            roleRepo.save(roleCRMAdmin);

            Role roleProject = new Role();
            roleProject.setRoleName(ERole.ROLE_PROJECT);
            roleRepo.save(roleProject);

            Role roleProjectAdmin = new Role();
            roleProjectAdmin.setRoleName(ERole.ROLE_PROJECT_ADMIN);
            roleRepo.save(roleProjectAdmin);

            Role roleProduct = new Role();
            roleProduct.setRoleName(ERole.ROLE_PRODUCT);
            roleRepo.save(roleProduct);

            Role roleProductAdmin = new Role();
            roleProductAdmin.setRoleName(ERole.ROLE_PRODUCT_ADMIN);
            roleRepo.save(roleProductAdmin);


            log.info("#################################");
            log.info("#################################");
            log.info("#################################");
            log.info("#################################");
            log.info("#################################");
            log.info("#################################");
            log.info("=================================");
            log.info("DONE ADDING ROLES");
            log.info("=================================");
            log.info("#################################");
            log.info("#################################");
            log.info("#################################");
            log.info("#################################");
            log.info("#################################");
            log.info("#################################");

        }

    }
}
