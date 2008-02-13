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

/**
 * This package contains the classes used for evaluating a rule.  The primary, and only public, class is
 * {@link RuleEvaluator}.  The rule evaluator uses a number of helping classes to determine a strategy for invoking
 * a rule based on the scripting language used, and, in the case of languages that support compilation, for caching
 * the compiled scripts.  This package is really meant to be used by the implementations of {@link RuleSession}, and
 * should not be invoked by clients. 
 */
package edu.wvu.ferl.eval;