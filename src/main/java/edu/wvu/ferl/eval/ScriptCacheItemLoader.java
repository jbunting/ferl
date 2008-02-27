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
   *
   * @param scriptLoader the script loader
   */
  public ScriptCacheItemLoader(ScriptLoader scriptLoader) {
    this.scriptLoader = scriptLoader;
  }

  /**
   * Loads a new {@link ScriptCompilation} from the rule that has a uri matching {@code key}.
   *
   * @param key the uri of the rule to load
   * @return the loaded script
   * @throws UnloadableException if the script fails to load
   */
  public ScriptCompilation loadNewInstance(String key) throws UnloadableException {
    try {
      return scriptLoader.loadCompilableScript(key);
    } catch(ScriptLoadingException e) {
      throw new UnloadableException(e);
    }
  }
}
