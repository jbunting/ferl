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

  /**
   * Creates a new validator.
   * @param ruleStore the rulestore to look up the current script in
   */
  ScriptCacheItemValidator(RuleStore ruleStore) {
    this.ruleStore = ruleStore;
  }

  /**
   * Compares the raw script stored in the cache with the raw script currently in the rule store.  If they are the same
   * then this method returns true, otherwise it returns false.
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return true if the item is valid, false otherwise
   */
  public boolean isValid(String key, ScriptCompilation value) {
    StoredRule storedRule = ruleStore.lookupRule(key);
    return value.matches(storedRule);
  }
}
