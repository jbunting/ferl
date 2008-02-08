package edu.wvu.ferl.eval;

import edu.wvu.ferl.cache.CacheItemLoader;
import edu.wvu.ferl.cache.UnloadableException;

/**
 * The {@link CacheItemLoader} used to compile new scripts.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 10:12:59 AM
 */
class ScriptCacheItemLoader implements CacheItemLoader<String, ScriptCompilation> {

  private ScriptLoader scriptLoader;

  /**
   * Creates a new loader.
   * @param scriptLoader the script loader
   */
  public ScriptCacheItemLoader(ScriptLoader scriptLoader) {
    this.scriptLoader = scriptLoader;
  }

  /**
   * Loads a new {@link ScriptCompilation} from the rule that has a uri matching {@code key}.
   * @param key the uri of the rule to load
   * @return the loaded script
   * @throws UnloadableException if the script fails to load
   */
  public ScriptCompilation loadNewInstance(String key) throws UnloadableException {
    try {
      return scriptLoader.loadCompilableScript(key);
    } catch (ScriptLoadingException e) {
      throw new UnloadableException(e);
    }
  }
}
