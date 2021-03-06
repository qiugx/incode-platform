package org.incode.domainapp.extended.integtests.spi.command.tests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.isisaddons.module.command.dom.BackgroundCommandServiceJdoRepository;
import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;

import org.incode.domainapp.extended.integtests.spi.command.CommandModuleIntegTestAbstract;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.dom.SomeCommandAnnotatedObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.dom.SomeCommandAnnotatedObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.fixture.SomeCommandAnnotatedObject_create3;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public class SomeCommandAnnotatedObjects_IntegTest extends CommandModuleIntegTestAbstract {


    @Before
    public void setUpData() throws Exception {
        runFixtureScript(new SomeCommandAnnotatedObject_create3());
    }

    @Inject
    private SomeCommandAnnotatedObjects someCommandAnnotatedObjects;

    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;

    @Inject
    BackgroundCommandServiceJdoRepository backgroundCommandServiceJdoRepository;

    @Inject
    BookmarkService bookmarkService;

    SomeCommandAnnotatedObject entity;
    Bookmark bookmark;

    @Before
    public void setUp() throws Exception {
        final List<SomeCommandAnnotatedObject> all = wrap(someCommandAnnotatedObjects).listAllSomeCommandAnnotatedObjects();
        assertThat(all.size(), is(3));

        entity = wrap(all.get(0));
        assertThat(entity.getName(), is("Foo"));

        bookmark = bookmarkService.bookmarkFor(entity);

        final List<CommandJdo> commands = commandServiceJdoRepository.findByTargetAndFromAndTo(bookmark, null, null);
        assertThat(commands, is(empty()));
    }

    public static class ChangeName extends SomeCommandAnnotatedObjects_IntegTest {

        @Ignore("currently not possible to test this,  using the wrapper, because the Command's executor is left as OTHER rather than USER")
        @Test
        public void happyCase() throws Exception {
            // when
            entity.changeName("Fizz");
            transactionService.nextTransaction();

            // then
            final List<CommandJdo> commands = commandServiceJdoRepository.findByTargetAndFromAndTo(bookmark, null, null);
            assertThat(commands.size(), is(1));

            assertThat(entity.getName(), is("Fizz"));
        }
    }

    public static class ChangeNameExplicitlyInBackground extends SomeCommandAnnotatedObjects_IntegTest {

        @Ignore("currently not possible to test this, because of error in @Query annotation referencing AuditEntryJdo and also because when using the wrapper the Command's executor is left as OTHER rather than USER")
        @Test
        public void happyCase() throws Exception {
            // when
            entity.changeNameExplicitlyInBackgroundUsingDirectAction("Changed!");
            transactionService.nextTransaction();

            // then
            final List<CommandJdo> commands = backgroundCommandServiceJdoRepository.findBackgroundCommandsNotYetStarted();
            assertThat(commands.size(), is(2)); // one for the command, one for the background command

            assertThat(entity.getName(), is("Foo")); // unchanged

            // TODO: more assertions required here.
        }
    }

    public static class ChangeNameImplicitlyInBackground extends SomeCommandAnnotatedObjects_IntegTest {

        @Ignore("currently not possible to test this, because of error in @Query annotation referencing AuditEntryJdo and also because when using the wrapper the Command's executor is left as OTHER rather than USER")
        @Test
        public void happyCase() throws Exception {
            // when
            entity.changeNameImplicitlyInBackground("Changed!");
            transactionService.nextTransaction();

            // then
            final List<CommandJdo> commands = backgroundCommandServiceJdoRepository.findBackgroundCommandsNotYetStarted();
            assertThat(commands.size(), is(1)); // one for the background command

            assertThat(entity.getName(), is("Foo")); // unchanged

            // TODO: more assertions required here.
        }
    }

    public static class ChangeNameCommandNotPersisted extends SomeCommandAnnotatedObjects_IntegTest {

        @Ignore("currently not possible to test this, because of error in @Query annotation referencing AuditEntryJdo and also because when using the wrapper the Command's executor is left as OTHER rather than USER")
        @Test
        public void happyCase() throws Exception {
            // when
            entity.changeNameCommandNotPersisted("Changed!");
            transactionService.nextTransaction();

            // then
            final List<CommandJdo> commands = backgroundCommandServiceJdoRepository.findBackgroundCommandsNotYetStarted();
            assertThat(commands.size(), is(0)); // none persisted

            assertThat(entity.getName(), is("Changed!"));
        }
    }

}