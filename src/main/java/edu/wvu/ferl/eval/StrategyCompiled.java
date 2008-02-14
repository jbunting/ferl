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

/*
 * StrategyCompiled.java
 *
 * Created on May 5, 2007, 7:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.eval;

import edu.wvu.ferl.store.StoredRule;
import edu.wvu.ferl.cache.Cache;

import javax.rules.RuleExecutionException;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * A {@link Strategy} used for scripting engines that support compilation.  This {@code Strategy} will invoke a
 * {@link CompiledScript} as retrieved from the script cache.
 *
 * @author jbunting
 */
class StrategyCompiled implements Strategy {

  private Cache<String, ScriptCompilation> scriptCache;

  /**
   * Creates a new object using the provided script cache.
   *
   * @param scriptCache the script cache to obtain {@link CompiledScript CompiledScripts} from
   */
  StrategyCompiled(Cache<String, ScriptCompilation> scriptCache) {
    this.scriptCache = scriptCache;
  }

  /**
   * Evalutes the compiled script after retrieving it from the script cache.
   *
   * @param rule          {@inheritDoc}
   * @param context       {@inheritDoc}
   * @param engineManager {@inheritDoc}
   * @return the output of the rule invocation
   * @throws RuleExecutionException {@inheritDoc}
   */
  public Object evaluateRule(StoredRule rule, ScriptContext context, ScriptEngineManager engineManager) throws RuleExecutionException {
    CompiledScript compiledScript = scriptCache.lookup(rule.getUri()).getCompiled();
    try {
      return compiledScript.eval(context);
    } catch(ScriptException ex) {
      throw new RuleExecutionException("Error executing rule: " + rule.getUri(), ex);
    }
  }
}
