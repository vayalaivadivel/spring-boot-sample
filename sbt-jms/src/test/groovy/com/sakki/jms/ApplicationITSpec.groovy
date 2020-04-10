package com.sakki.jms

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

import spock.lang.Specification

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
class ApplicationITSpec extends Specification {	 
    @Autowired
    ApplicationContext appCtx;

	@Test
    def "application Wireup"() {
        expect:
        appCtx
    }

}
