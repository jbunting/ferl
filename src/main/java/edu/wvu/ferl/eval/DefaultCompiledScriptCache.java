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
    synchronized(this) {
      scriptStore.put(ruleName, new CompiledItem(script, compiledScript));
    }
  }

  public CompiledScript retrieveCompiledScript(String ruleName, String script) {
    synchronized(this) {
      CompiledItem item = scriptStore.get(ruleName);
      if(item != null && item.matches(script)) {
        return item.getCompiledScript();
      } else {
        scriptStore.remove(ruleName);
        return null;
      }
    }
  }
  
  private class CompiledItem {
    private String script;
    private CompiledScript compiledScript;
    
    public CompiledItem(String script, CompiledScript compiledScript) {
      this.script = script;
      this.compiledScript = compiledScript;
    }
    
    public boolean matches(String script) {
      return this.script.equals(script);
    }
    
    public CompiledScript getCompiledScript() {
      return this.compiledScript;
    }
  }
}
