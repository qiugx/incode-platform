package org.incode.example.tags.demo.shared.dom.demo;

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
import org.apache.isis.applib.services.xactn.TransactionService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "tagsDemoUsage.DemoTaggableEntityMenu"
)
@DomainServiceLayout(
        named = "Subdomains",
        menuOrder = "30.2"
)
public class TaggableObjectMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<TaggableObject> listAllTaggableObjects() {
        return repositoryService.allInstances(TaggableObject.class);
    }


    @MemberOrder(sequence = "2")
    public TaggableObject createTaggableEntity(
            final String name,
            final String brand,
            final String sector) {
        final TaggableObject obj = repositoryService.instantiate(TaggableObject.class);
        obj.setName(name);
        repositoryService.persistAndFlush(obj);
        obj.setBrand(brand);
        obj.setSector(sector);
        transactionService.flushTransaction();
        return obj;
    }


    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject TransactionService transactionService;


}
