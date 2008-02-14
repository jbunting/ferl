/*
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

package edu.wvu.ferl.admin;

import javax.rules.ConfigurationException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

/**
 * @author Jared Bunting
 */
public class ProviderRuleSet implements RuleSet {

  private static final Log logger =
          LogFactory.getLog(Class.class);

  private RuleAdministratorImpl administrator;

  /**
   * Creates a new instance of ProviderRuleSet
   */
  public ProviderRuleSet(RuleAdministratorImpl administrator) {
    this.administrator = administrator;
  }

  public String getNamespaceURI() {
    return null;
  }

  public void addRuleInstances(Digester digester) {
    digester.addRule("rule-execution-set", new RuleExecutionSetCreationRule());
    digester.addRule("rule", new RuleDetailedDescriptorCreationRule());
    digester.addRule("rule-ref", new RuleReferenceCreationRule());
    digester.addSetNext("rule", "addRuleDescriptor");
    digester.addSetNext("rule-ref", "addRuleDescriptor");
    digester.addBeanPropertySetter("name");
    digester.addBeanPropertySetter("description");
    digester.addBeanPropertySetter("language");
    digester.addBeanPropertySetter("script");
    digester.addCallMethod("property", "setProperty", 2);
    digester.addCallParam("property", 0, "key");
    digester.addCallParam("property", 1, "value");
  }

  private abstract class UriBasedObjectCreationRule extends Rule {
    public void begin(String namespace, String name, Attributes attributes) throws Exception {
      String uri = attributes.getValue("uri");
      if(StringUtils.isBlank(uri)) {
        throw new Exception("No URI found!!");
      }
      digester.push(createObject(uri));
    }

    public void end(String namespace, String name) throws Exception {
      Object top = digester.pop();
    }

    protected abstract Object createObject(String uri) throws Exception;
  }

  private class RuleExecutionSetCreationRule extends UriBasedObjectCreationRule {
    protected Object createObject(String uri) {
      return new RuleExecutionSetImpl(uri);
    }
  }

  private class RuleDetailedDescriptorCreationRule extends UriBasedObjectCreationRule {
    protected Object createObject(String uri) {
      return new RuleDetailedDescriptor(uri);
    }
  }

  private class RuleReferenceCreationRule extends UriBasedObjectCreationRule {
    protected Object createObject(String uri) throws Exception {
      return new RuleReference(uri, administrator.serviceProvider.getRuleRuntime().getRuleServiceProvider().getRuleStore());
    }
  }

}
