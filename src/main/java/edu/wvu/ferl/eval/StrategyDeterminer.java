package edu.wvu.ferl.eval;

import edu.wvu.ferl.cache.Cache;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.Compilable;

/**
 * Created by IntelliJ IDEA.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 11:50:43 AM
 * To change this template use File | Settings | File Templates.
 */
class StrategyDeterminer {

  private ScriptEngineManager scriptEngineManager;
  private Cache<String, ScriptCompilation> scriptCache;

  public StrategyDeterminer(ScriptEngineManager scriptEngineManager, Cache<String, ScriptCompilation> scriptCache) {
    this.scriptEngineManager = scriptEngineManager;
    this.scriptCache = scriptCache;
  }

  public Strategy loadStrategy(String language) throws StrategyLoadingException {
    ScriptEngine engine = scriptEngineManager.getEngineByName(language);
    if(engine == null) {
      throw StrategyLoadingException.newLanguageNotExist(language);
    }
    if(engine instanceof Compilable) {
      return new StrategyCompiled(scriptCache);
    } else {
      return new StrategySimple();
    }
  }
}
