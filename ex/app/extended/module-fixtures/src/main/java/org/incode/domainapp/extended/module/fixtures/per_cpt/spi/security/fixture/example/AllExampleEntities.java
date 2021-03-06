package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.nontenanted.NonTenantedEntities;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example.nontenanted.NonTenantedEntity_create4;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example.tenanted.TenantedEntity_create4;

public class AllExampleEntities extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new NonTenantedEntity_create4());
        executionContext.executeChild(this, new TenantedEntity_create4());

    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private NonTenantedEntities exampleNonTenantedEntities;

}
