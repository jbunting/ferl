package edu.wvu.ferl.eval;

import edu.wvu.ferl.store.StoredRule;
import edu.wvu.ferl.store.StoredRuleExecutionSet;
import edu.wvu.ferl.runtime.RuleRuntimeImpl;
import edu.wvu.ferl.cache.Cache;
import edu.wvu.ferl.cache.CacheItemValidator;

import javax.rules.InvalidRuleSessionException;
import javax.script.*;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: jbunting
 * Date: Feb 5, 2008
 * Time: 10:14:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class RuleEvaluator {

  private RuleRuntimeImpl ruleRuntime;
  private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

  private Cache<String, ScriptCompilation> scriptCache;
  private Cache<String, Strategy> strategyCache;

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

  public void executeRules(ExecuteRulesHook hook, StoredRuleExecutionSet storedRuleExecutionSet) throws InvalidRuleSessionException, RemoteException {
    ScriptContext context = new SimpleScriptContext();
    hook.populateScriptContext(context);
    for(String ruleUri: storedRuleExecutionSet.getRuleUris()) {
      StoredRule rule = this.ruleRuntime.getRuleStore().lookupRule(ruleUri);
      if(rule == null) {
        throw new InvalidRuleSessionException("Cannot locate rule by uri: " + ruleUri);
      }
      Strategy evalStrategy = determineStrategy(rule.getLanguage());
      Object output = evalStrategy.evaluateRule(rule, context, scriptEngineManager);
      hook.handleOutput(context, output);
    }
  }

  private Strategy determineStrategy(String language) {
    return strategyCache.lookup(language);
  }
  
  public interface ExecuteRulesHook {
    public void populateScriptContext(ScriptContext scriptContext);
    public void handleOutput(ScriptContext context, Object output);
  }
}
