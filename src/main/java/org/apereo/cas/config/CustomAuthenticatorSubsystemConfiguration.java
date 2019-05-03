package org.apereo.cas.config;

import org.apereo.spring.webflow.plugin.ClientFlowExecutionRepository;
import org.apereo.spring.webflow.plugin.EncryptedTranscoder;
import org.apereo.spring.webflow.plugin.Transcoder;
import org.springframework.webflow.conversation.impl.SessionBindingConversationManager;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.impl.FlowExecutionImplFactory;
import org.springframework.webflow.execution.FlowExecutionListener;
import org.springframework.webflow.execution.factory.StaticFlowExecutionListenerLoader;
import org.springframework.webflow.execution.repository.impl.DefaultFlowExecutionRepository;
import org.springframework.webflow.execution.repository.snapshot.SerializedFlowExecutionSnapshotFactory;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.executor.FlowExecutorImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.services.MultifactorAuthenticationProvider;
//import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;

import org.springframework.webflow.config.FlowDefinitionRegistryBuilder;
import org.springframework.context.ApplicationContext;

import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.apereo.cas.configuration.CasConfigurationProperties;

@Configuration("CustomAuthenticatorSubsystemConfiguration")
public class CustomAuthenticatorSubsystemConfiguration {

    @Autowired
	private CasConfigurationProperties casProperties;

    @Autowired
	private ApplicationContext applicationContext;

    @Autowired
	@Qualifier("builder")
	private FlowBuilderServices flowBuilderServices;

    @Autowired
	@Qualifier("loginFlowRegistry")
	private FlowDefinitionRegistry loginFlowDefinitionRegistry;

    @Bean
    public FlowDefinitionRegistry customFlowRegistry() {
        final FlowDefinitionRegistryBuilder builder = new FlowDefinitionRegistryBuilder(this.applicationContext, this.flowBuilderServices);
        builder.setBasePath("classpath*:/webflow");
        builder.addFlowLocationPattern("/mfa-custom/*-webflow.xml");
        return builder.build();
    }

    @Bean
    public MultifactorAuthenticationProvider customAuthenticationProvider() {
        final CustomMultifactorAuthenticationProvider p = new CustomMultifactorAuthenticationProvider();
        p.setId("mfa-custom");
        return p;
    }

    @Bean
    public CasWebflowConfigurer customWebflowConfigurer() {
        return new CustomAuthenticatorWebflowConfigurer(
                flowBuilderServices,
                loginFlowDefinitionRegistry,
                customFlowRegistry(),
                applicationContext, 
                casProperties);
    }
}