package edu.wvu.ferl.eval;

import edu.wvu.ferl.store.StoredRule;
import edu.wvu.ferl.store.StoredRuleExecutionSet;
import edu.wvu.ferl.runtime.RuleRuntimeImpl;
import edu.wvu.ferl.cache.Cache;
import edu.wvu.ferl.cache.CacheItemValidator;

import javax.rules.InvalidRuleSessionException;
import javax.script.ScriptEngineManager;
import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;

/**
 * This class is the primary party responsible for actually executing rules.  It uses package protected classes to
 * implement the strategy pattern to differentiate between languages that are compilable and ones that are not.  It
 * uses the {@link edu.wvu.ferl.cache.CacheFactory CacheFactory} obtained from the {@link RuleRuntimeImpl} in order
 * to create two caches.  One is used for storing which strategy will be used for which language.  The other is for
 * storing compiled scripts.  It also uses a callback interface {@link ExecuteRulesHook}.  This is to provide for
 * the differences between Stateless and Stateful rule sessions.  Each one provides it's own implementation to be used
 * for executing rules.
 * User: jbunting
 * Date: Feb 5, 2008
 * Time: 10:14:38 AM
 */
public class RuleEvaluator {

  private RuleRuntimeImpl ruleRuntime;
  private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

  @SuppressWarnings({"FieldCanBeLocal"})
  private Cache<String, ScriptCompilation> scriptCache;
  private Cache<String, Strategy> strategyCache;

  /**
   * Creates a new {@code RuleEvaluator}
   *
   * @param ruleRuntime the runtime that this evaluator is being used with
   */
  public RuleEvaluator(RuleRuntimeImpl ruleRuntime) {
    this.ruleRuntime = ruleRuntime;
    scriptCache = ruleRuntime.getRuleServiceProvider().getCacheFactory().createCache(
            new ScriptCacheItemValidator(ruleRuntime.getRuleStore()),
            new ScriptCacheItemLoader(new ScriptLoader(ruleRuntime.getRuleStore(), scriptEngineManager)),
            String.class,
            ScriptCompilation.class);

    strategyCache = ruleRuntime.getRuleServiceProvider().getCacheFactory().createCache(
            CacheItemValidator.TRUE,
            new StrategyCacheItemLoader(new StrategyDeterminer(scriptEngineManager, scriptCache)),
            String.class,
            Strategy.class);
  }

  /**
   * The primary method of this class.  This method executes a StoredRuleExecutionSet.  The {@link ExecuteRulesHook} is
   * called to provide for differences in context handling.
   *
   * @param hook                   the hook that will be invoked to perform context handling
   * @param storedRuleExecutionSet the execution set to be invoked
   * @throws InvalidRuleSessionException if there is an issue with the rule invocation requested
   */
  public void executeRules(ExecuteRulesHook hook, StoredRuleExecutionSet storedRuleExecutionSet) throws InvalidRuleSessionException {
    ScriptContext context = new SimpleScriptContext();
    hook.populateScriptContext(context);
    for(String ruleUri : storedRuleExecutionSet.getRuleUris()) {
      StoredRule rule = this.ruleRuntime.getRuleStore().lookupRule(ruleUri);
      if(rule == null) {
        throw new InvalidRuleSessionException("Cannot locate rule by uri: " + ruleUri);
      }
      Strategy evalStrategy = strategyCache.lookup(rule.getLanguage());
      Object output = evalStrategy.evaluateRule(rule, context, scriptEngineManager);
      hook.handleOutput(context, output);
    }
  }

  /**
   * Used to allow the client of the {@code RuleEvaluator} to decide how to initially populate the
   * {@link ScriptContext} and also how to handle the output of each rule.
   */
  public interface ExecuteRulesHook {

    /**
     * Invoked prior to executing any of the rules in order to allow the session to populate the {@link ScriptContext}
     * that is going to be used for running the rules.
     *
     * @param scriptContext the script context to populate
     */
    public void populateScriptContext(ScriptContext scriptContext);

    /**
     * Invoked after the execution of each rule in the rule set.  This method allows the session to handle the output
     * in a manner appropriate for the type of session being run.
     *
     * @param context the script context for this execution
     * @param output  the output from the last rule executed
     */
    public void handleOutput(ScriptContext context, Object output);
  }
}
