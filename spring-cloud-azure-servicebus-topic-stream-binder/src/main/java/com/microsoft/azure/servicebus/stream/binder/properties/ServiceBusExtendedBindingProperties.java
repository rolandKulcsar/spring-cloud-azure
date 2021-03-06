/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.azure.servicebus.stream.binder.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.stream.binder.BinderSpecificPropertiesProvider;
import org.springframework.cloud.stream.binder.ExtendedBindingProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Warren Zhu
 */
@ConfigurationProperties("spring.cloud.stream.servicebus.topic")
public class ServiceBusExtendedBindingProperties
        implements ExtendedBindingProperties<ServiceBusTopicConsumerProperties, ServiceBusTopicProducerProperties> {
    private static final String DEFAULTS_PREFIX = "spring.cloud.stream.servicebus.topic.default";
    private final Map<String, ServiceBusTopicBindingProperties> bindings = new ConcurrentHashMap<>();

    @Override
    public ServiceBusTopicConsumerProperties getExtendedConsumerProperties(String channelName) {
        return this.bindings.computeIfAbsent(channelName, key -> new ServiceBusTopicBindingProperties()).getConsumer();
    }

    @Override
    public ServiceBusTopicProducerProperties getExtendedProducerProperties(String channelName) {
        return this.bindings.computeIfAbsent(channelName, key -> new ServiceBusTopicBindingProperties()).getProducer();
    }

    @Override
    public String getDefaultsPrefix() {
        return DEFAULTS_PREFIX;
    }

    @Override
    public Class<? extends BinderSpecificPropertiesProvider> getExtendedPropertiesEntryClass() {
        return ServiceBusTopicBindingProperties.class;
    }

    public Map<String, ServiceBusTopicBindingProperties> getBindings() {
        return bindings;
    }
}
