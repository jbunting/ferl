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

import java.util.List;
import java.util.Map;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Mar 3, 2008
 * Time: 3:50:24 PM
 */
public class SimpleStoredRuleExecutionSetImplTest extends AbstractStoredRuleExecutionSetImplTest {
  protected StoredRuleExecutionSetImpl createStoredRuleExecutionSetImpl(String uri, String name, String description, List<String> ruleUris, Map<Object, Object> properties, String defaultObjectFilter) {
    return new StoredRuleExecutionSetImpl(uri, name, description, ruleUris, properties, defaultObjectFilter);
  }
}
