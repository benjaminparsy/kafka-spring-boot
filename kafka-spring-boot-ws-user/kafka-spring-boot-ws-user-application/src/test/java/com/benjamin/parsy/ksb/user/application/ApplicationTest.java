package com.benjamin.parsy.ksb.user.application;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles("test")
public abstract class ApplicationTest {
}
