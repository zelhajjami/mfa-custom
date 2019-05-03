package org.apereo.cas.config;

import org.apereo.cas.authentication.AbstractMultifactorAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.webflow.execution.Event;

public class CustomMultifactorAuthenticationProvider extends AbstractMultifactorAuthenticationProvider {
    private static final long serialVersionUID = 4789727148634156909L;

    @Override
    public String getFriendlyName() {
        return "Custom MFA";
    }
}
