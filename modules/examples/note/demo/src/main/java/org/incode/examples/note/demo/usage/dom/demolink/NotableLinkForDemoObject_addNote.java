package org.incode.examples.note.demo.usage.dom.demolink;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.examples.note.demo.shared.demo.dom.DemoObject;
import org.incode.example.note.dom.impl.note.T_addNote;

@Mixin
public class NotableLinkForDemoObject_addNote extends T_addNote<DemoObject> {
    public NotableLinkForDemoObject_addNote(final DemoObject notable) {
        super(notable);
    }
}