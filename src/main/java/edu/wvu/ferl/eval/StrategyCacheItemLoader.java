package edu.wvu.ferl.eval;

import edu.wvu.ferl.cache.CacheItemLoader;
import edu.wvu.ferl.cache.UnloadableException;

/**
 * Created by IntelliJ IDEA.
 * User: jbunting
 * Date: Feb 6, 2008
 * Time: 12:12:57 PM
 * To change this template use File | Settings | File Templates.
 */
class StrategyCacheItemLoader implements CacheItemLoader<String, Strategy> {

  private StrategyDeterminer strategyDeterminer;

  public StrategyCacheItemLoader(StrategyDeterminer strategyDeterminer) {
    this.strategyDeterminer = strategyDeterminer;
  }

  public Strategy loadNewInstance(String key) throws UnloadableException {
    try {
      return strategyDeterminer.loadStrategy(key);
    } catch (StrategyLoadingException ex) {
      throw new UnloadableException(ex);
    }
  }
}
