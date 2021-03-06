package org.incode.domainapp.extended.integtests.spi.security.tests.example;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.extended.integtests.spi.security.SecurityModuleIntegTestAbstract;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.nontenanted.NonTenantedEntities;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.nontenanted.NonTenantedEntity;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.SecurityModuleAppTearDown;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example.nontenanted.sub.NonTenantedEntity_createBar;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example.nontenanted.sub.NonTenantedEntity_createBaz;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example.nontenanted.sub.NonTenantedEntity_createBip;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example.nontenanted.sub.NonTenantedEntity_createBop;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SecurityModuleAppEntity_IntegTest extends SecurityModuleIntegTestAbstract {

    NonTenantedEntity entity;

    @Before
    public void setUpData() throws Exception {
        runFixtureScript(
                new SecurityModuleAppTearDown(),
                new NonTenantedEntity_createBip(),
                new NonTenantedEntity_createBar(),
                new NonTenantedEntity_createBaz(),
                new NonTenantedEntity_createBop()
                );
    }

    @Inject
    NonTenantedEntities exampleNonTenantedEntities;

    @Inject
    IsisJdoSupport isisJdoSupport;

    @Before
    public void setUp() throws Exception {
        final List<NonTenantedEntity> all = wrap(exampleNonTenantedEntities).listAllNonTenantedEntities();
        assertThat(all.size(), is(4));

        entity = all.get(0);
    }

    public static class TODO extends SecurityModuleAppEntity_IntegTest {

        @Test
        public void TODO() throws Exception {

        }

    }


}