package functions.pact;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import functions.log.Ln;
import functions.rest.LnRestProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("ln-provider")
@PactFolder("target/pacts")
public class LnProviderPactTest {

    private LnRestProvider provider;

    @BeforeEach
    void startProvider(PactVerificationContext context) throws Exception {
        provider = new LnRestProvider(new Ln(), 0);
        provider.start();
        context.setTarget(new HttpTestTarget("localhost", provider.getPort()));
    }

    @AfterEach
    void stopProvider() {
        if (provider != null) {
            provider.stop();
        }
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("ln service is available")
    void lnServiceIsAvailable() {
    }
}
