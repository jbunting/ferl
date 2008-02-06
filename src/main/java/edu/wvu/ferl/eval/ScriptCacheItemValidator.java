package edu.wvu.ferl.eval;

import edu.wvu.ferl.cache.CacheItemValidator;
import edu.wvu.ferl.store.StoredRule;
import edu.wvu.ferl.store.RuleStore;

/**
 * Validates that a script compilation still matches the rule that it was created from.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 10:40:10 AM
 */
class ScriptCacheItemValidator implements CacheItemValidator<String, ScriptCompilation> {

  private RuleStore ruleStore;

  ScriptCacheItemValidator(RuleStore ruleStore) {
    this.ruleStore = ruleStore;
  }

  /**
   * {@inheritDoc}
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc
   */
  public boolean isValid(String key, ScriptCompilation value) {
    StoredRule storedRule = ruleStore.lookupRule(key);
    return value.matches(storedRule);
  }
}
