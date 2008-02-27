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

import edu.wvu.ferl.cache.Cache;

import javax.script.Compilable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Used to determine which strategy to use based on the language being used.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 11:50:43 AM
 */
class StrategyDeterminer {

  private ScriptEngineManager scriptEngineManager;

  private final StrategyCompiled strategyCompiled;
  private final StrategySimple strategySimple;

  /**
   * Creates a new instance using the provided engine manager and script cache.
   *
   * @param scriptEngineManager the engine manager to use
   * @param scriptCache         the script cache to use
   */
  public StrategyDeterminer(ScriptEngineManager scriptEngineManager, Cache<String, ScriptCompilation> scriptCache) {
    this.scriptEngineManager = scriptEngineManager;
    strategyCompiled = new StrategyCompiled(scriptCache);
    strategySimple = new StrategySimple();
  }

  /**
   * Actually loads the appropriate strategy based on whether or not the language specified supports compilation.  If
   * it does, then this method returns an instance of {@link StrategyCompiled}.  Otherwise it returns an instance of
   * {@link StrategySimple}.
   *
   * @param language the language to determine the strategy for
   * @return the appropriate strategy for the specified language
   * @throws StrategyLoadingException if somethign goes wrong during the strategy loading process
   */
  public Strategy loadStrategy(String language) throws StrategyLoadingException {
    ScriptEngine engine = scriptEngineManager.getEngineByName(language);
    if(engine == null) {
      throw StrategyLoadingException.newLanguageNotExist(language);
    }
    if(engine instanceof Compilable) {
      return strategyCompiled;
    } else {
      return strategySimple;
    }
  }
}
