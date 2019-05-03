package org.apereo.cas.config;

import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apereo.cas.web.flow.configurer.AbstractCasMultifactorWebflowConfigurer;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;


import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.context.ApplicationContext;



public class CustomAuthenticatorWebflowConfigurer extends AbstractCasMultifactorWebflowConfigurer {
    public static final String MFA_EVENT_ID = "mfa-custom";
    private FlowDefinitionRegistry flowDefinitionRegistry;

    public CustomAuthenticatorWebflowConfigurer(final FlowBuilderServices flowBuilderServices,
                                                           final FlowDefinitionRegistry loginFlowDefinitionRegistry,
                                                           final FlowDefinitionRegistry flowDefinitionRegistry,
                                                           final ApplicationContext applicationContext,
                                                           final CasConfigurationProperties casProperties) {
        super(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext, casProperties);
        this.flowDefinitionRegistry = flowDefinitionRegistry;
    }

    @Override
    protected void doInitialize() {
        registerMultifactorProviderAuthenticationWebflow(getLoginFlow(), MFA_EVENT_ID, this.flowDefinitionRegistry);
    }
}
