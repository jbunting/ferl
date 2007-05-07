/*
 * DefaultCompiledScriptCache.java
 *
 * Created on May 5, 2007, 7:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.wvu.ferl.eval;

import java.util.HashMap;
import java.util.Map;
import javax.script.CompiledScript;

/**
 *
 * @author jbunting
 */
public class DefaultCompiledScriptCache implements CompiledScriptCache {
  
  private Map<String, CompiledItem> scriptStore = new HashMap<String, CompiledItem>();
  
  /** Creates a new instance of DefaultCompiledScriptCache */
  public DefaultCompiledScriptCache() {
  }

  public void storeCompiledScript(String ruleName, String script, CompiledScript compiledScript) {
    scriptStore.put(ruleName, new CompiledItem(script, compiledScript));
  }

  public CompiledScript retrieveCompiledScript(String ruleName, String script) {
    CompiledItem item = scriptStore.get(ruleName);
    if(item != null && item.script.equals(script)) {
      return item.compiledScript;
    } else {
      return null;
    }
  }
  
  private class CompiledItem {
    public String script;
    public CompiledScript compiledScript;
    
    public CompiledItem(String script, CompiledScript compiledScript) {
      this.script = script;
      this.compiledScript = compiledScript;
    }
  }
}
