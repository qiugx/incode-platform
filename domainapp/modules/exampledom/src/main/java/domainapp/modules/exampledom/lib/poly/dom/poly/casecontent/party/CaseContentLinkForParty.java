package domainapp.modules.exampledom.lib.poly.dom.poly.casecontent.party;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import domainapp.modules.exampledom.lib.poly.dom.demoparty.Party;
import domainapp.modules.exampledom.lib.poly.dom.poly.casecontent.CaseContent;
import domainapp.modules.exampledom.lib.poly.dom.poly.casecontent.CaseContentLink;

@javax.jdo.annotations.PersistenceCapable()
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        objectType = "party.CaseContentLinkForParty"
)
public class CaseContentLinkForParty extends CaseContentLink {

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class InstantiationSubscriber extends AbstractSubscriber {
        @Programmatic
        @Subscribe
        public void on(final InstantiateEvent ev) {
            if(ev.getPolymorphicReference() instanceof Party) {
                ev.setSubtype(CaseContentLinkForParty.class);
            }
        }
    }

    @Override
    public void setPolymorphicReference(final CaseContent polymorphicReference) {
        super.setPolymorphicReference(polymorphicReference);
        setParty((Party) polymorphicReference);
    }

    //region > party (property)
    private Party party;

    @Column(
            allowsNull = "false",
            name = "party_id"
    )
    @MemberOrder(sequence = "1")
    public Party getParty() {
        return party;
    }

    public void setParty(final Party party) {
        this.party = party;
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private BookmarkService bookmarkService;
    //endregion

}