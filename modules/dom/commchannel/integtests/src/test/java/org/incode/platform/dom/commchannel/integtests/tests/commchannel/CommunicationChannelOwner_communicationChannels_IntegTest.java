package org.incode.platform.dom.commchannel.integtests.tests.commchannel;

import java.util.SortedSet;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.commchannel.fixture.DemoObject_withCommChannels_tearDown;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.platform.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannelOwner_communicationChannels_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject fredDemoOwner;
    DemoObject billDemoOwner;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withCommChannels_tearDown(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).createDemoObject("Fred");
        billDemoOwner = wrap(commChannelDemoObjectMenu).createDemoObject("Bill");

        wrap(mixinNewEmailAddress(fredDemoOwner)).$$("fred@gmail.com", "Home Email", "Fred Smith's home email", new LocalDate(2017, 1, 1), null);
        wrap(mixinNewEmailAddress(fredDemoOwner)).$$("fred.smith@somecompany.com", "Work Email",
                "Fred Smith's work email", new LocalDate(2017, 2, 1), null);

        wrap(mixinNewPhoneOrFaxNumber(fredDemoOwner)).$$(
                CommunicationChannelType.PHONE_NUMBER, "0208 111 1111", "Home Phone", "Fred Smith's home phone number", new LocalDate(2017, 1, 1), null);
        wrap(mixinNewPhoneOrFaxNumber(fredDemoOwner)).$$(
                CommunicationChannelType.PHONE_NUMBER, "0207 222 2222", "Mobile Phone",
                "Fred Smith's work phone number", new LocalDate(2017, 1, 1), null);
        wrap(mixinNewPhoneOrFaxNumber(fredDemoOwner)).$$(
                CommunicationChannelType.FAX_NUMBER, "0207 222 3333", "Work Fax", "Fred Smith's work fax number", new LocalDate(2017, 1, 1), null);
        wrap(mixinNewPostalAddress(fredDemoOwner)).$$(
                "Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA", "UK",
                "Shipping Address", "Fred Smith's home", false, new LocalDate(2017, 1, 1), null);
        wrap(mixinNewPostalAddress(fredDemoOwner)).$$(
                "Grange School", "Wavertree", null, "Liverpool", "L36 1QQ", "UK",
                "Billing Address", "Fred Smith's work", false, new LocalDate(2017, 1, 1), null);

        wrap(mixinNewEmailAddress(billDemoOwner)).$$("bill@yahoo.com", "Home Email", "Bill Jones' home email", new LocalDate(2017, 1, 1), null);
        wrap(mixinNewPhoneOrFaxNumber(billDemoOwner)).$$(
                CommunicationChannelType.PHONE_NUMBER, "01865 222 222", "Work Number", "Bill Jones' work phone number", new LocalDate(2017, 1, 1), null);
        wrap(mixinNewPhoneOrFaxNumber(billDemoOwner)).$$(
                CommunicationChannelType.FAX_NUMBER, "01865 222 333", "Work Fax", "Bill Jones' work fax number", new LocalDate(2017, 1, 1), null);
        wrap(mixinNewPostalAddress(billDemoOwner)).$$(
                "Beatles Museum", "Albert Dock", null, "Liverpool", "L5 1AB", "UK",
                "Shipping Address", "Bill Jones's work", false, new LocalDate(2017, 1, 1), null);
    }

    public static class ActionImplementationIntegrationTest extends
            CommunicationChannelOwner_communicationChannels_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final SortedSet<CommunicationChannel> channels = wrap(
                    mixinCommunicationChannels(fredDemoOwner)).$$();

            assertThat(channels).hasSize(7);
        }

    }

}