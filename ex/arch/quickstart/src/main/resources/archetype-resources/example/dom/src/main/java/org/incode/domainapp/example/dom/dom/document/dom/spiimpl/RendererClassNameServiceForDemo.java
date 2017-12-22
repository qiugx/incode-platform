#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.document.dom.spiimpl;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.example.document.dom.impl.docs.DocumentNature;
import org.incode.example.document.dom.impl.renderers.Renderer;
import org.incode.example.document.dom.services.ClassNameServiceAbstract;
import org.incode.example.document.dom.services.ClassNameViewModel;
import org.incode.example.document.dom.spi.RendererClassNameService;

@DomainService(
    nature = NatureOfService.DOMAIN
)
public class RendererClassNameServiceForDemo extends ClassNameServiceAbstract<Renderer> implements
        RendererClassNameService {

    public RendererClassNameServiceForDemo() {
        super(Renderer.class, "org.incode.example.document.fixture");
    }

    @Programmatic
    @Override
    public List<ClassNameViewModel> renderClassNamesFor(
            final DocumentNature inputNature, final DocumentNature outputNature) {
        if(inputNature == null || outputNature == null){
            return Lists.newArrayList();
        }
        return classNames(x -> inputNature.canActAsInputTo(x) && outputNature.canActAsOutputTo(x));
    }
    @Programmatic
    @Override
    public Class<Renderer> asClass(final String className) {
        return super.asClass(className);
    }
}
