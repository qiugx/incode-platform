package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.dom.color;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.dom.SomeCommandAnnotatedObject;

@Mixin
public class SomeCommandAnnotatedObject_changeColorViaMixin {

    public static class ActionDomainEvent
            extends org.apache.isis.applib.services.eventbus.ActionDomainEvent <SomeCommandAnnotatedObject_changeColorViaMixin> {}

    private final SomeCommandAnnotatedObject someCommandAnnotatedObject;

    public SomeCommandAnnotatedObject_changeColorViaMixin(SomeCommandAnnotatedObject someCommandAnnotatedObject) {
        this.someCommandAnnotatedObject = someCommandAnnotatedObject;
    }

    @Action(domainEvent = SomeCommandAnnotatedObject_changeColorViaMixin.ActionDomainEvent.class)
    public SomeCommandAnnotatedObject $$(final SomeCommandAnnotatedObject.Colour colour) {
        someCommandAnnotatedObject.setColour(colour);
        return someCommandAnnotatedObject;
    }

    public SomeCommandAnnotatedObject.Colour default0$$() {
        return someCommandAnnotatedObject.getColour();
    }

}
