package org.incode.example.classification.demo.usage.dom.classification.demowithatpath;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Property;

import org.incode.example.classification.demo.shared.demowithatpath.dom.SomeClassifiedObject;
import org.incode.example.classification.dom.impl.classification.Classification;
import org.incode.example.classification.dom.impl.classification.ClassificationRepository;
import org.incode.example.classification.dom.impl.classification.T_classifications;
import org.incode.example.classification.dom.impl.classification.T_classify;
import org.incode.example.classification.dom.impl.classification.T_unclassify;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema ="classificationDemoUsage"
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class ClassificationForSomeClassifiedObject extends Classification {


    @Column(allowsNull = "false", name = "demoObjectId")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private SomeClassifiedObject demoObject;




    @Override
    public Object getClassified() {
        return getDemoObject();
    }

    @Override
    protected void setClassified(final Object classified) {
        setDemoObject((SomeClassifiedObject) classified);
    }



    @Mixin
    public static class classifications
            extends T_classifications<SomeClassifiedObject> {
        public classifications(final SomeClassifiedObject classified) {
            super(classified);
        }
    }

    @Mixin
    public static class classify extends T_classify<SomeClassifiedObject> {
        public classify(final SomeClassifiedObject classified) {
            super(classified);
        }
    }

    @Mixin
    public static class unclassify extends T_unclassify<SomeClassifiedObject> {
        public unclassify(final SomeClassifiedObject classified) {
            super(classified);
        }
    }

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider
            extends ClassificationRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(SomeClassifiedObject.class, ClassificationForSomeClassifiedObject.class);
        }
    }


}
