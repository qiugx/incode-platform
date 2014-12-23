/*
 *  Copyright 2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.security.seed.scripts;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import com.google.common.collect.Lists;
import org.isisaddons.module.security.dom.password.PasswordEncryptionService;
import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoles;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancies;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUsers;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.value.Password;

public class AbstractUserAndRolesFixtureScript extends FixtureScript {

    private final String username;
    private final String password;
    private final String tenancyPath;
    private final AccountType accountType;
    private final List<String> roleNames;

    public AbstractUserAndRolesFixtureScript(
            final String username,
            final String password,
            final AccountType accountType, 
            final List<String> roleNames) {
        this(username, password, null, accountType, roleNames);
    }

    public AbstractUserAndRolesFixtureScript(
            final String username,
            final String password,
            final String tenancyPath,
            final AccountType accountType,
            final List<String> roleNames) {
        this.username = username;
        this.password = password;
        this.tenancyPath = tenancyPath;
        this.accountType = accountType;
        this.roleNames = Collections.unmodifiableList(Lists.newArrayList(roleNames));
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        // create user if does not exist, and assign to the role
        applicationUser = applicationUsers.findUserByUsername(username);
        if(applicationUser == null) {
            final boolean enabled = true;
            switch (accountType) {
                case DELEGATED:
                    applicationUser = applicationUsers.newDelegateUser(username, null , enabled);
                    break;
                case LOCAL:
                    final Password pwd = new Password(password);
                    applicationUser = applicationUsers.newLocalUser(username, pwd, pwd, null, enabled);
            }

            // update tenancy (repository checks for null)
            final ApplicationTenancy applicationTenancy = applicationTenancies.findTenancyByPath(tenancyPath);
            applicationUser.setTenancy(applicationTenancy);

            for (String roleName : roleNames) {
                ApplicationRole securityAdminRole = applicationRoles.findRoleByName(roleName);
                applicationUser.addRole(securityAdminRole);
            }
        }
    }

    private ApplicationUser applicationUser;

    /**
     * The {@link org.isisaddons.module.security.dom.user.ApplicationUser} updated/created by the fixture.
     */
    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    //region  >  (injected)
    @Inject
    ApplicationUsers applicationUsers;
    @Inject
    ApplicationRoles applicationRoles;
    @Inject
    ApplicationTenancies applicationTenancies;
    @Inject
    PasswordEncryptionService passwordEncryptionService;
    //endregion

}
