/*
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
 *
 */

package edu.wvu.ferl.eval;

import edu.wvu.ferl.cache.CacheItemValidator;
import edu.wvu.ferl.store.RuleStore;
import edu.wvu.ferl.store.StoredRule;

/**
 * Validates that a script compilation still matches the rule that it was created from.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 10:40:10 AM
 */
class ScriptCacheItemValidator implements CacheItemValidator<String, ScriptCompilation> {

  private RuleStore ruleStore;

  /**
   * Creates a new validator.
   *
   * @param ruleStore the rulestore to look up the current script in
   */
  ScriptCacheItemValidator(RuleStore ruleStore) {
    this.ruleStore = ruleStore;
  }

  /**
   * Compares the raw script stored in the cache with the raw script currently in the rule store.  If they are the same
   * then this method returns true, otherwise it returns false.
   *
   * @param key   {@inheritDoc}
   * @param value {@inheritDoc}
   * @return true if the item is valid, false otherwise
   */
  public boolean isValid(String key, ScriptCompilation value) {
    StoredRule storedRule = ruleStore.lookupRule(key);
    return value.matches(storedRule);
  }
}
