package com.benjamin.parsy.ksb.shared;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = SharedContext.class)
@ActiveProfiles("test")
public abstract class AbstractTest {
}
