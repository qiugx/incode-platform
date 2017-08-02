package org.isisaddons.module.stringinterpolator.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;

import domainapp.modules.exampledom.lib.stringinterpolator.ExampleDomLibStringInterpolatorModule;

public class StringInterpolatorLibAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.withModules(
            StringInterpolatorModule.class,
            ExampleDomLibStringInterpolatorModule.class
    );

    public StringInterpolatorLibAppManifest() {
        super(BUILDER);
    }

}
