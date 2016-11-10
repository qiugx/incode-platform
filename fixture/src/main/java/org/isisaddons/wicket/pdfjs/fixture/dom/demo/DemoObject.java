/*
 *  Copyright 2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.wicket.pdfjs.fixture.dom.demo;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.value.Blob;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema="isisaddonsPdfjsDemo")
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject(
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class DemoObject implements Comparable<DemoObject> {

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private String name;

    @Property
    @javax.jdo.annotations.Column(allowsNull="true")
    @Getter @Setter
    private String url;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "blob_name"),
            @javax.jdo.annotations.Column(name = "blob_mimetype"),
            @javax.jdo.annotations.Column(name = "blob_bytes", jdbcType = "BLOB", sqlType = "LONGVARBINARY")
    })
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @Getter @Setter
    private Blob blob;


    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "name");
    }

    @Override
    public int compareTo(final DemoObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

}
