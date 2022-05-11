package com.emilie.lib10;

import co.poynt.postman.runner.PostmanCollectionRunner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class Lib10ApiItTest {

    //todo ??? @WebMvcTest(PatientRecordController.class)


    @Test
    public void testRunPostman() throws Exception {
        PostmanCollectionRunner cr=new PostmanCollectionRunner();

        boolean isSuccessful=cr.runCollection(
                "classpath:/com/emilie/lib10/Lib10_testing.postman_collection",
                "classpath:/com/emilie/lib10/localhost.postman_environment",
                "My use cases", false ).isSuccessful();

        assertThat( isSuccessful ).isEqualTo( true );
    }
}