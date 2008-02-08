/**
 * This package contains implementation of the {@link edu.wvu.ferl.store} interfaces.  {@link DefaultRuleStore} is a
 * sample rule store that uses a map as its backing mechanism.  As this store is not persistent in any way, it is not
 * recommended for production use.  Both {@link StoredRuleImpl} and {@link StoredRuleExecutionSetImpl} are basic,
 * read only implementations of their respective interfaces.  Both provide constructors for making defensive copies
 * from the interface, and can be used by other {@link RuleStore} implementations.
 */
package edu.wvu.ferl.store.impl;