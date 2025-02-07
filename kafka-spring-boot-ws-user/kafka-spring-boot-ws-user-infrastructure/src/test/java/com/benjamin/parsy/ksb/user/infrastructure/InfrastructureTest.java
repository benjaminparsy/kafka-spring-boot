package com.benjamin.parsy.ksb.user.infrastructure;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = InfrastructureContext.class)
@ActiveProfiles("test")
public abstract class InfrastructureTest {

}
