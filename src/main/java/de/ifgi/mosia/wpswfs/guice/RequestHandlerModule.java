/**
 * Copyright (C) 2014
 * by Institute for Geoinformatics, University of Muenster
 *
 * Contact: http://www.ifgi.de
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
 */
package de.ifgi.mosia.wpswfs.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

import de.ifgi.mosia.wpswfs.handler.GenericRequestHandler;
import de.ifgi.mosia.wpswfs.handler.GetCapabilitiesHandler;
import de.ifgi.mosia.wpswfs.handler.GetFeatureHandler;
import de.ifgi.mosia.wpswfs.handler.RequestHandler;

public class RequestHandlerModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<RequestHandler> binder = Multibinder.newSetBinder(binder(),
				RequestHandler.class);
		binder.addBinding().to(GetCapabilitiesHandler.class);
		binder.addBinding().to(GetFeatureHandler.class);	
		
		binder().bind(GenericRequestHandler.class).in(Scopes.SINGLETON);;
	}

}
