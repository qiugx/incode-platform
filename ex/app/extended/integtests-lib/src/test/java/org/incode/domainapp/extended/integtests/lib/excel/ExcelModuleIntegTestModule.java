package org.incode.domainapp.extended.integtests.lib.excel;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.excel.ExcelModule;
import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel.FixturesModuleLibExcelSubmodule;

@XmlRootElement(name = "module")
public class ExcelModuleIntegTestModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new ExcelModule(),
                new FixturesModuleLibExcelSubmodule(),
                new FakeDataModule()
        );
    }
}
