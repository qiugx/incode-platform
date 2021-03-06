#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package domainapp.appdefn;

import org.apache.isis.applib.AppManifestAbstract2;

public class DomainAppAppManifestWithFlywayEnabledForSqlServer extends AppManifestAbstract2 {

	public DomainAppAppManifestWithFlywayEnabledForSqlServer() {
		super(DomainAppAppManifest.BUILDER
		.withConfigurationPropertiesFile(
                    DomainAppAppManifestWithFlywayEnabledForSqlServer.class,
                    "persistor-sqlserver-quickstart_db.properties")
		);
	}

}
