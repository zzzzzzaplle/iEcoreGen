package edu.ustb.sei.mde.mwe2.enhancement

import com.google.inject.Injector

abstract class InjectorClient {
	def void inject(Injector injector) {
		injector.injectMembers(this)
	}
}