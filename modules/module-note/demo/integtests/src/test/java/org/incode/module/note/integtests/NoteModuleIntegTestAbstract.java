package org.incode.module.note.integtests;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.module.note.app.NoteModuleAppManifest;
import org.incode.module.note.dom.impl.note.T_addNote;
import org.incode.module.note.dom.impl.note.T_notes;
import org.incode.module.note.dom.impl.note.T_removeNote;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.impl.note.Note_changeDate;
import org.incode.module.note.dom.impl.note.Note_changeNotes;
import org.incode.module.note.dom.impl.note.Note_remove;
import domainapp.modules.exampledom.module.note.dom.demolink.NotableLinkForDemoObject;
import domainapp.modules.exampledom.module.note.dom.demo.NoteDemoObject;

public abstract class NoteModuleIntegTestAbstract extends IntegrationTestAbstract {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    protected FixtureScripts fixtureScripts;

    @Inject
    protected FakeDataService fakeData;


    protected T_addNote mixinAddNote(final NoteDemoObject notable) {
        return mixin(NotableLinkForDemoObject._addNote.class, notable);
    }
    protected T_removeNote mixinRemoveNote(final NoteDemoObject notable) {
        return mixin(NotableLinkForDemoObject._removeNote.class, notable);
    }

    protected T_notes mixinNotes(final NoteDemoObject notable) {
        return mixin(NotableLinkForDemoObject._notes.class, notable);
    }

    protected Note_changeDate mixinChangeDate(final Note note) {
        return mixin(Note_changeDate.class, note);
    }
    protected Note_changeNotes mixinChangeNotes(final Note note) {
        return mixin(Note_changeNotes.class, note);
    }
    protected Note_remove mixinRemove(final Note note) {
        return mixin(Note_remove.class, note);
    }

    protected static <T> List<T> asList(final Iterable<T> iterable) {
        return Lists.newArrayList(iterable);
    }

    @BeforeClass
    public static void initClass() {
        org.apache.log4j.PropertyConfigurator.configure("logging-integtest.properties");

        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(org.apache.log4j.Level.INFO)
                    .with(new NoteModuleAppManifest()
                            .withModules(NoteModuleIntegTestAbstract.class, FakeDataModule.class))
                    .with(new IsisConfigurationForJdoIntegTests())
                    .build()
                    .setUpSystem();
            IsisSystemForTest.set(isft);
        }

        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }
}