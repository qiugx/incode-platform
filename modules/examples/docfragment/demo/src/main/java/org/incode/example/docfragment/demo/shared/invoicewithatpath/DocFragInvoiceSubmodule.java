package org.incode.example.docfragment.demo.shared.invoicewithatpath;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.docfragment.demo.shared.invoicewithatpath.dom.DocFragInvoice;

@XmlRootElement(name = "module")
public class DocFragInvoiceSubmodule extends ModuleAbstract {

    @Override
    public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override
            protected void execute(final FixtureScript.ExecutionContext executionContext) {
                deleteFrom(DocFragInvoice.class);
            }
        };
    }

}
