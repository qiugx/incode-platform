package org.incode.domainapp.example.dom.demo.dom.other;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleDemo.OtherObjectMenu"
)
@DomainServiceLayout(
        named = "Other Objects",
        menuOrder = "11"
)
public class OtherObjectMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<OtherObject> listAll() {
        return repositoryService.allInstances(OtherObject.class);
    }



    @MemberOrder(sequence = "2")
    public OtherObject create(final String name) {
        final OtherObject obj = new OtherObject(name);
        repositoryService.persist(obj);
        return obj;
    }


    @javax.inject.Inject
    RepositoryService repositoryService;

}