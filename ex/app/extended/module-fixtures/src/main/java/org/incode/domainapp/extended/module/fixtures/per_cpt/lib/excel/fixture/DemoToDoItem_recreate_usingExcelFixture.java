package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.todo.fixture.DemoToDoItem_tearDown2;

public class DemoToDoItem_recreate_usingExcelFixture extends FixtureScript {

    private final String user;

    public DemoToDoItem_recreate_usingExcelFixture() {
        this(null);
    }

    public DemoToDoItem_recreate_usingExcelFixture(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null ? this.user : getContainer().getUser().getName();

        executionContext.executeChild(this, new DemoToDoItem_tearDown2(ownedBy));
        executionContext.executeChild(this, new DemoToDoItem_create_usingExcelFixture(ownedBy));

        getContainer().flush();
    }

}
