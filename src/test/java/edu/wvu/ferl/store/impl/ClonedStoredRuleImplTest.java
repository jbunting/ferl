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
public class ClonedStoredRuleImplTest extends AbstractStoredRuleImplTest {

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
