package edu.wvu.ferl.store.impl;

import edu.wvu.ferl.store.StoredRuleExecutionSet;

import java.util.List;
import java.util.Map;

import org.jmock.Expectations;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Mar 3, 2008
 * Time: 4:17:27 PM
 */
public class ClonedStoredRuleExecutionSetImplTest extends AbstractStoredRuleExecutionSetImplTest {
  protected StoredRuleExecutionSetImpl createStoredRuleExecutionSetImpl(final String uri,
                                                                        final String name,
                                                                        final String description,
                                                                        final List<String> ruleUris,
                                                                        final Map<Object, Object> properties,
                                                                        final String defaultObjectFilter) {
    final StoredRuleExecutionSet mockStoredRuleExecutionSet = context.mock(StoredRuleExecutionSet.class);
    context.checking(new Expectations() {{
      allowing(mockStoredRuleExecutionSet).getUri();
      will(returnValue(uri));
      allowing(mockStoredRuleExecutionSet).getName();
      will(returnValue(name));
      allowing(mockStoredRuleExecutionSet).getDescription();
      will(returnValue(description));
      allowing(mockStoredRuleExecutionSet).getRuleUris();
      will(returnValue(ruleUris));
      allowing(mockStoredRuleExecutionSet).getProperties();
      will(returnValue(properties));
      allowing(mockStoredRuleExecutionSet).getDefaultObjectFilter();
      will(returnValue(defaultObjectFilter));
    }});
    return new StoredRuleExecutionSetImpl(mockStoredRuleExecutionSet);
  }
}
