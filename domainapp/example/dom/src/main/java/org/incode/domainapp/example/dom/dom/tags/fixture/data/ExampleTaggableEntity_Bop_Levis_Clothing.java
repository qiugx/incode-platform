package org.incode.domainapp.example.dom.dom.tags.fixture.data;

public class ExampleTaggableEntity_Bop_Levis_Clothing extends AbstractEntityFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bop", "Levi's", "Clothing", executionContext);
    }

}