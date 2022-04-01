package com.emilie.Lib10.Models.Entities;

import com.emilie.Lib10.Models.Mails.Mail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class MailUnitTest {

    static Mail mailUnderTest;

    @BeforeAll
    static void beforeAll() {
        mailUnderTest = new Mail();
        assertThat( mailUnderTest ).isInstanceOf( Mail.class );
    }

    @Test
    void mailFromUT() {
        mailUnderTest.setFrom( "fromTest" );
        assertThat( mailUnderTest.getFrom() ).isInstanceOf( String.class );
        assertThat( mailUnderTest.getFrom() ).isEqualTo( "fromTest" );
    }

    @Test
    void mailMailToUT() {
        mailUnderTest.setMailTo( "mail@to.net" );
        assertThat( mailUnderTest.getMailTo() ).isInstanceOf( String.class );
        assertThat( mailUnderTest.getMailTo() ).isEqualTo( "mail@to.net" );
    }

    @Test
    void mailSubjectUT() {
        mailUnderTest.setSubject( "subject for test" );
        assertThat( mailUnderTest.getSubject() ).isInstanceOf( String.class );
        assertThat( mailUnderTest.getSubject() ).isEqualTo( "subject for test" );
    }

    @Test
    void mailAttachmentsUT() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add( mock( Object.class ));
        objectList.add( mock( Object.class ));
        mailUnderTest.setAttachments( objectList );

        assertThat( mailUnderTest.getAttachments() ).isInstanceOf( ArrayList.class );
        assertThat( mailUnderTest.getAttachments().size() ).isEqualTo( 2 );
    }

    @Test
    void mailPropsUT() {
        HashMap<String, Object> props = new HashMap<>();
        props.put("prop1", mock(Object.class ));
        props.put("prop2", mock(Object.class ));
        mailUnderTest.setProps( props );
        assertThat( mailUnderTest.getProps() ).isInstanceOf( HashMap.class );
        assertThat( mailUnderTest.getProps().size() ).isEqualTo( 2 );
    }

    @Test
    void mailTemplateUT() {
        mailUnderTest.setTemplatePath("/templatePath");
        assertThat( mailUnderTest.getTemplatePath() ).isInstanceOf( String.class );
        assertThat( mailUnderTest.getTemplatePath() ).isEqualTo( "/templatePath" );
    }
}
