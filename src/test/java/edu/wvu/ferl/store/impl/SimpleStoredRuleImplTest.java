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

import java.util.Map;

/**
 * @author jbunting
 * @TODO Document Me!
 * Created by IntelliJ IDEA.
 * Date: Feb 29, 2008
 * Time: 8:02:32 AM
 */
public class SimpleStoredRuleImplTest extends AbstractStoredRuleImplTest {

  protected StoredRuleImpl createStoredRuleImpl(String uri, String name, String description, String language, String script, Map<Object, Object> properties) {
    return new StoredRuleImpl(uri,  name, description, language, script, properties);
  }
}
