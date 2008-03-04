/**
 * Copyright 2008 West Virginia University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
