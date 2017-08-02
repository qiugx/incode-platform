package org.incode.module.commchannel.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.wicket.gmap3.cpt.applib.Gmap3ApplibModule;
import org.isisaddons.wicket.gmap3.cpt.ui.Gmap3UiModule;

import org.incode.module.commchannel.dom.CommChannelModule;

import domainapp.modules.exampledom.module.commchannel.ExampleDomModuleCommChannelModule;

/**
 * Bootstrap the application.
 */
public class CommChannelModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.withModules(
            CommChannelModule.class, // dom module
            ExampleDomModuleCommChannelModule.class,
            Gmap3ApplibModule.class,
            Gmap3UiModule.class
    );

    public CommChannelModuleAppManifest() {
        super(BUILDER);
    }

}
