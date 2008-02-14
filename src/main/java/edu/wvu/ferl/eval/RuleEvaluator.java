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

import edu.wvu.ferl.store.StoredRule;
import edu.wvu.ferl.store.StoredRuleExecutionSet;
import edu.wvu.ferl.runtime.RuleRuntimeImpl;
import edu.wvu.ferl.cache.Cache;
import edu.wvu.ferl.cache.CacheItemValidator;

import java.util.List;
import java.util.Map;
import javax.rules.InvalidRuleSessionException;
import javax.rules.RuleExecutionException;
import javax.script.ScriptEngineManager;
import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;

/**
 * This class is the primary party responsible for actually executing rules.  It uses package protected classes to
 * implement the strategy pattern to differentiate between languages that are compilable and ones that are not.  It
 * uses the {@link edu.wvu.ferl.cache.CacheFactory CacheFactory} obtained from the {@link RuleRuntimeImpl} in order
 * to create two caches.  One is used for storing which strategy will be used for which language.  The other is for
 * storing compiled scripts.  See {@link #executeRules} for details on how the data is passed to the scripts.
 * User: jbunting
 * Date: Feb 5, 2008
 * Time: 10:14:38 AM
 */
public class RuleEvaluator {

  public static final String DATA_ATTRIBUTE_NAME = "ferl.data";
  public static final String PREVIOUS_OUTPUT_ATTRIBUTE_NAME = "ferl.previousOutput";
  
  private RuleRuntimeImpl ruleRuntime;
  private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

  private Cache<String, Strategy> strategyCache;

  /**
   * Creates a new {@code RuleEvaluator}
   *
   * @param ruleRuntime the runtime that this evaluator is being used with
   */
  public RuleEvaluator(RuleRuntimeImpl ruleRuntime) {
    this.ruleRuntime = ruleRuntime;

    ScriptCacheItemValidator scriptValidator =
            new ScriptCacheItemValidator(ruleRuntime.getRuleServiceProvider().getRuleStore());
    ScriptCacheItemLoader scriptLoader=
            new ScriptCacheItemLoader(new ScriptLoader(ruleRuntime.getRuleServiceProvider().getRuleStore(), scriptEngineManager));


    Cache<String, ScriptCompilation> scriptCache = ruleRuntime.getRuleServiceProvider().getCacheFactory().createCache(
            scriptValidator,
            scriptLoader,
            String.class,
            ScriptCompilation.class);

    StrategyDeterminer strategyDeterminer = new StrategyDeterminer(scriptEngineManager, scriptCache);
    StrategyCacheItemLoader strategyCacheItemLoader = new StrategyCacheItemLoader(strategyDeterminer);

    strategyCache = ruleRuntime.getRuleServiceProvider().getCacheFactory().createCache(
            CacheItemValidator.TRUE,
            strategyCacheItemLoader,
            String.class,
            Strategy.class);
  }

  /**
   * The primary method of this class.  This method executes a StoredRuleExecutionSet.  The properties are mapped to
   * the {@link ScriptContext} and the data is stored in the {@code ScriptContext} under the attribute
   * {@value #DATA_ATTRIBUTE_NAME}.  Each rule is execute in turn, with the output of each rule being appended to
   * the end of {@code data} as well as being stored in the {@code ScriptContext} under the attribute
   * {@value #PREVIOUS_OUTPUT_ATTRIBUTE_NAME}.  The {@code data} passed into this method will most likely be changed
   * by the rules.
   * @param data the data to be used for executing the rules
   * @param storedRuleExecutionSet the rule set to execute
   * @param properties the properties to map to the script context
   * @throws InvalidRuleSessionException if something goes wrong 
   */
  public void executeRules(List<Object> data, StoredRuleExecutionSet storedRuleExecutionSet, Map<? extends String,Object> properties) throws RuleExecutionException {
    ScriptContext context = new SimpleScriptContext();
    context.getBindings(ScriptContext.ENGINE_SCOPE).putAll(properties);
    context.setAttribute(DATA_ATTRIBUTE_NAME, data, ScriptContext.ENGINE_SCOPE);
    for(String ruleUri : storedRuleExecutionSet.getRuleUris()) {
      StoredRule rule = this.ruleRuntime.getRuleServiceProvider().getRuleStore().lookupRule(ruleUri);
      if(rule == null) {
        throw new RuleExecutionException("Cannot locate rule by uri: " + ruleUri);
      }
      Strategy evalStrategy = strategyCache.lookup(rule.getLanguage());
      Object output = evalStrategy.evaluateRule(rule, context, scriptEngineManager);
      context.setAttribute(PREVIOUS_OUTPUT_ATTRIBUTE_NAME, output, ScriptContext.ENGINE_SCOPE);
      if(output != null) {
        data.add(output);
      }
    }
  }

}
