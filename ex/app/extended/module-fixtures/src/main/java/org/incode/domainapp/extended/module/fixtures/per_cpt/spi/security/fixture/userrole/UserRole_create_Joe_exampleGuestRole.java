package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.userrole;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleGuest;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.users.ApplicationUser_create_Joe;

public class UserRole_create_Joe_exampleGuestRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Joe_exampleGuestRole() {
        super(ApplicationUser_create_Joe.USER_NAME, RoleAndPermissions_create_exampleGuest.ROLE_NAME);
    }
}
