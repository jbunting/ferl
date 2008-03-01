package edu.wvu.ferl.store.impl;

import edu.wvu.ferl.store.StoredRule;

import java.util.Map;

import org.jmock.Expectations;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Feb 29, 2008
 * Time: 7:01:11 PM
 */
public class ClonedStoredRuleImplTest extends StoredRuleImplTest {

  protected StoredRuleImpl createStoredRuleImpl(final String uri,
                                                final String name,
                                                final String description,
                                                final String language,
                                                final String script,
                                                final Map<Object, Object> properties) {
    final StoredRule mockStoredRule = context.mock(StoredRule.class);
    context.checking(new Expectations() {{
      allowing(mockStoredRule).getUri();
      will(returnValue(uri));
      allowing(mockStoredRule).getName();
      will(returnValue(name));
      allowing(mockStoredRule).getDescription();
      will(returnValue(description));
      allowing(mockStoredRule).getLanguage();
      will(returnValue(language));
      allowing(mockStoredRule).getScript();
      will(returnValue(script));
      allowing(mockStoredRule).getProperties();
      will(returnValue(properties));
    }});
    return new StoredRuleImpl(mockStoredRule);
  }
}
