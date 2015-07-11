/*
 * Copyright 2015 Daniel Bechler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.danielbechler.diff.identity

import de.danielbechler.diff.ObjectDiffer
import de.danielbechler.diff.ObjectDifferBuilder
import de.danielbechler.diff.comparison.IdentityStrategy
import spock.lang.Specification

class IdentityStrategyAT extends Specification {

	static class ObjectWithListProperty {
		List<?> collection = []
	}

	static class SimpleIdentityStrategy implements IdentityStrategy {

		@Override
		boolean equals(Object working, Object base) {
			return false
		}
	}

	def 'configuring identity strategy for property of specific type'() {
		def strategy = new SimpleIdentityStrategy()
		ObjectDiffer objectDiffer = ObjectDifferBuilder.startBuilding()
				.comparison()
				.ofCollectionItems(ObjectWithListProperty, 'collection').toUse(strategy)
				.and().build()

		def working = new ObjectWithListProperty(collection: ['a'])
		def base = new ObjectWithListProperty(collection: ['a'])

		when:
		  def node = objectDiffer.compare(working, base)
		then:
		  node.untouched
	}
}
