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
 *
 */

package edu.wvu.ferl.eval;

import edu.wvu.ferl.store.RuleStore;
import edu.wvu.ferl.store.StoredRule;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Used to load scripts from the {@link RuleStore}.  Will also compile them if the script type is compilable.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 10:58:42 AM
 */
class ScriptLoader {

  private RuleStore ruleStore;
  private ScriptEngineManager scriptEngineManager;

  /**
   * Creates a new {@code ScriptLoader}.
   *
   * @param ruleStore           the store to retrieve rules from
   * @param scriptEngineManager the engine manager to retrieve {@link ScriptEngine ScriptEngines} from for compiling
   */
  ScriptLoader(RuleStore ruleStore, ScriptEngineManager scriptEngineManager) {
    this.ruleStore = ruleStore;
    this.scriptEngineManager = scriptEngineManager;
  }

  /**
   * Loads a compiled script.  Returns the result in a {@link ScriptCompilation} that pairs the compiled script with the
   * information used to create it.
   *
   * @param uri the uri of the rule to load
   * @return the pair containing the compiled script and the original script
   * @throws ScriptLoadingException if something goes wrong during the load or compile process
   */
  public ScriptCompilation loadCompilableScript(String uri) throws ScriptLoadingException {
    // get the rule
    StoredRule storedRule = loadStoredRule(uri);
    // get the script engine
    ScriptEngine engine = scriptEngineManager.getEngineByName(storedRule.getLanguage());
    if(engine == null) {
      throw ScriptLoadingException.newNotValidScriptType(storedRule.getLanguage(), uri);
    }
    // check if the engine implements compilable, if so then cast it
    if(!(engine instanceof Compilable)) {
      throw ScriptLoadingException.newNotCompilable(storedRule.getLanguage(), uri);
    }
    Compilable compiler = (Compilable) engine;
    // actually perform the compile and return the result
    try {
      CompiledScript compiled = compiler.compile(storedRule.getScript());
      return new ScriptCompilation(compiled, storedRule.getScript(), storedRule.getLanguage());
    } catch(ScriptException ex) {
      throw ScriptLoadingException.newErrorCompiling(storedRule.getLanguage(), uri, ex);
    }
  }

  /**
   * Loads an uncompiled script.  Returns the script as a string.
   *
   * @param uri the uri of the rule to load
   * @return the script text
   * @throws ScriptLoadingException if something goes wrong during the load process
   */
  public String loadUncompiledScript(String uri) throws ScriptLoadingException {
    StoredRule storedRule = loadStoredRule(uri);
    if(scriptEngineManager.getEngineByName(storedRule.getLanguage()) == null) {
      throw ScriptLoadingException.newNotValidScriptType(storedRule.getLanguage(), uri);
    }
    return storedRule.getScript();
  }

  /**
   * Loads a rule.
   *
   * @param uri the ur of the rule to load
   * @return the stored rule
   * @throws ScriptLoadingException if something goes wrong during the load process
   */
  private StoredRule loadStoredRule(String uri) throws ScriptLoadingException {
    StoredRule storedRule = ruleStore.lookupRule(uri);
    if(storedRule == null) {
      throw ScriptLoadingException.newRuleNotExist(uri);
    }
    return storedRule;
  }

}
