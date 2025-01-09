package com.benjamin.parsy.ksb.shared;

import com.benjamin.parsy.ksb.shared.test.TestConstant;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = BeanConfiguration.class)
@ActiveProfiles(TestConstant.TEST_PROFILE)
public abstract class AbstractTest {
}
