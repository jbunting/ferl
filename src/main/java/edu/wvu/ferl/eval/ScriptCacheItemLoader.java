package edu.wvu.ferl.eval;

import edu.wvu.ferl.cache.CacheItemLoader;
import edu.wvu.ferl.cache.UnloadableException;
import edu.wvu.ferl.eval.ScriptLoadingException;

/**
 * The {@link CacheItemLoader} used to compile new scripts.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 10:12:59 AM
 */
class ScriptCacheItemLoader implements CacheItemLoader<String, ScriptCompilation> {

  private ScriptLoader scriptLoader;

  public ScriptCacheItemLoader(ScriptLoader scriptLoader) {
    this.scriptLoader = scriptLoader;
  }

  public ScriptCompilation loadNewInstance(String key) throws UnloadableException {
    try {
      ScriptCompilation compilation = scriptLoader.loadCompilableScript(key);
      return compilation;
    } catch (ScriptLoadingException e) {
      throw new UnloadableException(e);
    }
  }
}
