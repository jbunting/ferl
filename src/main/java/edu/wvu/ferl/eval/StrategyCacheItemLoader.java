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
 * The {@link CacheItemLoader} used to load new strategies.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 12:12:57 PM
 */
class StrategyCacheItemLoader implements CacheItemLoader<String, Strategy> {

  private StrategyDeterminer strategyDeterminer;

  /**
   * Creates a new instance using the specified {@link StrategyDeterminer}.
   *
   * @param strategyDeterminer the strategy determiner to use for loading
   */
  public StrategyCacheItemLoader(StrategyDeterminer strategyDeterminer) {
    this.strategyDeterminer = strategyDeterminer;
  }

  /**
   * Loads a new strategy by interpreting the key as the name of a language and invoking the {@link StrategyDeterminer}.
   *
   * @param key the name fo the language
   * @return the appropriate strategy
   * @throws UnloadableException if the strategy cannot be loaded for some reason
   */
  public Strategy loadNewInstance(String key) throws UnloadableException {
    try {
      return strategyDeterminer.loadStrategy(key);
    } catch(StrategyLoadingException ex) {
      throw new UnloadableException(ex);
    }
  }
}
