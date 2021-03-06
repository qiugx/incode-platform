package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.audited.SomeAuditedObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.audited.SomeAuditedObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.fixture.sub.SomeAuditedObject_create3;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.fixture.sub.SomeNotAuditedObject_create3;

public class SomeAuditedObject_and_SomeNonAuditedObject_create3 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new SomeAuditedObject_create3());
        executionContext.executeChild(this, new SomeNotAuditedObject_create3());

    }

    // //////////////////////////////////////

    private SomeAuditedObject create(final String name, ExecutionContext executionContext) {
        return executionContext.add(this, someAuditedObjects.createSomeAuditedObject(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SomeAuditedObjects someAuditedObjects;

}
