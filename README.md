# isis-module-stringinterpolator #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-stringinterpolator.png?branch=master)](https://travis-ci.org/danhaywood/isis-module-stringinterpolator)

This module, intended for use within [Apache Isis](http://isis.apache.org), provides a mechanism to interpolate string 
templates with either Isis system properties or values obtained from a domain object.

One use case for this service is in building URLs based on an object's state, parameterized
by environment (prod/test/dev etc).  These URLs could be anything; for example, to a reporting service:

    ${property['reportServerBase']}/ReportViewer.aspx?/Invoices&propertyId=${this.property.id}&dueDate=${this.dueDate}

where:

* `${property['reportServerBase']}` is an Isis system property
* `${this.property.id}` is an expression that is evaluated in the context of a domain object (`this`), returning
   `this.getProperty().getId()`
* `${this.dueDate}` similarly is an expression evaluating to `this.getDueDate()`.

Isis system properties are exposed as the `properties` map, while the target object is exposed as the `this` object.


## API ##

The module consists of a single domain service, `StringInterpolatorService`.  The main API exposed by this service is:
 
    public class StringInterpolatorService {

        // called by Isis (which passes in all Isis properties)
        @PostConstruct
        public void init(final Map<String,String> properties) { ... }

        // public API
        public String interpolate(Object domainObject, String template) { ... }
        
        ...
    }

Using this API makes `domainObject` available as `this` in the template.

The service also offers a lower-level API which allows multiple objects to be made accessible from the context:

    public class StringInterpolatorService {

        public static class Root {
            ...
            public Root(final Object context) {
                this._this = context;
            }
            public Object getThis() { return _this; }
            ...
        }

        // public API
        public String interpolate(Root root, String template) { ... }
        
        ...
    }

The `Root` class can be extended as necessary.
    
## Usage ##

The interpolation replaces each occurrence of `${...}` with its interpolated value.  The expression in within the
braces is interpreted using [OGNL](http://commons.apache.org/proper/commons-ognl/).

The examples below are adapted from the service's unit tests.

#### Property Interpolation ####

These tests only interpolate the Isis properties, and so pass in `null` for the object context:

        private StringInterpolatorService service;
        private Map<String, String> properties;
        
        @Before
        public void setUp() throws Exception {
            service = new StringInterpolatorService();
            
            properties = ImmutableMap.of(
                    "isis.asf.website.noScheme", "isis.apache.org", 
                    "isis.asf.website.documentationPage", "documentation.html");
                    
            service.init(properties);
        }
        
        @Test
        public void complex() throws Exception {
            String interpolated = service.interpolate(
                null, "http://${properties['isis.asf.website.noScheme']}/${properties['isis.asf.website.documentationPage']}#Core");
            assertThat(interpolated, is("http://isis.apache.org/documentation.html#Core"));
        }
    }

#### Object graph interpolation ####

These tests interpolate an instance of the `Customer` class, that in turn has relationships to the `Address` class:

    static class Customer {
        private String firstName;
        private String lastName;
        private Address address;
        private Address billingAddress;
        // getters and setters omitted
    }
    static class Address {
        private int houseNumber;
        private String town;
        private String postalCode;
        // getters and setters omitted
    }
    
    @Before
    public void setUp() throws Exception {
        service = new StringInterpolatorService();
        
        customer = new Customer();
        customer.setFirstName("Fred");
        
        Address address = new Address();
        address.setHouseNumber(34);
        address.setPostalCode("AB12 34DF");
        customer.setAddress(address);
                
        service.init(properties);
    }
    
    @Test
    public void simple() throws Exception {
        String interpolated = service.interpolate(customer, "${this.firstName}");
        assertThat(interpolated, is("Fred"));
    }

    @Test
    public void walkGraph() throws Exception {
        String interpolated = service.interpolate(customer, "${this.address.houseNumber}");
        assertThat(interpolated, is("34"));
    }
    
    @Test
    public void conditionals() throws Exception {
        String interpolated = service.withStrict(true).interpolate(customer, 
                "${this.firstName}"
                + "${this.lastName != null? this.lastName : ''}"
                + "${this.address != null? ' lives at ' + this.address.houseNumber + ', ' + this.address.postalCode: ''}"
                + "${this.billingAddress != null? ' , bill to ' + this.billingAddress.postTown : ''}");
        assertThat(interpolated, is("Fred lives at 34, AB12 34DF"));
    }

By default, any expression that cannot be parsed or would generate an exception (eg null pointer exception) is instead
returned unchanged in the interpolated string.

The service also provides a "strict" mode, which is useful for testing expressions:

    StringInterpolatorService service = new StringInterpolatorService().withStrict(true);
    
If enabled, then an exception is thrown instead.

#### Object graph interpolation (using the lower-level API) ####

To use the lower-level API, create a custom subclass of the `Root` class:

    final class CustomRoot extends StringInterpolatorService.Root {
        private Customer customer;
        public CustomRoot(Object context, Customer customer) {
            super(context);
            this.customer = customer;
        }
        public Customer getCustomer() {
            return customer;
        }
    }

The example above exposes the `customer` property.  This can then be used in the template:

    @Test
    public void simple() throws Exception {
        String interpolated = service.interpolate(
            new CustomRoot(null, customer), "${customer.firstName}");
        assertThat(interpolated, is("Fred"));
    }


## Maven Configuration ##

In the `pom.xml` for your "dom" module, add:
    
    <dependency>
        <groupId>org.isisaddons.module.stringinterpolator</groupId>
        <artifactId>isis-module-stringinterpolator-dom</artifactId>
        <version>x.y.z</version>
    </dependency>

where `x.y.z` currently is 1.6.0-SNAPSHOT (though the plan is to release this code into the [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-stringinterpolator-dom).

## Registering the service ##

The `StringInterpolatorService` is annotated with `@DomainService`, so `WEB-INF\isis.properties` file, add to the
`packagePrefix` key:

    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=...,\
                                                                org.isisaddons.module.stringinterpolator.dom,\
                                                                ...


## Legal Stuff ##
 
#### License ####

    Copyright 2014 Dan Haywood

    Licensed under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.


#### Dependencies ####

*** TODO

##  Maven deploy notes

Only the `dom` module is deployed, and is done so using Sonatype's OSS support (see 
[user guide](http://central.sonatype.org/pages/apache-maven.html)).

#### Release to Sonatype's Snapshot Repo ####

To deploy a snapshot, use:

    pushd dom
    mvn clean deploy
    popd

The artifacts should be available in Sonatype's 
[Snapshot Repo](https://oss.sonatype.org/content/repositories/snapshots).

#### Release to Maven Central (scripted process) ####

The `release.sh` script automates the release process.  It performs the following:

* perform sanity check (`mvn clean install -o`) that everything builds ok
* bump the `pom.xml` to a specified release version, and tag
* perform a double check (`mvn clean install -o`) that everything still builds ok
* release the code using `mvn clean deploy`
* bump the `pom.xml` to a specified release version

For example:

    sh release.sh 1.6.1 \
                  1.6.2-SNAPSHOT \
                  dan@haywood-associates.co.uk \
                  "this is not really my passphrase"
    
where
* `$1` is the release version
* `$2` is the snapshot version
* `$3` is the email of the secret key (`~/.gnupg/secring.gpg`) to use for signing
* `$4` is the corresponding passphrase for that secret key.

If the script completes successfully, then push changes:

    git push
    
If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.

#### Release to Maven Central (manual process) ####

If you don't want to use `release.sh`, then the steps can be performed manually.

To start, call `bumpver.sh` to bump up to the release version, eg:

     `sh bumpver.sh 1.6.1`

which:
* edit the parent `pom.xml`, to change `${isis-module-command.version}` to version
* edit the `dom` module's pom.xml version
* commit the changes
* if a SNAPSHOT, then tag

Next, do a quick sanity check:

    mvn clean install -o
    
All being well, then release from the `dom` module:

    pushd dom
    mvn clean deploy -P release \
        -Dpgp.secretkey=keyring:id=dan@haywood-associates.co.uk \
        -Dpgp.passphrase="literal:this is not really my passphrase"
    popd

where (for example):
* "dan@haywood-associates.co.uk" is the email of the secret key (`~/.gnupg/secring.gpg`) to use for signing
* the pass phrase is as specified as a literal

Other ways of specifying the key and passphrase are available, see the `pgp-maven-plugin`'s 
[documentation](http://kohsuke.org/pgp-maven-plugin/secretkey.html)).

If (in the `dom`'s `pom.xml`) the `nexus-staging-maven-plugin` has the `autoReleaseAfterClose` setting set to `true`,
then the above command will automatically stage, close and the release the repo.  Sync'ing to Maven Central should 
happen automatically.  According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update 
[search](http://search.maven.org).

If instead the `autoReleaseAfterClose` setting is set to `false`, then the repo will require manually closing and 
releasing either by logging onto the [Sonatype's OSS staging repo](https://oss.sonatype.org) or alternatively by 
releasing from the command line using `mvn nexus-staging:release`.

Finally, don't forget to update the release to next snapshot, eg:

    sh bumpver.sh 1.6.2-SNAPSHOT

and then push changes.
