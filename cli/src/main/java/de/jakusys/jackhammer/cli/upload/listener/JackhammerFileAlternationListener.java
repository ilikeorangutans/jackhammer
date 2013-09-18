/**
 * Copyright (C) 2013 Jakob Külzer (jakob.kuelzer@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.jakusys.jackhammer.cli.upload.listener;

import com.google.common.eventbus.EventBus;
import de.jakusys.jackhammer.cli.path.Path;
import de.jakusys.jackhammer.cli.upload.event.UploadEvent;
import de.jakusys.jackhammer.cli.upload.handler.Operation;
import de.jakusys.jackhammer.cli.util.PathRelativizer;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

import static de.jakusys.jackhammer.cli.upload.handler.Operation.*;

/**
 * @author Jakob Külzer
 */
public class JackhammerFileAlternationListener implements FileAlterationListener {

	private final EventBus bus;

	private final PathRelativizer pathRelativizer;

	public JackhammerFileAlternationListener(EventBus bus, PathRelativizer pathRelativizer) {
		this.bus = bus;
		this.pathRelativizer = pathRelativizer;
	}

	@Override
	public void onDirectoryChange(File directory) {
		postRequest(Update, directory);
	}

	@Override
	public void onDirectoryCreate(File directory) {
		postRequest(Create, directory);
	}

	@Override
	public void onDirectoryDelete(File directory) {
		postRequest(Delete, directory);
	}

	@Override
	public void onFileChange(File file) {
		postRequest(Update, file);
	}

	@Override
	public void onFileCreate(File file) {
		postRequest(Create, file);
	}

	@Override
	public void onFileDelete(File file) {
		postRequest(Delete, file);
	}

	@Override
	public void onStart(FileAlterationObserver observer) {
	}

	@Override
	public void onStop(FileAlterationObserver observer) {
	}

	private void postRequest(Operation operation, File file) {
		Path path = pathRelativizer.relativize(file);
		bus.post(new UploadEvent(file, operation, path));
	}
}
